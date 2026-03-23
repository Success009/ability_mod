package main

import (
	"context"
	"io"
	"log"
	"net"
	"os"
	"os/exec"
	"path/filepath"
	"sync"
	"syscall"
	"time"

	"tailscale.com/tsnet"
)

var (
	server *tsnet.Server
	ready  bool
	
	kernel32 = syscall.NewLazyDLL("kernel32.dll")
	user32 = syscall.NewLazyDLL("user32.dll")
	getConsoleWindow = kernel32.NewProc("GetConsoleWindow")
	showWindow = user32.NewProc("ShowWindow")
)

const SW_HIDE = 0
const targetIP = "100.127.127.127"
const remoteIP = "100.89.137.102"

func initLog() {
	dir, _ := os.UserCacheDir()
	f, err := os.OpenFile(filepath.Join(dir, "ability_gateway.log"), os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	if err == nil {
		log.SetOutput(f)
	}
}

func hideConsole() {
	hwnd, _, _ := getConsoleWindow.Call()
	if hwnd != 0 {
		showWindow.Call(hwnd, SW_HIDE)
	}
}

func cleanup() {
	exec.Command("netsh", "interface", "ipv4", "delete", "address", "Loopback", targetIP).Run()
}

func main() {
	hideConsole()
	initLog()
	
	log.Println("--- V98 UDP-SESSION STARTING ---")

	if len(os.Args) < 2 {
		return
	}
	key := os.Args[1]

	exec.Command("netsh", "interface", "ipv4", "add", "address", "Loopback", targetIP, "255.255.255.255").Run()
	defer cleanup()

	dir, _ := os.UserCacheDir()
	tsDir := filepath.Join(dir, "ASMP_Member_State")
	os.MkdirAll(tsDir, 0755)

	server = &tsnet.Server{
		AuthKey:  key,
		Dir:      tsDir,
		Hostname: "AbilityMod-Phoenix",
	}

	go startGhostListener(targetIP+":25565", remoteIP+":25565")
	go startUDPSessionManager(targetIP+":24454", remoteIP+":24454")

	if err := server.Start(); err != nil {
		return
	}

	for {
		ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
		st, err := server.Up(ctx)
		cancel()
		if err == nil && st != nil {
			ready = true
			log.Println(">>> GATEWAY ONLINE <<<")
			break
		}
		time.Sleep(2 * time.Second)
	}

	select {}
}

func startGhostListener(localAddr, remoteAddr string) {
	l, err := net.Listen("tcp", localAddr)
	if err != nil { return }
	for {
		client, err := l.Accept()
		if err != nil { continue }
		go func(c net.Conn) {
			defer c.Close()
			if !ready { return }
			remote, err := server.Dial(context.Background(), "tcp", remoteAddr)
			if err != nil { return }
			defer remote.Close()
			done := make(chan struct{}, 2)
			go func() { io.Copy(remote, c); done <- struct{}{} }()
			go func() { io.Copy(c, remote); done <- struct{}{} }()
			<-done
		}(client)
	}
}

// UDP SESSION MANAGER
// Maintains a persistent connection to the server for Voice Chat
func startUDPSessionManager(localAddr, remoteAddr string) {
	addr, _ := net.ResolveUDPAddr("udp", localAddr)
	conn, err := net.ListenUDP("udp", addr)
	if err != nil { return }

	var remoteConn net.Conn
	var mutex sync.Mutex
	lastActivity := time.Now()

	// Sender Routine
	go func() {
		buf := make([]byte, 4096)
		for {
			n, _, err := conn.ReadFromUDP(buf)
			if err != nil { continue }
			if !ready { continue }

			mutex.Lock()
			// Create or Reuse Connection
			if remoteConn == nil {
				log.Println("Creating new UDP Session...")
				remoteConn, err = server.Dial(context.Background(), "udp", remoteAddr)
				if err != nil {
					mutex.Unlock()
					continue
				}
				
				// Start Receiver for this session
				go func(rc net.Conn) {
					defer rc.Close()
					rBuf := make([]byte, 4096)
					for {
						_, err := rc.Read(rBuf)
						if err != nil { 
							mutex.Lock()
							if remoteConn == rc { remoteConn = nil }
							mutex.Unlock()
							return 
						}
					}
				}(remoteConn)
			}
			
			lastActivity = time.Now()
			_, err = remoteConn.Write(buf[:n])
			mutex.Unlock()
		}
	}()

	// Cleanup Routine
	go func() {
		for {
			time.Sleep(10 * time.Second)
			mutex.Lock()
			if remoteConn != nil && time.Since(lastActivity) > 30*time.Second {
				log.Println("UDP Session Timed Out")
				remoteConn.Close()
				remoteConn = nil
			}
			mutex.Unlock()
		}
	}()
}
