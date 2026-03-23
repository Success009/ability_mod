# TECHNICAL ARCHITECTURE: Ability SMP Mod

## 1. THE "SPLIT-BRAIN" BRIDGE (Go)
The bridge (`tsnet-bridge/main.go`) is a standalone Go binary compiled for Windows. It uses the `tsnet` library to embed Tailscale.

### Key Logic:
- **IP Ghosting:** Uses `netsh interface ipv4 add address "Loopback" 100.127.127.127` to hijack a specific IP address locally.
- **TCP Proxy:** Listens on `100.127.127.127:25565` and dials the server at `100.89.137.102:25565` via Tailscale.
- **UDP Proxy:** Handles Voice Chat by listening on UDP and forwarding packets.
- **Watchdog:** Accepts a `--pid` argument. It monitors this PID and exits (performing `netsh` cleanup) if the process dies.
- **Window Management:** Uses `user32.dll` to find its own console window and hide it 2 seconds after the tunnel is "ONLINE".

## 2. THE FABRIC MOD (Java)
The mod is a standard Fabric 1.21.4 mod.

### Key Mixins:
- **`TitleScreenMixin`:** Replaces the "Minecraft Realms" button. It polls the bridge log file (`ability_gateway.log`) to check for the "GATEWAY ONLINE" string. The button only enables when this string is found.
- **`ScreenAccessor`:** Provides access to private drawable lists to swap the buttons.

### Startup Sequence (`AbilityMod.java`):
1.  Kills any existing `AbilityGateway.exe`.
2.  Deletes the Tailscale state folder to force a fresh login (optional based on version).
3.  Extracts the bundled Voice Chat mod to the game's `mods` folder if missing.
4.  Extracts `AbilityGateway.exe` to a temporary cache.
5.  Launches the gateway using `powershell -Command Start-Process ... -Verb RunAs` to trigger the UAC prompt.

## 3. WHY THIS SYSTEM?
Traditional proxies (`127.0.0.1`) trigger Minecraft's "Localhost Mode," which enforces strict session validation. TLauncher users fail this check. By ghosting a non-loopback IP (like `100.127.127.127`), Minecraft switches to "Remote Mode," which accepts the TLauncher offline identity without error.
