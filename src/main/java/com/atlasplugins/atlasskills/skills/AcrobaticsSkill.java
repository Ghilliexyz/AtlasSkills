package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
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
    private WorldGuardPlugin worldGuardPlugin;

    private int xpStacked = 0;

    public AcrobaticsSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
        this.worldGuardPlugin = main.getWorldGuardPlugin();
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        EntityDamageEvent.DamageCause damageCause = e.getCause();
        double damage = e.getFinalDamage();
        // if the entity isn't a Player then return
        if(!(e.getEntity() instanceof Player p)) return;

        //WorldGuard Checks
        if(worldGuardPlugin != null && worldGuardPlugin.isEnabled() && !p.isOp())
        {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(BukkitAdapter.adapt(p.getWorld()));

            if (regions != null) {
                ApplicableRegionSet set = regions.getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));

                for (ProtectedRegion region : set) {
                    // Cancel the event if the player is in a protected region
                    e.setCancelled(true);
                    return;
                }
            }
        }


        // if the damage cause isn't from fall damage then return
        if(damageCause != EntityDamageEvent.DamageCause.FALL) return;

        // Get Skill XP amount
        int acrobaticsMinXP = main.getSkillsConfig().getInt("Skill-Settings.Acrobatics.Acrobatics-XP-Gain-Min");
        int acrobaticsMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Acrobatics.Acrobatics-XP-Gain-Max");

        // Gets a random int between the Min and Max XP Values
        Random random = new Random();
        int acrobaticsXP = acrobaticsMinXP + random.nextInt(acrobaticsMaxXP - acrobaticsMinXP + 1);

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Get the final XP amount
        int finalXPGained = (int) ((acrobaticsXP * damage) * xpMultiplier);

        if(main.getSettingsConfig().getBoolean("SkillBar.SkillBar-XPStacking")){
            // Get the current xp stack
            xpStacked += finalXPGained;
        }else {
            xpStacked = finalXPGained;
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.ACROBATICS, finalXPGained);

        // Get Skill Stats
        int level = levelManager.getLevel(p.getUniqueId(), LevelManager.Skill.ACROBATICS);
        int xp = levelManager.getXP(p.getUniqueId(), LevelManager.Skill.ACROBATICS);
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
                .replace("{skillName}", levelManager.ReformatName(LevelManager.Skill.ACROBATICS.toString()))
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
