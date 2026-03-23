# PROJECT HISTORY: The Road to v86

## Phase 1: The Proxy Era (v1-v40)
- **Attempt:** Used a simple SOCKS5 proxy on `127.0.0.1`.
- **Failure:** Minecraft 1.21.4 detects `127.0.0.1` as "Localhost" and enforces strict session validation. TLauncher failed with "Invalid Session."

## Phase 2: The LAN Ghosting Era (v41-v65)
- **Attempt:** Used Go to bind to the LAN IP (e.g., `192.168.1.5`).
- **Success:** Worked for some, but caused conflicts if the IP changed.
- **Failure:** "Connection Refused" loops when the bridge tried to dial the same IP it was ghosting.

## Phase 3: The Admin Ghosting Era (v66-v86)
- **Attempt:** Used `netsh` (Admin) to map `100.89.137.102` to Loopback.
- **Critical Failure (v81):** "Routing Loop." The OS routed the outbound traffic back to the bridge because the destination IP was ghosted locally.
- **The Breakthrough (v82):** **"Split-Brain Architecture."**
    - **Ghost IP:** `100.127.127.127` (Fake Local).
    - **Remote IP:** `100.89.137.102` (Real Remote).
    - **Result:** Traffic flows `Client -> Ghost -> Bridge -> Remote`. No loop.

## Key Decisions
- **UAC:** We MUST use `powershell Start-Process -Verb RunAs` to guarantee the Admin popup. `cmd /c` is unreliable.
- **Window:** The bridge window MUST stay visible for ~3 seconds to prove it launched, then hide itself using `user32.dll`.
- **Identity:** We must clear the `ASMP_Member_State` folder on startup to ensure a fresh Tailscale login if the server restarts.
