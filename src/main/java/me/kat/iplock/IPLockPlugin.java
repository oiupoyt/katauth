package me.kat.iplock;

import me.kat.iplock.listener.PreLoginListener;
import me.kat.iplock.storage.IPStorage;
import org.bukkit.plugin.java.JavaPlugin;

public class IPLockPlugin extends JavaPlugin {

    private static IPLockPlugin instance;
    private IPStorage storage;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        storage = new IPStorage(this);
        storage.load();

        getServer().getPluginManager().registerEvents(
                new PreLoginListener(storage), this
        );

        getCommand("ipreset").setExecutor(new me.kat.iplock.command.IPResetCommand(storage));
        getCommand("ipstatus").setExecutor(new me.kat.iplock.command.IPStatusCommand(storage));
        getCommand("ipinfo").setExecutor(new me.kat.iplock.command.IPInfoCommand(storage));
        getCommand("ipforce").setExecutor(new me.kat.iplock.command.IPForceCommand(storage));
        getCommand("ipreload").setExecutor(new me.kat.iplock.command.IPReloadCommand(this, storage));
    }

    @Override
    public void onDisable() {
        storage.save();
    }

    public static IPLockPlugin get() {
        return instance;
    }
}
