package me.kat.iplock.storage;

import com.google.gson.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IPStorage {

    public record Entry(String ip, long time) {}

    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, Entry> data = new ConcurrentHashMap<>();

    public IPStorage(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "ips.json");
    }

    public Entry get(String name) {
        return data.get(name.toLowerCase());
    }

    public void bind(String name, String ip) {
        data.put(name.toLowerCase(), new Entry(ip, Instant.now().toEpochMilli()));
        saveAsync();
    }

    public void remove(String name) {
        data.remove(name.toLowerCase());
        saveAsync();
    }

    public void load() {
        if (!file.exists()) return;

        try (Reader r = new FileReader(file)) {
            JsonObject obj = JsonParser.parseReader(r).getAsJsonObject();
            obj.entrySet().forEach(e -> {
                JsonObject v = e.getValue().getAsJsonObject();
                data.put(e.getKey(),
                        new Entry(v.get("ip").getAsString(), v.get("time").getAsLong()));
            });
        } catch (Exception ignored) {}
    }

    public void save() {
        try (Writer w = new FileWriter(file)) {
            JsonObject obj = new JsonObject();
            data.forEach((k, v) -> {
                JsonObject o = new JsonObject();
                o.addProperty("ip", v.ip());
                o.addProperty("time", v.time());
                obj.add(k, o);
            });
            gson.toJson(obj, w);
        } catch (Exception ignored) {}
    }

    private void saveAsync() {
        new Thread(this::save).start();
    }
}
