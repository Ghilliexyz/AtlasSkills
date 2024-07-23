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

public class SwordSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    public SwordSkill(Main main) {
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
        boolean xpDamageToggle = main.getSkillsConfig().getBoolean("Skill-Settings.Swords.Swords-XP-Toggle");

        int swordXP = 0;
        if(xpDamageToggle) {
            // Get Skill XP amount
            int swordMinXP = main.getSkillsConfig().getInt("Skill-Settings.Swords.Swords-XP-Gain-Min");
            int swordMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Swords.Swords-XP-Gain-Max");

            // Gets a random int between the Min and Max XP Values
            Random random = new Random();
            swordXP = swordMinXP + random.nextInt(swordMaxXP - swordMinXP + 1);
        }else{
            // Calculate how much XP based on damage
            swordXP = (int) damageDealt;
        }

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.SWORDS, swordXP * xpMultiplier);

        // Get Skill Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.SWORDS);
        int xp = levelManager.getXP(p, LevelManager.Skill.SWORDS);
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
                .replace("{skillName}", LevelManager.Skill.SWORDS.toString())
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }

    public Boolean hasCorrectTool(Material tool)
    {
        switch (tool){
            case Material.WOODEN_SWORD:
                return true;
            case Material.STONE_SWORD:
                return true;
            case Material.IRON_SWORD:
                return true;
            case Material.GOLDEN_SWORD:
                return true;
            case Material.DIAMOND_SWORD:
                return true;
            case Material.NETHERITE_SWORD:
                return true;
            default:
                return false;
        }
    }
}
