package com.atlasplugins.atlasskills.skills;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AlchemySkill implements Listener {

    private Main main;
    private LevelManager levelManager;

    public AlchemySkill(Main main) {
        this.main = main;
        this.levelManager = main.getLevelManager();
    }

    public static HashMap<Location, Player> brewingStandInteractions = new HashMap<>();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        InventoryType inventoryType = e.getInventory().getType();
        Player p = (Player) e.getWhoClicked();

        if(inventoryType != InventoryType.BREWING) return;

        brewingStandInteractions.put(e.getInventory().getLocation(), p);
    }

    @EventHandler
    public void onBrewEvent(BrewEvent e) {
        Player p = brewingStandInteractions.get(e.getBlock().getLocation());

        // if the player hasn't brewed anything return
        if(p == null) return;

        ItemStack potionSlot0 = e.getContents().getItem(0);
        ItemStack potionSlot1 = e.getContents().getItem(1);
        ItemStack potionSlot2 = e.getContents().getItem(2);


        // Get Skill XP amount
        int alchemyXP = main.getSkillsConfig().getInt("Skill-Settings.Alchemy.Alchemy-XP-Gain");

        // Gets the final amount of XP
        int finalXP = 0;

        if(potionSlot0!= null && potionSlot0.getType() != Material.AIR)
        {
            finalXP++;
        }
        if(potionSlot1 != null && potionSlot1.getType() != Material.AIR)
        {
            finalXP++;
        }
        if(potionSlot2 != null && potionSlot2.getType() != Material.AIR)
        {
            finalXP++;
        }

        // Add XP to Skill
        levelManager.addXP(p, LevelManager.Skill.ALCHEMY, alchemyXP * finalXP);

        // Get Skill Stats
        int level = levelManager.getLevel(p, LevelManager.Skill.ALCHEMY);
        int xp = levelManager.getXP(p, LevelManager.Skill.ALCHEMY);
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
                .replace("{skillName}", LevelManager.Skill.ALCHEMY.toString())
                .replace("{skillXP}", String.valueOf(xp))
                .replace("{skillLvl}", String.valueOf(level))
                .replace("{skillXPToNextLevel}", String.valueOf(xpToNextLevel)), xp, level);


        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, skillBarHideDelay);
    }
}