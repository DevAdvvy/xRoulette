package com.xroulette.game;

import com.xroulette.XRoulette;
import org.bukkit.entity.Player;

public class RouletteManager {

    public static void start(Player player, double amount, String chosenColor) {

        var plugin = XRoulette.get();
        var econ = plugin.getEconomy().get();

        double min = plugin.getConfig().getDouble("roulette.min-bet");
        double max = plugin.getConfig().getDouble("roulette.max-bet");

        if (amount < min) {
            player.sendMessage("§cLa apuesta mínima es: §e" + min);
            return;
        }

        if (amount > max) {
            player.sendMessage("§cLa apuesta máxima es: §e" + max);
            return;
        }

        if (!econ.has(player, amount)) {
            player.sendMessage("§cNo tienes dinero.");
            return;
        }

        econ.withdrawPlayer(player, amount);

        new RouletteAnimation(player, amount, chosenColor).start();
    }
}