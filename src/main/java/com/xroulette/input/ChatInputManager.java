package com.xroulette.input;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;


public class ChatInputManager {

    private static final Map<UUID, Consumer<String>> map = new HashMap<>();

    public static void waitFor(Player player, Consumer<String> action) {
        map.put(player.getUniqueId(), action);
        player.sendMessage("§eEscribe la cantidad (ej: 100k, 1M)");
    }

    public static boolean handle(Player player, String message) {
        if (!map.containsKey(player.getUniqueId())) return false;

        Consumer<String> action = map.remove(player.getUniqueId());
        action.accept(message);
        return true;
    }
    public static boolean isWaiting(Player player) {
        return map.containsKey(player.getUniqueId());
    }
}