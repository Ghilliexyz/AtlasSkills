package com.atlasplugins.atlasskills.guis;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {

    private Main main;

    public GuiListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) { // Ensure it's a custom GUI
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            // Handle different GUIs based on their title or other identifiers
            String SkillMenuTitle = main.getSkillsConfig().getString("Skills-Gui.Skills-Menu.Skills-Menu-Title");
            String title = event.getView().getTitle();
            if (title.equals(SkillMenuTitle)) {
                // Handle clicks for the Stats GUI
                if (event.getSlot() == 10) {
                    player.sendMessage("ACROBATICS!!!!!!!!!!!! ECHO.... 5x");
                }
            }
        }
    }
}

