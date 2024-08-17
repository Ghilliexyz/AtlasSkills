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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class HerbalismSkill implements Listener {

    private static final Logger log = LogManager.getLogger(HerbalismSkill.class);
    private Main main;
    private LevelManager levelManager;
    private WorldGuardPlugin worldGuardPlugin;

    private List<String> CROPS;

    public HerbalismSkill(Main main) {
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
        if (hasIncorrectTool(tool.getType())) return;

        // Get the list of blocks that work with this skill
        CROPS = main.getSkillsConfig().getStringList("Skill-Settings.Herbalism.Herbalism-Block-List");

        // if the block broken doesn't match with the list then return
        if (!CROPS.contains(blockBroken.getType().toString())) return;

        // return if the crop is not at max age.
//        if(blockBroken.getState().getBlockData() instanceof Ageable blockAgeable)
//        {
//            main.getLogger().info("Block data: " + blockAgeable);
//            if(blockAgeable.getAge() < blockAgeable.getMaximumAge())
//            {
//                return;
//            }
//        }

        // Get Skill XP amount
        int herbalismMinXP = main.getSkillsConfig().getInt("Skill-Settings.Herbalism.Herbalism-XP-Gain-Min");
        int herbalismMaxXP = main.getSkillsConfig().getInt("Skill-Settings.Herbalism.Herbalism-XP-Gain-Max");

        // Gets a random int between the Min and Max XP Values
        Random random = new Random();
        int herbalismXP = herbalismMinXP + random.nextInt(herbalismMaxXP - herbalismMinXP + 1);

        // get xp multiplier
        int xpMultiplier = main.getSkillsConfig().getInt("Skill-Addons.Skill-XP-Multiplier.Skill-XP-Multiplier-Amount");

        int finalXP = herbalismXP;
        if(hasAboveBlock(blockBroken.getType()))
        {
            finalXP = (herbalismXP * countBlocksAbove(blockBroken));
        }
        if(hasBelowBlock(blockBroken.getType()))
        {
            finalXP = (herbalismXP * countBlocksBelow(blockBroken));
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.HERBALISM, finalXP * xpMultiplier);

        // Get Skill Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.HERBALISM);
        int xp = levelManager.getXP(p, LevelManager.Skill.HERBALISM);
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
                .replace("{skillName}", levelManager.ReformatName(LevelManager.Skill.HERBALISM.toString()))
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }

    // Method to check the number of blocks above the given block that are of the same type
    public static int countBlocksAbove(Block startBlock) {
        Material blockType = startBlock.getType();
        if (!hasAboveBlock(blockType)) {return 1;}

        int count = 1;
        Block currentBlock = startBlock.getRelative(BlockFace.UP);

        while (currentBlock.getType() == blockType) {
            count++;
            currentBlock = currentBlock.getRelative(BlockFace.UP);
        }

        return count;
    }

    // Method to check the number of vine blocks below the given block
    public static int countBlocksBelow(Block startBlock) {
        Material blockType = startBlock.getType();
        if (!hasBelowBlock(blockType)) {return 1;}

        int count = 1;
        Block currentBlock = startBlock.getRelative(BlockFace.DOWN);

        while (currentBlock.getType() == blockType) {
            count++;
            currentBlock = currentBlock.getRelative(BlockFace.DOWN);
        }

        return count;
    }

    public static boolean hasAboveBlock(Material startBlock){
        switch (startBlock) {
            case Material.SUGAR_CANE:
                return true;
            case Material.CACTUS:
                return true;
            case Material.TWISTING_VINES:
                return true;
            case Material.TWISTING_VINES_PLANT:
                return true;
            default:
                return false;
        }
    }
    public static boolean hasBelowBlock(Material startBlock){
        switch (startBlock) {
            case Material.VINE:
                return true;
            case Material.WEEPING_VINES:
                return true;
            case Material.WEEPING_VINES_PLANT:
                return true;
            default:
                return false;
        }
    }

    public Boolean hasIncorrectTool(Material tool) {
        switch (tool) {
            case Material.WOODEN_PICKAXE:
                return true;
            case Material.STONE_PICKAXE:
                return true;
            case Material.IRON_PICKAXE:
                return true;
            case Material.GOLDEN_PICKAXE:
                return true;
            case Material.DIAMOND_PICKAXE:
                return true;
            case Material.NETHERITE_PICKAXE:
                return true;
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