package me.kat.iplock.listener;

import me.kat.iplock.storage.IPStorage;
import me.kat.iplock.discord.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.time.Instant;

public class PreLoginListener implements Listener {

    private final IPStorage storage;

    public PreLoginListener(IPStorage storage) {
        this.storage = storage;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        String name = event.getName();
        String ip = event.getAddress().getHostAddress();

        IPStorage.Entry entry = storage.get(name);

        if (entry == null) {
            storage.bind(name, ip);
            return;
        }

        if (!entry.ip().equals(ip)) {
            event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    "IP mismatch. Access denied. Contact the owner if you believe this is a mistake"
            );

            DiscordWebhook.sendAlert(
                    name,
                    entry.ip(),
                    ip,
                    Instant.now()
            );
        }
    }
}
