package me.kat.iplock.command;

import me.kat.iplock.storage.IPStorage;
import org.bukkit.command.*;

public class IPForceCommand implements CommandExecutor {

    private final IPStorage storage;

    public IPForceCommand(IPStorage storage) {
        this.storage = storage;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!s.isOp() || a.length != 1) return true;
        storage.remove(a[0]);
        s.sendMessage("IP binding removed.");
        return true;
    }
}
