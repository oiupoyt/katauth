package me.kat.iplock.util;

import me.kat.iplock.IPLockPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUpdater {

    private final IPLockPlugin plugin;

    public ConfigUpdater(IPLockPlugin plugin) {
        this.plugin = plugin;
    }

    public void updateConfig() {
        FileConfiguration config = plugin.getConfig();
        boolean updated = false;

        plugin.getLogger().info("Checking config for missing settings...");

        // Use isSet instead of contains to check if the setting is specifically in the
        // file on disk
        // contains() returns true if the setting is in AND/OR in the defaults (jar)

        // Check and add missing discord settings
        if (!config.isSet("discord.webhook")) {
            plugin.getLogger().info("Adding missing discord.webhook setting");
            config.set("discord.webhook", "PUT_WEBHOOK_URL_HERE");
            updated = true;
        }

        // Check and add missing privacy settings
        if (!config.isSet("privacy.mask-ips")) {
            plugin.getLogger().info("Adding missing privacy.mask-ips setting");
            config.set("privacy.mask-ips", false);
            updated = true;
        }

        // Check and add missing messages
        if (!config.isSet("messages.blocked")) {
            plugin.getLogger().info("Adding missing messages.blocked setting");
            config.set("messages.blocked",
                    "&cLogin blocked: IP mismatch. If you believe this is a mistake contact the owner");
            updated = true;
        }

        // Save the config if it was updated
        if (updated) {
            plugin.saveConfig();
            plugin.reloadConfig(); // Reload to ensure in-memory config is updated
            plugin.getLogger().info("Config.yml has been updated with missing settings.");
        } else {
            plugin.getLogger().info("Config.yml is up to date.");
        }
    }
}