# PROJECT CONTEXT: Minecraft Ability SMP (Ultimate Edition)

**CRITICAL MANDATE:** This project uses a hybrid Java/Go architecture to bypass Tailscale app requirements and Minecraft session security.

## 1. PROJECT STATUS (v86 - GOLD)
- **Current Version:** v86 "Polished Master"
- **Architecture:** IP-Ghosting (Admin Mode)
- **Status:** STABLE & VERIFIED
- **Voice Chat:** Enabled (Fabric 2.6.12 Client -> Bukkit 2.6.12 Server)

## 2. CORE ARCHITECTURE (THE "PHOENIX" SYSTEM)
The mod functions by "ghosting" the server's Tailscale IP locally to bypass session checks.
- **Bridge:** `AbilityGateway.exe` (Go + tsnet).
- **Injection:** Java (Fabric) launches the bridge with `runas` (Admin) to perform `netsh` IP hijacking.
- **Local IP:** `100.127.127.127` (Ghost Loopback).
- **Remote IP:** `100.89.137.102` (Real Server).
- **Watchdog:** The bridge monitors the Java PID and kills itself on game exit.

## 3. FILE MAP (CRITICAL)
- **Source (Mod):** `D:\ability\AbilityModProject`
- **Source (Bridge):** `D:\ability\tsnet-bridge`
- **Backups:** `D:\ability\GOLD_WORKING_BACKUP` (Last stable v85 state)
- **Bundled Mods:** `D:\ability\mods` (Source for Voice Chat JARs)

## 4. SERVER CONFIGURATION
- **IP:** `100.89.137.102`
- **User:** `bb`
- **Safe Mode:** `online-mode=false`, `enforce-secure-profile=false`, `network-compression-threshold=-1`.

## 5. RECENT WINS (DO NOT REVERT)
- **Fixed "Invalid Session":** By using IP-Ghosting, Minecraft treats the connection as "Remote," which enables the correct offline-mode handshake.
- **Fixed "Connection Refused":** By using a different local Ghost IP (`100.127.127.127`) than the Remote IP, we prevented a routing loop.
- **Fixed "Invisible Crash":** By disabling the auto-termination watchdog during debug, we found the PID mismatch issue.

## 6. NEXT GOALS
- Add more custom abilities to the SMP.
- Monitor Voice Chat stability under high load.
- Implement a more native-looking UAC prompt if possible.

*Reference `ARCHITECTURE.md` for technical implementation details.*
