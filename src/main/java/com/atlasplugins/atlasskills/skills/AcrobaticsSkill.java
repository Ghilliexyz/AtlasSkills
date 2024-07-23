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
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class AcrobaticsSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    public AcrobaticsSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        EntityDamageEvent.DamageCause damageCause = e.getCause();
        double damage = e.getFinalDamage();
        // if the entity isn't a Player then return
        if(!(e.getEntity() instanceof Player p)) return;


        // if the damage cause isn't from fall damage then return
        if(damageCause != EntityDamageEvent.DamageCause.FALL) return;

        // Get Skill XP amount
        int acrobaticsMinXP = main.getSkillsConfig().getInt("Skill-Settings.Acrobatics.Acrobatics-XP-Gain-Min");
        int acrobaticsMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Acrobatics.Acrobatics-XP-Gain-Max");

        // Gets a random int between the Min and Max XP Values
        Random random = new Random();
        int acrobaticsXP = acrobaticsMinXP + random.nextInt(acrobaticsMaxXP - acrobaticsMinXP + 1);

        // Get the final XP amount
        double finalXP = acrobaticsXP * damage;

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.ACROBATICS, (int) finalXP * xpMultiplier);

        // Get Skill Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.ACROBATICS);
        int xp = levelManager.getXP(p, LevelManager.Skill.ACROBATICS);
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
                .replace("{skillName}", LevelManager.Skill.ACROBATICS.toString())
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }
}
