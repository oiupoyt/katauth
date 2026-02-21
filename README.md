# 🛡️ KatIPAuth (KIA)

[![Download on Modrinth](https://img.shields.io/modrinth/dt/katipauth?logo=modrinth)](https://modrinth.com/plugin/katipauth) [![GitHub](https://img.shields.io/github/v/release/oiupoyt/katIPAuth)](https://github.com/oiupoyt/katIPAuth)

**KatIPAuth** is an optimized plugin for **Minecraft 1.19 to 1.21.x** that locks accounts to IPs. 
It provides account security for cracked servers without the need for complex authentication plugins.  
No passwords. No sessions. Just **IP says yes or go home**.

## 🚀 Key Features

### 👤 Identity Locking
- **Automatic IP Binding**: Binds a player's username to their IP on the first join.
- **Login Block**: Automatically disallows connections from mismatching IPs.
- **Customizable Messages**: Configure kick messages with full color code support.

### 📶 Subnet Locking (BETA)
- **Dynamic IP Support**: Solving dynamic IP issues by allowing connections within the same subnet.
- **Security Balance**: Provides flexibility for players whose IPs change within their ISP's range while still blocking unauthorized access from elsewhere.

### 📡 Discord Alerts
- Sends detailed embeds with player info, stored IP, attempted IP, and timestamps.

### 🛠️ Maintenance & Utilities
- **Config Updater**: Automatically adds missing settings and comments to your `config.yml` on plugin updates.
- **Formatted Data**: View binding times in standard readable formats (`yyyy-MM-dd HH:mm:ss`).

---

## 🧾 Commands

### Player Commands
#### /ipstatus
- Shows if your currently used IP is bound.
- Shows exactly **when** it was bound in a readable format.

### Admin Commands (Permission: `ipauth.admin`)
#### /ipinfo <player>
- Displays the stored IP and original binding time for any player.
#### /ipreset <player>
- Resets a player's IP binding and kicks them instantly. The next IP they join with will be their new bound IP.
#### /ipforce <player>
- Removes a player's current IP binding. Unlike `/ipreset`, it does NOT kick the player; the next time they join, a new IP will be bound.
#### /ipreload
- Reloads the `config.yml` and re-initializes all settings (including Discord webhooks).

---

## ⚙️ Configuration

This is a sample of the `config.yml` with explanations for each section:

```yaml
# Discord Webhook URL for logging IP changes (optional)
discord:
  webhook: "PUT_WEBHOOK_URL_HERE"

# Privacy settings
privacy:
  mask-ips: false # If true, last two octets of IPs will be replaced with x (e.g. 192.168.xx.xx)

# Customizable messages
messages:
  blocked: "&cLogin blocked: IP mismatch.If you believe this is a mistake contact the owner"

# Beta features (Use with caution)
beta:
  subnet-locking: false # If true, players can join as long as they are in the same /24 subnet (e.g. 192.168.1.*)
```

---

## ❓ FAQ

### Q: Can dynamic IP players use this?
- A: Yes. If their IP changes within the same subnet, you can enable **Subnet Locking** in the beta settings.
*Reminder: Subnet Locking is a beta feature and may not be fully stable.*

### Q: Can VPNs bypass this?
- A: No. VPN users get blocked unless they join from an IP that matches their binding or the subnet.

### Q: Is this compatible with other auth plugins?
- A: Yes. It works independently before players even enter the server, making it a great second layer of security alongside plugins like AuthMe.

### Q: How do I reset a player's IP?
- A: Ask an admin to use `/ipreset <player>` to reset their IP binding and kick them. They can then rejoin to bind a new IP.
