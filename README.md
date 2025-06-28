# BUNGEE-FUN-MOD

## ✅ Features

### 🧩 General Features
- 🔧 Easy YAML-based config (`config.yml`, `messages.yml`, `punishments.yml`)
- ⚙️ `/modreload` command to reload configs at runtime
- 🔐 Console and in-game command support
- 🧠 Smart UUID handling (works with offline players)
- 🔄 Tempban/mute with auto-expiry and auto-unmute/unban on player join
- 🪵 Logs all moderation actions to Discord using webhooks (rich embeds)
- 📝 Modular command system for easy extension and maintenance

### 🎉 Fun Commands
| Command         | Description                         |
| --------------- | --------------------------------- |
| `/hug <player>` | Sends a warm hug message           |
| `/slap <player>`| Slaps a player with a random item |
| `/roast <player>` | Sends a roast from config          |
| `/coinflip`     | Flips a virtual coin               |
| `/8ball <question>` | Magic 8-ball answers from a pool   |

### 🔨 Moderation Commands
| Command                    | Description                                 |
| -------------------------- | ------------------------------------------- |
| `/ban <player> [time] [reason]`   | Bans a player temporarily or permanently    |
| `/unban <player>`          | Unbans a player                             |
| `/kick <player> [reason]`  | Kicks a player                             |
| `/mute <player> [time] [reason]`  | Mutes a player globally                      |
| `/warn <player> <reason>`  | Issues a warning                           |
| `/modlog <player>`         | Shows mod history from YAML                |
| `/report <player> <reason>`| Allows players to report others            |
| `/modreload`               | Reloads all YAML configs on the fly        |
| `/ipban <ip> <time> <reason>` | Bans an IP temporarily or permanently       |
| `/unipban <ip>`            | Unbans an IP                              |

### 🔐 Moderation Logic
- ✅ Fully offline player support (UUID and name handling)
- ✅ Time parsing for durations (`1h`, `2d`, `15m`, or `0` for permanent)
- ✅ Cooldown system per command (configurable via `config.yml`)
- ✅ Auto-tempban after X warnings (threshold & duration configurable)
- ✅ Silent punishments (support for `-s` flag in commands)
- ✅ Logs all punishments and reports to console and Discord webhook as rich embeds
- ✅ Reports stored and viewable by staff with `/report` and `/modlog`
- ✅ Auto unmute/unban expired punishments on player join
- ✅ Ban evade detection (alerts on banned players joining from new IPs)

### 🛰 Discord Webhook Logging
All moderation actions are logged to a configurable Discord channel with rich embeds including:

- Bans and unbans
- Mutes and unmutes
- Kicks and warnings
- Reports submitted by players
- Auto unmute/unban events
- Ban evade alerts

---

## 📦 Installation

1. Build the plugin using Maven with Java 8+.
2. Place the compiled `.jar` in your BungeeCord `plugins` folder.
3. Configure `config.yml`, `messages.yml`, and `punishments.yml` as needed.
4. Restart or reload your BungeeCord proxy.
5. Use `/modreload` to reload configs without restart.

---

## ⚙️ Configuration

- `config.yml` handles plugin settings, cooldowns, webhook URLs, and thresholds.
- `messages.yml` allows customization of all chat messages.
- `punishments.yml` stores all warnings, mutes, bans, reports, and IP bans.

---

## 🛠 Support & Contribution

Feel free to open issues or contribute via pull requests on the repository.

---

**Enjoy moderating your BungeeCord server with BungeeFunMod!**
