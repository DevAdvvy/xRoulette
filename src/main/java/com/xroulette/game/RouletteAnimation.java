package com.xroulette.game;

import com.xroulette.XRoulette;

import com.xroulette.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;

import java.util.*;



public class RouletteAnimation {

    private final Player player;
    private final double amount;
    private final String chosenColor;

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

        // & -> §
        return buffer.toString().replace("&", "§");
    }

    private static final Set<Player> spinning = new HashSet<>();

    private static final List<String> WHEEL = List.of(
            "GREEN",
            "RED", "BLACK", "RED", "BLACK", "RED", "BLACK", "RED", "BLACK",
            "RED", "BLACK", "RED", "BLACK", "RED", "BLACK", "RED", "BLACK",
            "RED", "BLACK", "RED", "BLACK", "RED", "BLACK", "RED", "BLACK",
            "RED", "BLACK", "RED", "BLACK", "RED", "BLACK", "RED", "BLACK",
            "RED", "BLACK", "RED", "BLACK"
    );

    private final Random random = new Random();

    public RouletteAnimation(Player player, double amount, String chosenColor) {
        this.player = player;
        this.amount = amount;
        this.chosenColor = chosenColor;
    }

    public void start() {

        spinning.add(player);

        Inventory inv = Bukkit.createInventory(null, 27, color("&#FF2F2F&lRuleta"));

        inv.setItem(4, new ItemStack(Material.HOPPER));

        player.openInventory(inv);

        String finalResult = getWeightedResult();
        List<String> strip = generateStrip(120);

        strip.set(strip.size() - 5, finalResult);

        new BukkitRunnable() {

            double speed = 0.10;
            double maxSpeed = 1.4;
            double current = 0;

            int index = 0;

            @Override
            public void run() {

                current += speed;

                if (current >= 1) {

                    current = 0;
                    index++;

                    update(inv, strip, index);

                    float pitch = (float) Math.max(0.5, 1.5 - speed);

                    player.playSound(
                            player.getLocation(),
                            Sound.UI_BUTTON_CLICK,
                            1.0f,
                            pitch
                    );
                }

                speed += 0.008;

                if (speed >= maxSpeed) {

                    String result = strip.get((index + 4) % strip.size());

                    finish(result);

                    player.playSound(
                            player.getLocation(),
                            Sound.ENTITY_PLAYER_LEVELUP,
                            1.0f,
                            1.0f
                    );

                    cancel();
                }
            }

        }.runTaskTimer(XRoulette.get(), 0, 1);
    }

    public static boolean isSpinning(Player player) {
        return spinning.contains(player);
    }

    private List<String> generateStrip(int size) {

        List<String> strip = new ArrayList<>();

        int start = random.nextInt(WHEEL.size());

        for (int i = 0; i < size; i++) {
            strip.add(WHEEL.get((start + i) % WHEEL.size()));
        }

        return strip;
    }

    private void update(Inventory inv, List<String> strip, int index) {

        for (int i = 0; i < 9; i++) {

            String color = strip.get((index + i) % strip.size());
            inv.setItem(9 + i, createItem(color));
        }
    }

    private ItemStack createItem(String color) {

        Material mat;
        String name;

        switch (color) {
            case "RED":
                mat = Material.RED_STAINED_GLASS_PANE;
                name = "&#FF2F2F&lRojo";
                break;
            case "BLACK":
                mat = Material.BLACK_STAINED_GLASS_PANE;
                name = "&#264364&lNegro";
                break;
            default:
                mat = Material.GREEN_STAINED_GLASS_PANE;
                name = "&#0BCD00&lVerde";
                break;
        }

        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(color(name));
            item.setItemMeta(meta);
        }

        return item;
    }

    private String getWeightedResult() {

        var config = XRoulette.get().getConfigManager();

        double red = config.getChance("red");
        double black = config.getChance("gray");
        double green = config.getChance("green");

        double total = red + black + green;
        double r = random.nextDouble() * total;

        if (r < red) return "RED";
        if (r < red + black) return "BLACK";
        return "GREEN";
    }

    private void finish(String result) {

        player.sendMessage(color(""));
        player.sendMessage(color("&#FF2F2F🔥 &#FF2F2F&lRULETA"));
        player.sendMessage(color("&#F5F5F5Resultado: " + getColorHex(result)));

        var econ = XRoulette.get().getEconomy().get();
        var config = XRoulette.get().getConfigManager();

        if (result.equalsIgnoreCase(chosenColor)) {

            player.sendMessage(color("&#00FFAA&l✔ GANASTE"));
            player.sendMessage(color("&#F5F5F5Ganancia: &#00FFAA$" + amount));

            if (econ != null) {
                double multiplier = config.getMultiplier(result);
                double reward = amount * multiplier;

                econ.depositPlayer(player, reward);

                if (config.isBroadcastEnabled() && reward >= config.getBroadcastMinAmount()) {

                    for (String line : config.getBroadcastMessage()) {

                        boolean centered = line.contains("[center]");
                        line = line.replace("[center]", "");

                        String msg = line
                                .replace("%player%", player.getName())
                                .replace("%amount%", String.format("%.2f", reward))
                                .replace("%color%", result);

                        msg = color(msg);

                        if (centered) {
                            msg = TextUtil.center(msg);
                        }

                        Bukkit.broadcastMessage(msg);
                    }
                }
            }

        } else {
            player.sendMessage(color("&#FF4C4C&l✖ PERDISTE"));
            player.sendMessage(color("&#F5F5F5Mejor suerte la próxima"));
        }

        spinning.remove(player);

        Bukkit.getScheduler().runTaskLater(XRoulette.get(), () -> {
            player.closeInventory();
        }, 60L);
    }

    private String getColorHex(String result) {
        return result;
    }
}