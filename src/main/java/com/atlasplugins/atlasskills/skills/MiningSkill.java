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

        // Get Mining skill XP amount
        int miningXP = main.getSkillsConfig().getInt("Skill-Settings.Mining.Mining-XP-Gain");

        // Add XP to Mining
        levelManager.addXP(p, LevelManager.Skill.MINING, miningXP);

        // Get Mining Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.MINING);
        int xp = levelManager.getXP(p, LevelManager.Skill.MINING);
        int xpToNextLevel = levelManager.getXPForNextLevel(level);

        // Get boss bar style
        String skillBarTitle = main.getSettingsConfig().getString("SkillBar.SkillBar-Style-Title");
        BarColor skillBarColor = BarColor.valueOf(main.getSettingsConfig().getString("SkillBar.SkillBar-Color"));
        BarStyle skillBarStyle = BarStyle.valueOf(main.getSettingsConfig().getString("SkillBar.SkillBar-Style"));
        long skillBarHideDelay = main.getSettingsConfig().getLong("SkillBar.SkillBar-Hide-Delay");

        // Display Skill Lvl & XP Bar
        UIManager.getBossBarManager().createXPBossBar(p, "", skillBarColor, skillBarStyle, xp, level);
        assert skillBarTitle != null;
        UIManager.getBossBarManager().showXPProgressBar(p, skillBarTitle
                        .replace("{skillName}", LevelManager.Skill.MINING.toString())
                        .replace("{skillXP}", String.valueOf(xp))
                        .replace("{skillLvl}", String.valueOf(level))
                        .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }
}
