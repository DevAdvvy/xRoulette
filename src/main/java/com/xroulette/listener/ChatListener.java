package com.xroulette.listener;

import com.xroulette.XRoulette;
import com.xroulette.input.ChatInputManager;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if (!ChatInputManager.isWaiting(e.getPlayer())) return;

        e.setCancelled(true);

        Bukkit.getScheduler().runTask(XRoulette.get(), () -> {
            ChatInputManager.handle(e.getPlayer(), e.getMessage());
        });
    }
}