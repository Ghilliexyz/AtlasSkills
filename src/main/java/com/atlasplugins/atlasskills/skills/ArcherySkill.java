package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Random;

public class ArcherySkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    private int xpStacked = 0;

    public ArcherySkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent e) {
        Entity hitEntity = e.getHitEntity();
        Projectile projectile = e.getEntity();

        // if the entity isn't a player then return
        if(!(e.getEntity().getShooter() instanceof Player p)) return;

        // if the entity is not living then return
        if(!(hitEntity instanceof LivingEntity)) return;

        // if the entity is an ArmorStand then return
        if ((hitEntity instanceof ArmorStand)) return;

        // if the projectile is not an arrow then return
        if(!(projectile instanceof Arrow)) return;

        // Get Skill XP amount
        int archeryMinXP = main.getSkillsConfig().getInt("Skill-Settings.Archery.Archery-XP-Gain-Min");
        int archeryMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Archery.Archery-XP-Gain-Max");

        // Gets a random int between the Min and Max XP Values
        Random random = new Random();
        int archeryXP = archeryMinXP + random.nextInt(archeryMaxXP - archeryMinXP + 1);

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Get the final XP amount
        int finalXPGained = archeryXP * xpMultiplier;

        if(main.getSettingsConfig().getBoolean("SkillBar.SkillBar-XPStacking")){
            // Get the current xp stack
            xpStacked += finalXPGained;
        }else {
            xpStacked = finalXPGained;
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.ARCHERY, finalXPGained);

        // Get Skill Stats
        int level = levelManager.getLevel(p.getUniqueId(), LevelManager.Skill.ARCHERY);
        int xp = levelManager.getXP(p.getUniqueId(), LevelManager.Skill.ARCHERY);
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
                .replace("{skillName}", levelManager.ReformatName(LevelManager.Skill.ARCHERY.toString()))
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
