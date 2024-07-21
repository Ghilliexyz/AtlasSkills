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

public class BossBarManager {
    private final Map<Player, BossBar> bossBars = new HashMap<>();
    private BossBar bossBar;
    private BukkitRunnable currentTask;

    public void createBossBar(Player player, String title, BarColor color, BarStyle style, double BarProgress) {
        if(bossBar != null)
        {
            bossBar.removeAll();
            bossBar = Bukkit.createBossBar(Main.color(title), color, style);
            bossBar.addPlayer(player);
            bossBar.setProgress(BarProgress);
            bossBars.put(player, bossBar);
        }else{
            bossBar = Bukkit.createBossBar(Main.color(title), color, style);
            bossBar.addPlayer(player);
            bossBar.setProgress(BarProgress);
            bossBars.put(player, bossBar);
        }
    }

    public void removeBossBar(Player player) {
        bossBars.remove(player);
        if (bossBar != null) {
            bossBar.removeAll();
        }
    }

    public void showProgressBar(Player player, String title, double progress, double progressMax) {
        double normalizedProgress = Math.max(0.0, Math.min(1.0, progress / progressMax));
        bossBar.setTitle(Main.color(title));
        bossBar.setProgress(normalizedProgress); // Progress is now between 0.0 and 1.0
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public void createXPBossBar(Player player, String title, BarColor color, BarStyle style, int currentXP, int currentLevel) {
        int xpForNextLevel = Main.instance.getLevelManager().getXPForNextLevel(currentLevel); // XP required for the next level
        double normalizedProgress = (double) currentXP / xpForNextLevel; // Progress as a fraction of XP needed for the next level

        // Ensure progress is within [0.0, 1.0]
        normalizedProgress = Math.max(0.0, Math.min(1.0, normalizedProgress));

        if(bossBar != null)
        {
            bossBar.addPlayer(player);
            bossBar.setProgress(normalizedProgress);
            bossBars.put(player, bossBar);
        }else{
            bossBar = Bukkit.createBossBar(Main.color(title), color, style);
            bossBar.addPlayer(player);
            bossBar.setProgress(normalizedProgress);
            bossBars.put(player, bossBar);
        }
    }

    public void showXPProgressBar(Player player, String title, int currentXP, int currentLevel) {
        int xpForNextLevel = Main.instance.getLevelManager().getXPForNextLevel(currentLevel); // XP required for the next level
        double normalizedProgress = (double) currentXP / xpForNextLevel; // Progress as a fraction of XP needed for the next level

        // Ensure progress is within [0.0, 1.0]
        normalizedProgress = Math.max(0.0, Math.min(1.0, normalizedProgress));

        bossBar.setTitle(Main.color(title));
        bossBar.setProgress(normalizedProgress); // Set progress to the normalized value
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
    }

    public void hideProgressBar(Player player, long delay) {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
        }

        currentTask = new BukkitRunnable() {
            @Override
            public void run() {
                // Code to execute after the delay
                bossBar.removePlayer(player);
                bossBar.setVisible(false);
                // Add any additional code you want to execute here
            }
        };currentTask.runTaskLater(Main.instance, delay * 20); // delayInSeconds * 20 ticks = delayInSeconds seconds
    }

    public void cancelTimerBar() {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            currentTask = null;
        }
    }
}
