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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ExcavationSkill implements Listener {

    private Main main;
    private LevelManager levelManager;
    private WorldGuardPlugin worldGuardPlugin;

    private int xpStacked = 0;

    public ExcavationSkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
        this.worldGuardPlugin = main.getWorldGuardPlugin();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Block blockBroken = e.getBlock();
        Player p = e.getPlayer();
        ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();

        //WorldGuard Checks
        if(worldGuardPlugin != null && worldGuardPlugin.isEnabled() && !p.isOp())
        {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(BukkitAdapter.adapt(blockBroken.getWorld()));

            if (regions != null) {
                ApplicableRegionSet set = regions.getApplicableRegions(BukkitAdapter.asBlockVector(blockBroken.getLocation()));

                for (ProtectedRegion region : set) {
                    if (!region.getMembers().contains(worldGuardPlugin.wrapPlayer(p)) &&
                            !region.getOwners().contains(worldGuardPlugin.wrapPlayer(p))) {
                        // Cancel the event if the player is not a member or owner of the region
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }

        // if the player doesn't have the correct tool return
        if(!hasCorrectTool(tool.getType())) return;

        // Get Skill XP amount
        int excavationMinXP = main.getSkillsConfig().getInt("Skill-Settings.Excavation.Excavation-XP-Gain-Min");
        int excavationMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Excavation.Excavation-XP-Gain-Max");

        // Gets a random int between the Min and Max XP Values
        Random random = new Random();
        int excavationXP = excavationMinXP + random.nextInt(excavationMaxXP - excavationMinXP + 1);

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        // Get the final XP amount
        int finalXPGained = excavationXP * xpMultiplier;

        if(main.getSettingsConfig().getBoolean("SkillBar.SkillBar-XPStacking")){
            // Get the current xp stack
            xpStacked += finalXPGained;
        }else {
            xpStacked = finalXPGained;
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.EXCAVATION, finalXPGained);

        // Get Skill Stats
        int level = levelManager.getLevel(p.getUniqueId(), LevelManager.Skill.EXCAVATION);
        int xp = levelManager.getXP(p.getUniqueId(), LevelManager.Skill.EXCAVATION);
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
                .replace("{skillName}", levelManager.ReformatName(LevelManager.Skill.EXCAVATION.toString()))
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

