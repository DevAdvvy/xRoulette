package com.xroulette.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import com.xroulette.XRoulette;

public class EconomyManager {

    private Economy econ;

    public EconomyManager(XRoulette plugin) {
        if (!setupEconomy(plugin)) {
            plugin.getLogger().severe("Vault no encontrado!");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private boolean setupEconomy(XRoulette plugin) {
        RegisteredServiceProvider<Economy> rsp =
                plugin.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) return false;

        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy get() {
        return econ;
    }
}