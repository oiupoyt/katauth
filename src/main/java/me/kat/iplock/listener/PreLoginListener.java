package me.kat.iplock.listener;

import me.kat.iplock.storage.IPStorage;
import me.kat.iplock.discord.DiscordWebhook;
import me.kat.iplock.util.LogManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.time.Instant;

public class PreLoginListener implements Listener {

    private final IPStorage storage;
    private final LogManager logManager;

    public PreLoginListener(IPStorage storage, LogManager logManager) {
        this.storage = storage;
        this.logManager = logManager;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        String ip = event.getAddress().getHostAddress();

        IPStorage.Entry entry = storage.get(name);

        if (entry == null) {
            storage.bind(name, ip);
            logManager.log(name, ip, true, "First login - IP bound");
            return;
        }

        if (!entry.ip().equals(ip)) {
            event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    "IP mismatch. Access denied. Contact the owner if you believe this is a mistake");

            logManager.log(name, ip, false, "IP mismatch (Expected: " + logManager.formatIp(entry.ip()) + ")");

            DiscordWebhook.sendAlert(
                    name,
                    entry.ip(),
                    ip,
                    Instant.now());
        } else {
            logManager.log(name, ip, true, "IP match");
        }
    }
}
