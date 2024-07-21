package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    public MiningSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Block blockBroken = e.getBlock();
        Player p = e.getPlayer();

        // Add XP to Mining
        levelManager.addXP(p, LevelManager.Skill.MINING, 5);

        // Get Mining Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.MINING);
        int xp = levelManager.getXP(p, LevelManager.Skill.MINING);
        int xpToNextLevel = levelManager.getXPForNextLevel(level);

        // Display Mining Lvl & XP Bar
        UIManager.getBossBarManager().createXPBossBar(p, "", BarColor.WHITE, BarStyle.SEGMENTED_10, xp, level);
        UIManager.getBossBarManager().showXPProgressBar(p, "&c&lSKILL &7Mining lvl: &e" + level + " &7| xp: &e" + xp + " &7/ &e" + xpToNextLevel, xp, level);

        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, 2);
    }
}
