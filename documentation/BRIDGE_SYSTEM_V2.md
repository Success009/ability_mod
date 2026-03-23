# Ability Plugin (Firebase Bridge) v2.0.0 Documentation

The Ability Plugin connects your Minecraft server to the Firebase Realtime Database to power the website dashboard, chat bridge, and leaderboard systems.

## 1. Configuration (`config.yml`)
The plugin is now fully configurable. The file is located at `plugins/Ability/config.yml`.

### Firebase Settings
- `service-account-file`: The name of your JSON service account key (place in the plugin folder).
- `database-url`: Your Firebase Database URL.
  - *Default:* `https://community-canvas-255fa-default-rtdb.firebaseio.com/`

### Feature Toggles
You can enable/disable modules without removing the plugin:
- `chat-bridge`: Game <-> Web chat synchronization.
- `server-monitor`: Real-time TPS, RAM, and Player count updates.
- `leaderboards`: Automatic player stat tracking.
- `linking`: The `/link` command for web account verification.

### Chat Settings
- `log-joins/quits/deaths`: Toggle server events being sent to the website.
- `plugin-prefix`: The prefix for all plugin messages. (Default: `&8[&bAbility&8]`)

## 2. Custom Formatting
You can change how messages from the website appear in Minecraft chat:

| Placeholder | Description |
| :--- | :--- |
| `{name}` | The name of the user (Web User, Anonymous, or Admin) |
| `{message}` | The content of the message |
| `{prefix}` | The plugin prefix defined in config |

**Formats available in config:**
- `web-user`: Messages from logged-in web users.
- `web-anonymous`: Messages from anonymous web visitors.
- `admin-panel`: Messages sent specifically from the Admin dashboard.
- `link-message`: A multi-line message sent when a player runs `/link`.

## 3. Administrative Commands
The plugin uses the `bridge.admin` permission (default: OP).

- `/bridge reload`: Reloads the `config.yml` and applies changes immediately (except for core Firebase connection settings which may require a restart).

## 4. Technical Architecture
- **Performance:** All Firebase operations run **Asynchronously**. The plugin will never cause "Server Thread Hang" or lag spikes during database updates.
- **Branding:** All system logs and messages now use the "Ability" naming convention instead of "Bridge".
- **Compatibility:** Targeted for **Paper 1.21.4** using Java 21+.

## 5. Website Integration
The plugin expects the following structure in Firebase:
- `Ability/minecraft/chatLogs`: Chat messages.
- `Ability/minecraft/status`: Server health and info.
- `Ability/minecraft/onlinePlayers`: List of active players (includes OP status for staff badges).
- `Ability/security`: Handlers for linking codes and verified users.
- `Ability/leaderboard`: Player statistics.
