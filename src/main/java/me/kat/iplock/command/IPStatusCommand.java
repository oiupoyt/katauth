package me.kat.iplock.command;

import me.kat.iplock.storage.IPStorage;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IPStatusCommand implements CommandExecutor {

    private final IPStorage storage;

    public IPStatusCommand(IPStorage storage) {
        this.storage = storage;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (!(s instanceof Player p)) {
            s.sendMessage("This command can only be used by players.");
            return true;
        }

        IPStorage.Entry e = storage.get(p.getName());
        if (e == null) {
            p.sendMessage("No IP bound. Join with a valid IP to bind it.");
        } else {
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(e.time()), ZoneId.systemDefault());
            String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            p.sendMessage("Your IP is bound since: " + formattedTime);
        }
        return true;
    }
}
