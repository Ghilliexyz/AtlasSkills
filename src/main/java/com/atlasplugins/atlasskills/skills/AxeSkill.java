package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AxeSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    private int xpStacked = 0;

    public AxeSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Entity damagedEntity = e.getEntity();
        double damageDealt = e.getDamage();

        // if the damager is not a player return
        if(!(e.getDamager() instanceof Player p)) return;

        ItemStack tool = p.getInventory().getItemInMainHand();

        // if the player doesn't have the correct tool return
        if(!hasCorrectTool(tool.getType())) return;

        // if the entity is not living then return
        if(!(damagedEntity instanceof LivingEntity)) return;

        // if the entity is an ArmorStand then return
        if ((damagedEntity instanceof ArmorStand)) return;

        // get xp damage toggle
        boolean xpDamageToggle = main.getSkillsConfig().getBoolean("Skill-Settings.Axes.Axes-XP-Toggle");

        int axeXP = 0;
        if(xpDamageToggle) {
            // Get Skill XP amount
            int axesMinXP = main.getSkillsConfig().getInt("Skill-Settings.Axes.Axes-XP-Gain-Min");
            int axesMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Axes.Axes-XP-Gain-Max");

            // Gets a random int between the Min and Max XP Values
            Random random = new Random();
            axeXP = axesMinXP + random.nextInt(axesMaxXP - axesMinXP + 1);
        }else{
            // Calculate how much XP based on damage
            axeXP = (int) damageDealt;
        }

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Get the final XP amount
        int finalXPGained = axeXP * xpMultiplier;

        if(main.getSettingsConfig().getBoolean("SkillBar.SkillBar-XPStacking")){
            // Get the current xp stack
            xpStacked += finalXPGained;
        }else {
            xpStacked = finalXPGained;
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.AXES, finalXPGained);

        // Get Skill Stats
        int level = levelManager.getLevel(p.getUniqueId(), LevelManager.Skill.AXES);
        int xp = levelManager.getXP(p.getUniqueId(), LevelManager.Skill.AXES);
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
                .replace("{skillName}", levelManager.ReformatName(LevelManager.Skill.AXES.toString()))
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

    public Boolean hasCorrectTool(Material tool)
    {
        switch (tool){
            case Material.WOODEN_AXE:
                return true;
            case Material.STONE_AXE:
                return true;
            case Material.IRON_AXE:
                return true;
            case Material.GOLDEN_AXE:
                return true;
            case Material.DIAMOND_AXE:
                return true;
            case Material.NETHERITE_AXE:
                return true;
            default:
                return false;
        }
    }
}
