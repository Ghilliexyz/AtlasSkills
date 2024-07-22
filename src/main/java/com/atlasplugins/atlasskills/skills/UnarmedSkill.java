package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class UnarmedSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    public UnarmedSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Entity damagedEntity = e.getEntity();
        double damageDealt = e.getDamage();

        // if the damager is not a player return
        if (!(e.getDamager() instanceof Player p)) return;

        ItemStack tool = p.getInventory().getItemInMainHand();

        // if the player doesn't have the correct tool return
        if (!hasCorrectTool(tool.getType())) return;

        // if the entity is not living then return
        if (!(damagedEntity instanceof LivingEntity)) return;

        // get xp damage toggle
        boolean xpDamageToggle = main.getSkillsConfig().getBoolean("Skill-Settings.Unarmed.Unarmed-XP-Toggle");

        int swordXP = 0;
        if (xpDamageToggle) {
            // Get Skill XP Fixed amount
            swordXP = main.getSkillsConfig().getInt("Skill-Settings.Unarmed.Unarmed-XP-Gain");
        } else {
            // Calculate how much XP based on damage
            swordXP = (int) damageDealt;
        }

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.UNARMED, swordXP * xpMultiplier);

        // Get Skill Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.UNARMED);
        int xp = levelManager.getXP(p, LevelManager.Skill.UNARMED);
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
                .replace("{skillName}", LevelManager.Skill.UNARMED.toString())
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }

    public Boolean hasCorrectTool(Material tool) {
        switch (tool) {
            case Material.AIR:
                return true;
            default:
                return false;
        }
    }
}
