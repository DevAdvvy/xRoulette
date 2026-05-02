package com.xroulette.gui;

import com.xroulette.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainGUI {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§c§lElige un color");

        inv.setItem(11, new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("§c§lROJO")
                .build());

        inv.setItem(13, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName("§8§lNEGRO")
                .build());

        inv.setItem(15, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName("§a§lVERDE")
                .build());

        player.openInventory(inv);
    }
}