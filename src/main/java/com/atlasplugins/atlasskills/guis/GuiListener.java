package com.atlasplugins.atlasskills.guis;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.Material;
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

        // Get the inventory title
        String title = event.getView().getTitle();
        // Get the player who clicked
        Player player = (Player) event.getWhoClicked();

        // Get the Skill Menu title from the config
        String skillMenuTitle = Main.color(main.getSkillsConfig().getString("Skills-Gui.Skills-Menu.Skills-Menu-Title"));
        // Check if the clicked inventory matches your custom GUI title
        if (title.equals(Main.color(skillMenuTitle))) {
            // Check if the clicked inventory is the custom GUI, not the player's inventory
            if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
                event.setCancelled(true); // Cancel the event to prevent normal behavior

                // Handle clicks within your custom GUI
//            if (event.getSlot() == 10) {
//                player.sendMessage(Main.color("&c&lACROBATICS!!!!!!!!!!!! ECHO.... 5x"));
//            }
            }
        }

        // ======= LEADERBOARD GUI ======= \\
        // Get the Skill Menu title from the config
        int currentPage = getCurrentPageFromTitle(title);
        String leaderboardMenuTitle = Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-Title").replace("{CurrentPage}", String.valueOf(currentPage + 1)));

        // Check if the clicked inventory matches your custom GUI title
        if (title.equals(Main.color(leaderboardMenuTitle))) {
            // Check if the clicked inventory is the custom GUI, not the player's inventory
            if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
                event.setCancelled(true); // Cancel the event to prevent normal behavior

                if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getType() == Material.ARROW) {
                    String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

                    if (itemName.equals(Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-NextPage-Title")))) {
                        // Open next page
                        new LeaderboardGui(player, main, main.getLevelManager().getAllPlayerData(), currentPage + 1).open();
                    } else if (itemName.equals(Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-PreviousPage-Title")))) {
                        // Open previous page
                        new LeaderboardGui(player, main, main.getLevelManager().getAllPlayerData(), currentPage - 1).open();
                    }
                }
            }
        }
    }

    private int getCurrentPageFromTitle(String title) {
        // Remove Minecraft color/formatting codes
        String cleanTitle = title.replaceAll("§[0-9a-fk-orA-FK-OR]", "").replaceAll("&[0-9a-fk-orA-FK-OR]", "");

        String[] parts = cleanTitle.split(" ");

        // Ensure the title has at least two parts and the last part is a number
        if (parts.length > 1 && isNumeric(parts[parts.length - 1])) {
            return Integer.parseInt(parts[parts.length - 1]) - 1;
        }

        // Default to page 0 if no valid page number is found
        return 0;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

