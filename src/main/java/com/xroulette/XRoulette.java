package com.xroulette;

import com.xroulette.command.RouletteCommand;
import com.xroulette.config.ConfigManager;
import com.xroulette.economy.EconomyManager;
import com.xroulette.listener.ChatListener;
import com.xroulette.listener.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class XRoulette extends JavaPlugin {
    private String color(String msg) {

        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("&#([A-Fa-f0-9]{6})")
                .matcher(msg);

        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");

            for (char c : hex.toCharArray()) {
                replacement.append("§").append(c);
            }

            matcher.appendReplacement(buffer, replacement.toString());
        }

        matcher.appendTail(buffer);

        return buffer.toString().replace("&", "§");
    }

    private static XRoulette instance;

    private EconomyManager economyManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);
        economyManager = new EconomyManager(this);

        getCommand("roulette").setExecutor(new RouletteCommand());

        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        log("&4-----------------------");
        log("&c       ¡START NOW!");
        log("&c    &f" + getName() + " - v" + getDescription().getVersion());
        log("&4-----------------------");
        log("&c    Author: &fDevAdvvy");
        log("&c Git: &fgithub.com/DevAdvvy");
        log("&4-----------------------");
    }

    public static XRoulette get() {
        return instance;
    }

    public EconomyManager getEconomy() {
        return economyManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    private void log(String msg) {
        String prefix = "&8[&cXRoulette&8] ";
        Bukkit.getConsoleSender().sendMessage(color(prefix + msg));
    }
}