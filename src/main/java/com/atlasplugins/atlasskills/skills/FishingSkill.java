package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.Random;

public class FishingSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    private int xpStacked = 0;

    public FishingSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent e) {
        Entity caughtEntity = e.getCaught();
        Player p = e.getPlayer();

        // if the player hasn't caught a fish then return
        if(!(e.getState() == PlayerFishEvent.State.CAUGHT_FISH)) return;

        // Get Skill XP amount
        int fishingMinXP = main.getSkillsConfig().getInt("Skill-Settings.Fishing.Fishing-XP-Gain-Min");
        int fishingMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Fishing.Fishing-XP-Gain-Max");

        // Gets a random int between the Min and Max XP Values
        Random random = new Random();
        int fishingXP = fishingMinXP + random.nextInt(fishingMaxXP - fishingMinXP + 1);

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Get the final XP amount
        int finalXPGained = fishingXP * xpMultiplier;

        if(main.getSettingsConfig().getBoolean("SkillBar.SkillBar-XPStacking")){
            // Get the current xp stack
            xpStacked += finalXPGained;
        }else {
            xpStacked = finalXPGained;
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.FISHING, finalXPGained);

        // Get Skill Stats
        int level = levelManager.getLevel(p.getUniqueId(), LevelManager.Skill.FISHING);
        int xp = levelManager.getXP(p.getUniqueId(), LevelManager.Skill.FISHING);
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
                .replace("{skillName}", levelManager.ReformatName(LevelManager.Skill.FISHING.toString()))
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillGainedXP}", String.valueOf(xpStacked))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay, () -> {
            // Reset the xp stack when the bossbar has been hidden.
            xpStacked = 0;
        });
    }
}