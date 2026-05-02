package com.xroulette.command;

import com.xroulette.gui.MainGUI;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class RouletteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        MainGUI.open(player);
        return true;
    }
}