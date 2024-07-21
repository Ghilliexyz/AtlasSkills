package com.atlasplugins.atlasskills.managers.uiapi;

import com.atlasplugins.atlasskills.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarManager {

    private boolean hasCompleted;
    private BukkitRunnable currentTask;

    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Main.color(message)));
    }

    public void showProgressBar(Player player, String title, int current, int max, String progressColor, String nonProgressColor) {
        int percent = (int) (((double) current / max) * 100);
        String progressBar = getProgressBar(current, max, 20, '|', progressColor, nonProgressColor);
        sendActionBar(player, Main.color(title) + progressBar + " " + Main.color("&e" + percent) + "%");
    }

    public void createTimerBar(Player p, String completedTitle, String loadingTitle, int seconds, String progressColor, String nonProgressColor) {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
        }

        currentTask = new BukkitRunnable() {
            int timeLeft = 0;

            @Override
            public void run() {
                if (timeLeft >= seconds) {
                    sendActionBar(p, Main.color(completedTitle));
                    hasCompleted = true;
                    cancel();
                    return;
                }
                showProgressBar(p, Main.color(loadingTitle), timeLeft, seconds, progressColor, nonProgressColor);
                timeLeft++;
            }
        };
        currentTask.runTaskTimer(Main.instance, 0, 20); // Update every second
    }

    public void cancelTimerBar() {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
            currentTask = null;
            hasCompleted = false; // Reset the completion status if needed
        }
    }

    public boolean getHasCompleted() {
        return hasCompleted;
    }

    private String getProgressBar(int current, int max, int totalBars, char symbol, String progressColor, String nonProgressColor) {
        int progressBars = (int) (((double) current / max) * totalBars);
        int leftOver = totalBars - progressBars;

        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < progressBars; i++) {
            bar.append(Main.color(progressColor)).append(symbol); // §a is green
        }
        for (int i = 0; i < leftOver; i++) {
            bar.append(Main.color(nonProgressColor)).append(symbol); // §7 is gray
        }
        return bar.toString();
    }
}
