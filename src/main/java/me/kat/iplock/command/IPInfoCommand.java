package me.kat.iplock.command;

import me.kat.iplock.storage.IPStorage;
import org.bukkit.command.*;

public class IPInfoCommand implements CommandExecutor {

    private final IPStorage storage;

    public IPInfoCommand(IPStorage storage) {
        this.storage = storage;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!s.isOp() || a.length != 1) return true;

        IPStorage.Entry e = storage.get(a[0]);
        if (e == null) {
            s.sendMessage("No data.");
        } else {
            s.sendMessage("IP: " + e.ip() + " | Time: " + e.time());
        }
        return true;
    }
}
