package com.xroulette.listener;

import com.xroulette.XRoulette;
import com.xroulette.game.RouletteAnimation;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import com.xroulette.game.RouletteManager;
import com.xroulette.input.ChatInputManager;
import com.xroulette.util.MoneyParser;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryListener implements Listener {

    private static final Map<UUID, String> selectedColor = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        String title = e.getView().getTitle();
        Player player = (Player) e.getWhoClicked();

        if (title.contains("Ruleta") || title.contains("color")) {
            e.setCancelled(true);
        }

        if (!title.equals("§c§lElige un color")) return;

        String color = null;

        switch (e.getSlot()) {
            case 11 -> color = "RED";
            case 13 -> color = "BLACK";
            case 15 -> color = "GREEN";
        }

        if (color == null) return;

        final String chosenColor = color;

        selectedColor.put(player.getUniqueId(), chosenColor);

        player.closeInventory();

        player.sendMessage("§eElegiste: " + chosenColor);
        player.sendMessage("§7Ahora escribe tu apuesta:");

        ChatInputManager.waitFor(player, input -> {
            try {
                double amount = MoneyParser.parse(input);

                RouletteManager.start(player, amount, chosenColor);

            } catch (Exception ex) {
                player.sendMessage("§cCantidad inválida.");
            }

        });
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        String title = e.getView().getTitle();

        if (!title.equals("§c§lRuleta")) return;

        Player player = (Player) e.getPlayer();

        if (!RouletteAnimation.isSpinning(player)) return;

        Bukkit.getScheduler().runTaskLater(XRoulette.get(), () -> {
            if (player.isOnline()) {
                player.openInventory(e.getInventory());
            }
        }, 1);
    }
}