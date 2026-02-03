package me.kat.iplock.command;

import me.kat.iplock.IPLockPlugin;
import me.kat.iplock.storage.IPStorage;
import org.bukkit.command.*;

public class IPReloadCommand implements CommandExecutor {

    private final IPLockPlugin plugin;
    private final IPStorage storage;

    public IPReloadCommand(IPLockPlugin plugin, IPStorage storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!s.isOp()) return true;
        plugin.reloadConfig();
        storage.load();
        s.sendMessage("KIA reloaded.");
        return true;
    }
}
