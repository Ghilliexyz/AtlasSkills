package com.atlasplugins.atlasskills.managers.uiapi;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class BossBarManager {
    private final Map<Player, BossBar> bossBars = new HashMap<>();
    private final Map<Player, BukkitRunnable> hideTasks = new HashMap<>();

    public void createBossBar(Player player, String title, BarColor color, BarStyle style, double barProgress) {
        BossBar bossBar = bossBars.get(player);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(Main.color(title), color, style);
            bossBars.put(player, bossBar);
            Main.instance.getLogger().log(Level.INFO, "Created new BossBar for player: " + player.getName());
        } else {
            bossBar.setTitle(Main.color(title));
            bossBar.setColor(color);
            bossBar.setStyle(style);
            Main.instance.getLogger().log(Level.INFO, "Reusing existing BossBar for player: " + player.getName());
        }
        bossBar.setProgress(barProgress);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public void removeBossBar(Player player) {
        BossBar bossBar = bossBars.remove(player);
        if (bossBar != null) {
            bossBar.removeAll();
            Main.instance.getLogger().log(Level.INFO, "Removed BossBar for player: " + player.getName());
        }
    }

    public void showProgressBar(Player player, String title, double progress, double progressMax) {
        double normalizedProgress = Math.max(0.0, Math.min(1.0, progress / progressMax));
        BossBar bossBar = bossBars.get(player);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(Main.color(title), BarColor.BLUE, BarStyle.SOLID); // Default color and style
            bossBars.put(player, bossBar);
            Main.instance.getLogger().log(Level.INFO, "Created new progress bar for player: " + player.getName());
        } else {
            bossBar.setTitle(Main.color(title));
            Main.instance.getLogger().log(Level.INFO, "Updated progress bar for player: " + player.getName());
        }
        bossBar.setProgress(normalizedProgress);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public void createXPBossBar(Player player, String title, BarColor color, BarStyle style, int currentXP, int currentLevel) {
        int xpForNextLevel = Main.instance.getLevelManager().getXPForNextLevel(currentLevel);
        double normalizedProgress = (double) currentXP / xpForNextLevel;
        normalizedProgress = Math.max(0.0, Math.min(1.0, normalizedProgress));

        BossBar bossBar = bossBars.get(player);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(Main.color(title), color, style);
            bossBars.put(player, bossBar);
            Main.instance.getLogger().log(Level.INFO, "Created new XP BossBar for player: " + player.getName());
        } else {
            bossBar.setTitle(Main.color(title));
            bossBar.setColor(color);
            bossBar.setStyle(style);
            Main.instance.getLogger().log(Level.INFO, "Reusing existing XP BossBar for player: " + player.getName());
        }
        bossBar.setProgress(normalizedProgress);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public void showXPProgressBar(Player player, String title, int currentXP, int currentLevel) {
        int xpForNextLevel = Main.instance.getLevelManager().getXPForNextLevel(currentLevel);
        double normalizedProgress = (double) currentXP / xpForNextLevel;
        normalizedProgress = Math.max(0.0, Math.min(1.0, normalizedProgress));

        BossBar bossBar = bossBars.get(player);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(Main.color(title), BarColor.BLUE, BarStyle.SOLID); // Default color and style
            bossBars.put(player, bossBar);
            Main.instance.getLogger().log(Level.INFO, "Created new XP progress bar for player: " + player.getName());
        } else {
            bossBar.setTitle(Main.color(title));
            Main.instance.getLogger().log(Level.INFO, "Updated XP progress bar for player: " + player.getName());
        }
        bossBar.setProgress(normalizedProgress);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public void hideProgressBar(Player player, long delay) {
        BukkitRunnable hideTask = hideTasks.get(player);
        if (hideTask != null && !hideTask.isCancelled()) {
            hideTask.cancel();
        }

        hideTask = new BukkitRunnable() {
            @Override
            public void run() {
                BossBar bossBar = bossBars.get(player);
                if (bossBar != null) {
                    bossBar.removePlayer(player);
                    bossBar.setVisible(false);
                    Main.instance.getLogger().log(Level.INFO, "Hid progress bar for player: " + player.getName());
                }
            }
        };

        hideTasks.put(player, hideTask);
        hideTask.runTaskLater(Main.instance, delay * 20);
    }

    public void cancelTimerBar() {
        for (BukkitRunnable task : hideTasks.values()) {
            if (task != null && !task.isCancelled()) {
                task.cancel();
            }
        }
        hideTasks.clear();
        Main.instance.getLogger().log(Level.INFO, "Cancelled all BossBar hide tasks");
    }
}
