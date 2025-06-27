# BUNGEE-FUN-MOD

## ✅ Feature

### 🧩 General Features
- 🔧 Easy YAML-based config (config.yml, messages.yml)

- ⚙️ /modreload command to reload configs at runtime

- 🔐 Console and in-game command support

- 🧠 Smart UUID handling (works with offline players)

- 🔄 Tempban/mute with auto-expiry on player join

- 🪵 Logs all moderation actions to Discord using webhooks

- 📝 Modular command system for easy extension

### 🎉 Fun Commands
- /hug <player>	Sends a hug message
- /slap <player>	Slaps a player with a random item
- /roast <player>	Sends a roast from config
- /coinflip	Flips a virtual coin
- /8ball <question>	Magic 8-ball answers from a pool

## 🔨 Moderation Commands
Command	Description
- /ban <player> [time] [reason]	Bans a player temporarily or permanently
- /unban <player>	Unbans a player
- /kick <player> [reason]	Kicks a player
- /mute <player> [time] [reason]	Mutes globally
- /warn <player> <reason>	Issues a warning
- /modlog <player>	Shows mod history from YAML
- /report <player> <reason>	Players can report others
- /modreload	Reloads YAML configs on the fly

## 🔐 Moderation Logic
- ✅ Fully offline player support

- ✅ Time parsing (1h, 2d, 15m)

- ✅ Cooldown system per command (configurable)

- ✅ Auto-tempban after X warnings (threshold & duration in config.yml)

- ✅ Silent punishments (-s flag in command)

- ✅ Logs to console + Discord webhook as rich embeds

- ✅ Reports stored and viewable by staff

## 🛰 Discord Webhook Logging
Sends all moderation actions to a Discord channel:

- Bans, unbans

- Mutes, unmutes

- Kicks, warns

- Reports