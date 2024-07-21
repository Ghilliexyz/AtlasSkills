package com.atlasplugins.atlasskills.managers.uiapi;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Map;

public class ScoreboardManagerr {
    private final ScoreboardManager manager;
    private final Scoreboard scoreboard;

    public ScoreboardManagerr() {
        this.manager = Bukkit.getScoreboardManager();
        this.scoreboard = manager.getNewScoreboard();
    }

    public void createScoreboard(Player player, String title, Map<String, Integer> scores) {
        Objective objective = scoreboard.getObjective(title);
        if (objective == null) {
            objective = scoreboard.registerNewObjective(Main.color(title), "dummy", Main.color(title));
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Objective finalObjective = objective;
        scores.forEach((name, score) -> {
            finalObjective.getScore(name).setScore(score);
        });

        player.setScoreboard(scoreboard);
    }

    public void clearScoreboard(Player player) {
        player.setScoreboard(manager.getMainScoreboard());
    }

    public void createProgressBar(Player player, String title, int current, int max, String progressColor, String nonProgressColor) {
        Objective objective = scoreboard.getObjective(title);
        if (objective == null) {
            objective = scoreboard.registerNewObjective(Main.color(title), "dummy", Main.color(title));
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int percent = (int) (((double) current / max) * 100);
        String progressBar = getProgressBar(current, max, 20, '|', progressColor, nonProgressColor);

        objective.getScore("Progress:").setScore(percent);
        objective.getScore(progressBar).setScore(1);

        player.setScoreboard(scoreboard);
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

