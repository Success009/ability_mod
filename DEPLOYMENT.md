# DEPLOYMENT GUIDE: Ability SMP Ultimate

## 1. PREREQUISITES
- **Go 1.21+** (For the bridge)
- **Java 21** (For the mod)
- **Tailscale Auth Key** (Reusable or Ephemeral)

## 2. BUILDING THE BRIDGE
```powershell
cd D:\ability\tsnet-bridge
$env:CGO_ENABLED='0'
$env:GOOS='windows'
$env:GOARCH='amd64'
go build -o AbilityGateway.exe main.go
cp AbilityGateway.exe D:\ability\AbilityModProject\src\main\resources\assets\abilitysmp\natives\
```

## 3. BUILDING THE MOD
```powershell
cd D:\ability\AbilityModProject
./gradlew.bat clean build
```

## 4. DEPLOYING TO MINECRAFT
1.  Copy `AbilityModProject/build/libs/ability-mod-1.0.0.jar` to your `%AppData%\.minecraft\mods` folder.
2.  Rename it to `Ability-SMP-Ultimate.jar`.
3.  Ensure `voicechat-fabric-1.21.4-2.6.12.jar` is present in the `D:\ability\mods` folder for bundling.

## 5. RUNNING
1.  Launch Minecraft via TLauncher.
2.  Agree to the **Admin UAC Popup** for `AbilityGateway.exe`.
3.  Wait for the button to turn Gold on the title screen.
4.  Join.

## 6. TROUBLESHOOTING
- **No Popup:** Check if `AbilityGateway.exe` is being blocked by Antivirus.
- **Invalid Session:** Verify the bridge is listening on `100.127.127.127` and NOT `127.0.0.1`.
- **Voice Chat Error:** Ensure the client and server versions match (v2.6.12).
