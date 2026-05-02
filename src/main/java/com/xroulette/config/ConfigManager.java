package com.xroulette.config;

import com.xroulette.XRoulette;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;
import java.util.ArrayList;

public class ConfigManager {

    private final XRoulette plugin;
    private FileConfiguration config;

    public ConfigManager(XRoulette plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public double getMultiplier(String color) {
        return config.getDouble("multipliers." + color.toLowerCase());
    }

    public Material getMaterial(String color) {
        String mat = config.getString("colors." + color.toLowerCase());
        return Material.valueOf(mat);
    }

    public double getMinBet() {
        return config.getDouble("roulette.min-bet", 10);
    }

    public double getMaxBet() {
        return config.getDouble("roulette.max-bet", 10000);
    }

    public double getChance(String color) {
        return config.getDouble("roulette.chances." + color.toLowerCase());
    }

    public boolean isBroadcastEnabled() {
        return config.getBoolean("Broadcast.Enabled");
    }

    public double getBroadcastMinAmount() {
        return config.getDouble("Broadcast.MinAmount");
    }

    public List<String> getBroadcastMessage() {
        return config.getStringList("Broadcast.Message");
    }
}