package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ExcavationSkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    public ExcavationSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Block blockBroken = e.getBlock();
        Player p = e.getPlayer();
        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();

        // if the player doesn't have the correct tool return
        if(!hasCorrectTool(tool.getType())) return;

        // Get Skill XP amount
        int excavationXP = main.getSkillsConfig().getInt("Skill-Settings.Excavation.Excavation-XP-Gain");

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.EXCAVATION, excavationXP * xpMultiplier);

        // Get Skill Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.EXCAVATION);
        int xp = levelManager.getXP(p, LevelManager.Skill.EXCAVATION);
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
                .replace("{skillName}", LevelManager.Skill.EXCAVATION.toString())
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }

    public Boolean hasCorrectTool(Material tool)
    {
        switch (tool){
            case Material.WOODEN_SHOVEL:
                return true;
            case Material.STONE_SHOVEL:
                return true;
            case Material.IRON_SHOVEL:
                return true;
            case Material.GOLDEN_SHOVEL:
                return true;
            case Material.DIAMOND_SHOVEL:
                return true;
            case Material.NETHERITE_SHOVEL:
                return true;
            default:
                return false;
        }
    }
}

