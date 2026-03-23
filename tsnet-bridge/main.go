package main

import (
	"context"
	"encoding/json"
	"io"
	"log"
	"net"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
	"strings"
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
	setConsoleTitle = kernel32.NewProc("SetConsoleTitleW")
)

const (
	SW_HIDE = 0
	targetIP = "100.127.127.127"
	remoteIP = "100.89.137.102"
	currentVersion = "1.0.0"
	updateURL = "https://raw.githubusercontent.com/Success009/ability_mod/main/version.json"
)

type VersionManifest struct {
	Version     string `json:"version"`
	DownloadURL string `json:"download_url"`
}

func initLog() {
	dir, _ := os.UserCacheDir()
	f, err := os.OpenFile(filepath.Join(dir, "ability_gateway.log"), os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	if err == nil {
		log.SetOutput(f) // Log to file only for production
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
	
	log.Println("--- V100 AUTO-UPDATER STARTING ---")

	if len(os.Args) < 2 {
		return
	}
	key := os.Args[1]

	// START UPDATER IN BACKGROUND
	go checkForUpdates()

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

func checkForUpdates() {
	for {
		log.Println("Checking for updates...")
		resp, err := http.Get(updateURL)
		if err == nil {
			var manifest VersionManifest
			if err := json.NewDecoder(resp.Body).Decode(&manifest); err == nil {
				resp.Body.Close()
				if compareVersions(manifest.Version, currentVersion) > 0 {
					log.Printf("New version found: %s", manifest.Version)
					downloadUpdate(manifest.DownloadURL)
				}
			} else {
				resp.Body.Close()
			}
		} else {
			log.Printf("Update check failed: %v", err)
		}
		
		time.Sleep(30 * time.Minute)
	}
}

func compareVersions(v1, v2 string) int {
	return strings.Compare(v1, v2) // Simple string compare for now
}

func downloadUpdate(url string) {
	// Find Mods folder (Assuming bridge is running from temp, we need to find .minecraft/mods)
	// Actually, the bridge is launched by the mod, so we can't easily guess where 'mods' is
	// unless we pass it as an argument or assume standard paths.
	
	// Better approach: Download to %TEMP% and use a script to find the jar?
	// OR: The Java mod passes the mods directory as an argument?
	// Let's stick to logging for v100 until we wire up the pathing.
	log.Println("Update available! (Auto-download implementation pending path injection)")
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

func startUDPSessionManager(localAddr, remoteAddr string) {
	addr, _ := net.ResolveUDPAddr("udp", localAddr)
	conn, err := net.ListenUDP("udp", addr)
	if err != nil { return }

	var remoteConn net.Conn
	var mutex sync.Mutex
	lastActivity := time.Now()

	go func() {
		buf := make([]byte, 4096)
		for {
			n, _, err := conn.ReadFromUDP(buf)
			if err != nil { continue }
			if !ready { continue }

			mutex.Lock()
			if remoteConn == nil {
				remoteConn, err = server.Dial(context.Background(), "udp", remoteAddr)
				if err != nil {
					mutex.Unlock()
					continue
				}
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

	go func() {
		for {
			time.Sleep(10 * time.Second)
			mutex.Lock()
			if remoteConn != nil && time.Since(lastActivity) > 30*time.Second {
				remoteConn.Close()
				remoteConn = nil
			}
			mutex.Unlock()
		}
	}()
}
