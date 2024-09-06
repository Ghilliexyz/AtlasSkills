package com.atlasplugins.atlasskills.guis;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.managers.levelsystem.PlayerSkillData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LeaderboardGui extends Gui {

    private Main main;
    private List<PlayerSkillData> playerDataList; // Store the list of player data
    private int currentPage;
    private static final int ITEMS_PER_PAGE = 45; // Number of items per page, leaving space for nav buttons
    private int totalPages;

    public LeaderboardGui(Player player, Main main, List<PlayerSkillData> playerDataList, int page) {
        super(player, Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-Title")
                .replace("{CurrentPage}", String.valueOf(page + 1))), 54);
        this.playerDataList = playerDataList;
        this.currentPage = page;
        this.main = main;
        setupItems(); // Setup the items in the inventory
    }

    @Override
    public void setupItems() {

        int placement = 0;
        int totalLevel = 0;
        int totalXp = 0;

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, playerDataList.size());

        int totalItems = playerDataList.size();
        totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        for (int i = 0; i < totalItems; i++){
            PlayerSkillData data = playerDataList.get(i);
            // Get the player's UUID
            UUID playerUUID = data.getUuid();

            if(player.getUniqueId().equals(playerUUID))
            {
                placement = i + 1;
                totalLevel = data.getTotalLevel();
                totalXp = data.getTotalXP();
            }
        }

        for (int i = startIndex; i < endIndex; i++) {
            PlayerSkillData data = playerDataList.get(i);
            // Get the player's UUID
            UUID playerUUID = data.getUuid();

            // Get the player's head using the UUID

            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta playerSkullMeta = (SkullMeta) playerHead.getItemMeta();

            PlayerProfile playerSkullProfile = getProfile(playerUUID);
            playerSkullProfile.update();

            playerSkullMeta.setOwnerProfile(playerSkullProfile);

            int acrobaticsLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.ACROBATICS);
            int acrobaticsXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.ACROBATICS);
            int acrobaticsXpToNextLvl = main.getLevelManager().getXPForNextLevel(acrobaticsLevel);

            int alchemyLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.ALCHEMY);
            int alchemyXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.ALCHEMY);
            int alchemyXpToNextLvl = main.getLevelManager().getXPForNextLevel(alchemyLevel);

            int archeryLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.ARCHERY);
            int archeryXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.ARCHERY);
            int archeryXpToNextLvl = main.getLevelManager().getXPForNextLevel(archeryLevel);

            int axesLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.AXES);
            int axesXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.AXES);
            int axesXpToNextLvl = main.getLevelManager().getXPForNextLevel(axesLevel);

            int excavationLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.EXCAVATION);
            int excavationXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.EXCAVATION);
            int excavationXpToNextLvl = main.getLevelManager().getXPForNextLevel(excavationLevel);

            int fishingLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.FISHING);
            int fishingXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.FISHING);
            int fishingXpToNextLvl = main.getLevelManager().getXPForNextLevel(fishingLevel);

            int herbalismLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.HERBALISM);
            int herbalismXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.HERBALISM);
            int herbalismXpToNextLvl = main.getLevelManager().getXPForNextLevel(herbalismLevel);

            int miningLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.MINING);
            int miningXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.MINING);
            int miningXpToNextLvl = main.getLevelManager().getXPForNextLevel(miningLevel);

            int swordLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.SWORDS);
            int swordXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.SWORDS);
            int swordXpToNextLvl = main.getLevelManager().getXPForNextLevel(swordLevel);

            int tamingLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.TAMING);
            int tamingXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.TAMING);
            int tamingXpToNextLvl = main.getLevelManager().getXPForNextLevel(tamingLevel);

            int unarmedLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.UNARMED);
            int unarmedXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.UNARMED);
            int unarmedXpToNextLvl = main.getLevelManager().getXPForNextLevel(unarmedLevel);

            int woodcuttingLevel = main.getLevelManager().getDataBaseLevel(data.getUuid(), LevelManager.Skill.WOODCUTTING);
            int woodcuttingXp = main.getLevelManager().getDataBaseXP(data.getUuid(), LevelManager.Skill.WOODCUTTING);
            int woodcuttingXpToNextLvl = main.getLevelManager().getXPForNextLevel(woodcuttingLevel);

            String displayName = Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Player-Skill-Styling.Player-Title")
                    .replace("{PlayerName}", data.getName())
                    .replace("{PlayerLeaderboardPlacement}", String.valueOf(i + 1))
                    .replace("{PlayerTotalLvl}", String.valueOf(data.getTotalLevel()))
                    .replace("{PlayerTotalXp}", String.valueOf(data.getTotalXP()))
                    .replace("{PlayerAcrobaticsLvl}", String.valueOf(acrobaticsLevel))
                    .replace("{PlayerAcrobaticsXP}", String.valueOf(acrobaticsXp))
                    .replace("{PlayerAcrobaticsXpToNextLvl}", String.valueOf(acrobaticsXpToNextLvl))
                    .replace("{PlayerAlchemyLvl}", String.valueOf(alchemyLevel))
                    .replace("{PlayerAlchemyXP}", String.valueOf(alchemyXp))
                    .replace("{PlayerAlchemyXpToNextLvl}", String.valueOf(alchemyXpToNextLvl))
                    .replace("{PlayerArcheryLvl}", String.valueOf(archeryLevel))
                    .replace("{PlayerArcheryXP}", String.valueOf(archeryXp))
                    .replace("{PlayerArcheryXpToNextLvl}", String.valueOf(archeryXpToNextLvl))
                    .replace("{PlayerAxeLvl}", String.valueOf(axesLevel))
                    .replace("{PlayerAxeXP}", String.valueOf(axesXp))
                    .replace("{PlayerAxeXpToNextLvl}", String.valueOf(axesXpToNextLvl))
                    .replace("{PlayerExcavationLvl}", String.valueOf(excavationLevel))
                    .replace("{PlayerExcavationXP}", String.valueOf(excavationXp))
                    .replace("{PlayerExcavationXpToNextLvl}", String.valueOf(excavationXpToNextLvl))
                    .replace("{PlayerFishingLvl}", String.valueOf(fishingLevel))
                    .replace("{PlayerFishingXP}", String.valueOf(fishingXp))
                    .replace("{PlayerFishingXpToNextLvl}", String.valueOf(fishingXpToNextLvl))
                    .replace("{PlayerHerbalismLvl}", String.valueOf(herbalismLevel))
                    .replace("{PlayerHerbalismXP}", String.valueOf(herbalismXp))
                    .replace("{PlayerHerbalismXpToNextLvl}", String.valueOf(herbalismXpToNextLvl))
                    .replace("{PlayerMiningLvl}", String.valueOf(miningLevel))
                    .replace("{PlayerMiningXP}", String.valueOf(miningXp))
                    .replace("{PlayerMiningXpToNextLvl}", String.valueOf(miningXpToNextLvl))
                    .replace("{PlayerSwordLvl}", String.valueOf(swordLevel))
                    .replace("{PlayerSwordXP}", String.valueOf(swordXp))
                    .replace("{PlayerSwordXpToNextLvl}", String.valueOf(swordXpToNextLvl))
                    .replace("{PlayerTamingLvl}", String.valueOf(tamingLevel))
                    .replace("{PlayerTamingXP}", String.valueOf(tamingXp))
                    .replace("{PlayerTamingXpToNextLvl}", String.valueOf(tamingXpToNextLvl))
                    .replace("{PlayerUnarmedLvl}", String.valueOf(unarmedLevel))
                    .replace("{PlayerUnarmedXP}", String.valueOf(unarmedXp))
                    .replace("{PlayerUnarmedXpToNextLvl}", String.valueOf(unarmedXpToNextLvl))
                    .replace("{PlayerWoodcuttingLvl}", String.valueOf(woodcuttingLevel))
                    .replace("{PlayerWoodcuttingXP}", String.valueOf(woodcuttingXp))
                    .replace("{PlayerWoodcuttingXpToNextLvl}", String.valueOf(woodcuttingXpToNextLvl)));
            String withDisplayNamePAPISet = main.setPlaceholders(player, displayName);

            playerSkullMeta.setDisplayName(withDisplayNamePAPISet);
//            meta.setLore(List.of("Skill Level: " + data.getTotalLevel()));
            ArrayList<String> lore1 = new ArrayList<>();
            for (String WorldInfo : main.getSkillsConfig().getStringList("Leaderboard-Gui.Player-Skill-Styling.Player-Lore")) {
                String withPAPISet = main.setPlaceholders(player, WorldInfo);
                lore1.add(Main.color(withPAPISet)
                        .replace("{PlayerName}", data.getName())
                        .replace("{PlayerLeaderboardPlacement}", String.valueOf(i + 1))
                        .replace("{PlayerTotalLvl}", String.valueOf(data.getTotalLevel()))
                        .replace("{PlayerTotalXp}", String.valueOf(data.getTotalXP()))
                        .replace("{PlayerAcrobaticsLvl}", String.valueOf(acrobaticsLevel))
                        .replace("{PlayerAcrobaticsXP}", String.valueOf(acrobaticsXp))
                        .replace("{PlayerAcrobaticsXpToNextLvl}", String.valueOf(acrobaticsXpToNextLvl))
                        .replace("{PlayerAlchemyLvl}", String.valueOf(alchemyLevel))
                        .replace("{PlayerAlchemyXP}", String.valueOf(alchemyXp))
                        .replace("{PlayerAlchemyXpToNextLvl}", String.valueOf(alchemyXpToNextLvl))
                        .replace("{PlayerArcheryLvl}", String.valueOf(archeryLevel))
                        .replace("{PlayerArcheryXP}", String.valueOf(archeryXp))
                        .replace("{PlayerArcheryXpToNextLvl}", String.valueOf(archeryXpToNextLvl))
                        .replace("{PlayerAxeLvl}", String.valueOf(axesLevel))
                        .replace("{PlayerAxeXP}", String.valueOf(axesXp))
                        .replace("{PlayerAxeXpToNextLvl}", String.valueOf(axesXpToNextLvl))
                        .replace("{PlayerExcavationLvl}", String.valueOf(excavationLevel))
                        .replace("{PlayerExcavationXP}", String.valueOf(excavationXp))
                        .replace("{PlayerExcavationXpToNextLvl}", String.valueOf(excavationXpToNextLvl))
                        .replace("{PlayerFishingLvl}", String.valueOf(fishingLevel))
                        .replace("{PlayerFishingXP}", String.valueOf(fishingXp))
                        .replace("{PlayerFishingXpToNextLvl}", String.valueOf(fishingXpToNextLvl))
                        .replace("{PlayerHerbalismLvl}", String.valueOf(herbalismLevel))
                        .replace("{PlayerHerbalismXP}", String.valueOf(herbalismXp))
                        .replace("{PlayerHerbalismXpToNextLvl}", String.valueOf(herbalismXpToNextLvl))
                        .replace("{PlayerMiningLvl}", String.valueOf(miningLevel))
                        .replace("{PlayerMiningXP}", String.valueOf(miningXp))
                        .replace("{PlayerMiningXpToNextLvl}", String.valueOf(miningXpToNextLvl))
                        .replace("{PlayerSwordLvl}", String.valueOf(swordLevel))
                        .replace("{PlayerSwordXP}", String.valueOf(swordXp))
                        .replace("{PlayerSwordXpToNextLvl}", String.valueOf(swordXpToNextLvl))
                        .replace("{PlayerTamingLvl}", String.valueOf(tamingLevel))
                        .replace("{PlayerTamingXP}", String.valueOf(tamingXp))
                        .replace("{PlayerTamingXpToNextLvl}", String.valueOf(tamingXpToNextLvl))
                        .replace("{PlayerUnarmedLvl}", String.valueOf(unarmedLevel))
                        .replace("{PlayerUnarmedXP}", String.valueOf(unarmedXp))
                        .replace("{PlayerUnarmedXpToNextLvl}", String.valueOf(unarmedXpToNextLvl))
                        .replace("{PlayerWoodcuttingLvl}", String.valueOf(woodcuttingLevel))
                        .replace("{PlayerWoodcuttingXP}", String.valueOf(woodcuttingXp))
                        .replace("{PlayerWoodcuttingXpToNextLvl}", String.valueOf(woodcuttingXpToNextLvl)));
            }
            playerSkullMeta.setLore(lore1);
            playerHead.setItemMeta(playerSkullMeta);

            inventory.setItem(i - startIndex, playerHead); // Set item in the inventory
        }

        // Create Item \\
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerSkullMeta = (SkullMeta) playerHead.getItemMeta();

        assert playerSkullMeta != null;
        playerSkullMeta.setOwner(player.getName());

        int acrobaticsLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.ACROBATICS);
        int acrobaticsXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.ACROBATICS);
        int acrobaticsXpToNextLvl = main.getLevelManager().getXPForNextLevel(acrobaticsLevel);

        int alchemyLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.ALCHEMY);
        int alchemyXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.ALCHEMY);
        int alchemyXpToNextLvl = main.getLevelManager().getXPForNextLevel(alchemyLevel);

        int archeryLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.ARCHERY);
        int archeryXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.ARCHERY);
        int archeryXpToNextLvl = main.getLevelManager().getXPForNextLevel(archeryLevel);

        int axesLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.AXES);
        int axesXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.AXES);
        int axesXpToNextLvl = main.getLevelManager().getXPForNextLevel(axesLevel);

        int excavationLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.EXCAVATION);
        int excavationXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.EXCAVATION);
        int excavationXpToNextLvl = main.getLevelManager().getXPForNextLevel(excavationLevel);

        int fishingLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.FISHING);
        int fishingXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.FISHING);
        int fishingXpToNextLvl = main.getLevelManager().getXPForNextLevel(fishingLevel);

        int herbalismLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.HERBALISM);
        int herbalismXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.HERBALISM);
        int herbalismXpToNextLvl = main.getLevelManager().getXPForNextLevel(herbalismLevel);

        int miningLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.MINING);
        int miningXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.MINING);
        int miningXpToNextLvl = main.getLevelManager().getXPForNextLevel(miningLevel);

        int swordLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.SWORDS);
        int swordXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.SWORDS);
        int swordXpToNextLvl = main.getLevelManager().getXPForNextLevel(swordLevel);

        int tamingLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.TAMING);
        int tamingXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.TAMING);
        int tamingXpToNextLvl = main.getLevelManager().getXPForNextLevel(tamingLevel);

        int unarmedLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.UNARMED);
        int unarmedXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.UNARMED);
        int unarmedXpToNextLvl = main.getLevelManager().getXPForNextLevel(unarmedLevel);

        int woodcuttingLevel = main.getLevelManager().getLevel(player.getUniqueId(), LevelManager.Skill.WOODCUTTING);
        int woodcuttingXp = main.getLevelManager().getXP(player.getUniqueId(), LevelManager.Skill.WOODCUTTING);
        int woodcuttingXpToNextLvl = main.getLevelManager().getXPForNextLevel(woodcuttingLevel);

        String displayName = Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Player-Skill-Styling.Player-Title")
                .replace("{PlayerName}", player.getName())
                .replace("{PlayerLeaderboardPlacement}", String.valueOf(placement))
                .replace("{PlayerTotalLvl}", String.valueOf(totalLevel))
                .replace("{PlayerTotalXp}", String.valueOf(totalXp))
                .replace("{PlayerAcrobaticsLvl}", String.valueOf(acrobaticsLevel))
                .replace("{PlayerAcrobaticsXP}", String.valueOf(acrobaticsXp))
                .replace("{PlayerAcrobaticsXpToNextLvl}", String.valueOf(acrobaticsXpToNextLvl))
                .replace("{PlayerAlchemyLvl}", String.valueOf(alchemyLevel))
                .replace("{PlayerAlchemyXP}", String.valueOf(alchemyXp))
                .replace("{PlayerAlchemyXpToNextLvl}", String.valueOf(alchemyXpToNextLvl))
                .replace("{PlayerArcheryLvl}", String.valueOf(archeryLevel))
                .replace("{PlayerArcheryXP}", String.valueOf(archeryXp))
                .replace("{PlayerArcheryXpToNextLvl}", String.valueOf(archeryXpToNextLvl))
                .replace("{PlayerAxeLvl}", String.valueOf(axesLevel))
                .replace("{PlayerAxeXP}", String.valueOf(axesXp))
                .replace("{PlayerAxeXpToNextLvl}", String.valueOf(axesXpToNextLvl))
                .replace("{PlayerExcavationLvl}", String.valueOf(excavationLevel))
                .replace("{PlayerExcavationXP}", String.valueOf(excavationXp))
                .replace("{PlayerExcavationXpToNextLvl}", String.valueOf(excavationXpToNextLvl))
                .replace("{PlayerFishingLvl}", String.valueOf(fishingLevel))
                .replace("{PlayerFishingXP}", String.valueOf(fishingXp))
                .replace("{PlayerFishingXpToNextLvl}", String.valueOf(fishingXpToNextLvl))
                .replace("{PlayerHerbalismLvl}", String.valueOf(herbalismLevel))
                .replace("{PlayerHerbalismXP}", String.valueOf(herbalismXp))
                .replace("{PlayerHerbalismXpToNextLvl}", String.valueOf(herbalismXpToNextLvl))
                .replace("{PlayerMiningLvl}", String.valueOf(miningLevel))
                .replace("{PlayerMiningXP}", String.valueOf(miningXp))
                .replace("{PlayerMiningXpToNextLvl}", String.valueOf(miningXpToNextLvl))
                .replace("{PlayerSwordLvl}", String.valueOf(swordLevel))
                .replace("{PlayerSwordXP}", String.valueOf(swordXp))
                .replace("{PlayerSwordXpToNextLvl}", String.valueOf(swordXpToNextLvl))
                .replace("{PlayerTamingLvl}", String.valueOf(tamingLevel))
                .replace("{PlayerTamingXP}", String.valueOf(tamingXp))
                .replace("{PlayerTamingXpToNextLvl}", String.valueOf(tamingXpToNextLvl))
                .replace("{PlayerUnarmedLvl}", String.valueOf(unarmedLevel))
                .replace("{PlayerUnarmedXP}", String.valueOf(unarmedXp))
                .replace("{PlayerUnarmedXpToNextLvl}", String.valueOf(unarmedXpToNextLvl))
                .replace("{PlayerWoodcuttingLvl}", String.valueOf(woodcuttingLevel))
                .replace("{PlayerWoodcuttingXP}", String.valueOf(woodcuttingXp))
                .replace("{PlayerWoodcuttingXpToNextLvl}", String.valueOf(woodcuttingXpToNextLvl)));
        String withDisplayNamePAPISet = main.setPlaceholders(player, displayName);

        playerSkullMeta.setDisplayName(withDisplayNamePAPISet);
//            meta.setLore(List.of("Skill Level: " + data.getTotalLevel()));
        ArrayList<String> lore1 = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Leaderboard-Gui.Player-Skill-Styling.Player-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            lore1.add(Main.color(withPAPISet)
                    .replace("{PlayerName}", player.getName())
                    .replace("{PlayerLeaderboardPlacement}", String.valueOf(placement))
                    .replace("{PlayerTotalLvl}", String.valueOf(totalLevel))
                    .replace("{PlayerTotalXp}", String.valueOf(totalXp))
                    .replace("{PlayerAcrobaticsLvl}", String.valueOf(acrobaticsLevel))
                    .replace("{PlayerAcrobaticsXP}", String.valueOf(acrobaticsXp))
                    .replace("{PlayerAcrobaticsXpToNextLvl}", String.valueOf(acrobaticsXpToNextLvl))
                    .replace("{PlayerAlchemyLvl}", String.valueOf(alchemyLevel))
                    .replace("{PlayerAlchemyXP}", String.valueOf(alchemyXp))
                    .replace("{PlayerAlchemyXpToNextLvl}", String.valueOf(alchemyXpToNextLvl))
                    .replace("{PlayerArcheryLvl}", String.valueOf(archeryLevel))
                    .replace("{PlayerArcheryXP}", String.valueOf(archeryXp))
                    .replace("{PlayerArcheryXpToNextLvl}", String.valueOf(archeryXpToNextLvl))
                    .replace("{PlayerAxeLvl}", String.valueOf(axesLevel))
                    .replace("{PlayerAxeXP}", String.valueOf(axesXp))
                    .replace("{PlayerAxeXpToNextLvl}", String.valueOf(axesXpToNextLvl))
                    .replace("{PlayerExcavationLvl}", String.valueOf(excavationLevel))
                    .replace("{PlayerExcavationXP}", String.valueOf(excavationXp))
                    .replace("{PlayerExcavationXpToNextLvl}", String.valueOf(excavationXpToNextLvl))
                    .replace("{PlayerFishingLvl}", String.valueOf(fishingLevel))
                    .replace("{PlayerFishingXP}", String.valueOf(fishingXp))
                    .replace("{PlayerFishingXpToNextLvl}", String.valueOf(fishingXpToNextLvl))
                    .replace("{PlayerHerbalismLvl}", String.valueOf(herbalismLevel))
                    .replace("{PlayerHerbalismXP}", String.valueOf(herbalismXp))
                    .replace("{PlayerHerbalismXpToNextLvl}", String.valueOf(herbalismXpToNextLvl))
                    .replace("{PlayerMiningLvl}", String.valueOf(miningLevel))
                    .replace("{PlayerMiningXP}", String.valueOf(miningXp))
                    .replace("{PlayerMiningXpToNextLvl}", String.valueOf(miningXpToNextLvl))
                    .replace("{PlayerSwordLvl}", String.valueOf(swordLevel))
                    .replace("{PlayerSwordXP}", String.valueOf(swordXp))
                    .replace("{PlayerSwordXpToNextLvl}", String.valueOf(swordXpToNextLvl))
                    .replace("{PlayerTamingLvl}", String.valueOf(tamingLevel))
                    .replace("{PlayerTamingXP}", String.valueOf(tamingXp))
                    .replace("{PlayerTamingXpToNextLvl}", String.valueOf(tamingXpToNextLvl))
                    .replace("{PlayerUnarmedLvl}", String.valueOf(unarmedLevel))
                    .replace("{PlayerUnarmedXP}", String.valueOf(unarmedXp))
                    .replace("{PlayerUnarmedXpToNextLvl}", String.valueOf(unarmedXpToNextLvl))
                    .replace("{PlayerWoodcuttingLvl}", String.valueOf(woodcuttingLevel))
                    .replace("{PlayerWoodcuttingXP}", String.valueOf(woodcuttingXp))
                    .replace("{PlayerWoodcuttingXpToNextLvl}", String.valueOf(woodcuttingXpToNextLvl)));
        }
        playerSkullMeta.setLore(lore1);
        playerHead.setItemMeta(playerSkullMeta);

        // Create Item \\
        String GlassTitle = main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-Filler-Title");
        Material GlassConfigItem = Material.valueOf(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-Filler-Item"));
        ItemStack GlassItem = new ItemStack(GlassConfigItem);
        ItemMeta GlassItemMeta = GlassItem.getItemMeta();
        // Set Title \\
        String GlassItemDisplayName = Main.color(GlassTitle).replace("{Player}", player.getName());
        String GlassItemDisplayNamePAPISet = main.setPlaceholders(player, GlassItemDisplayName);
        assert GlassItemMeta != null;
        GlassItemMeta.setDisplayName(Main.color(GlassItemDisplayNamePAPISet));
        GlassItem.setItemMeta(GlassItemMeta);

        // Add "Previous Page" and "Next Page" buttons
        if (currentPage > 0) {
            inventory.setItem(46, getPreviousPageItem());
        }else{
            inventory.setItem(46, GlassItem);
        }

        if (endIndex < playerDataList.size()) {
            inventory.setItem(52, getNextPageItem());
        }else{
            inventory.setItem(52, GlassItem);
        }

        inventory.setItem(45, GlassItem);
        inventory.setItem(47, GlassItem);
        inventory.setItem(48, GlassItem);
        inventory.setItem(49, playerHead);
        inventory.setItem(50, GlassItem);
        inventory.setItem(51, GlassItem);
        inventory.setItem(53, GlassItem);
    }

    private ItemStack getNextPageItem() {
        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta meta = nextPage.getItemMeta();
        String displayName = Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-NextPage-Title")
                .replace("{PlayerName}", player.getName())
                .replace("{CurrentPage}", String.valueOf(currentPage + 1))
                .replace("{TotalPages}", String.valueOf(totalPages)));
        String withDisplayNamePAPISet = main.setPlaceholders(player, displayName);

        meta.setDisplayName(withDisplayNamePAPISet);
        ArrayList<String> nextlore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-NextPage-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            nextlore.add(Main.color(withPAPISet)
                    .replace("{PlayerName}", player.getName())
                    .replace("{CurrentPage}", String.valueOf(currentPage + 1))
                    .replace("{TotalPages}", String.valueOf(totalPages)));
        }
        meta.setLore(nextlore);
        nextPage.setItemMeta(meta);
        return nextPage;
    }

    private ItemStack getPreviousPageItem() {
        ItemStack prevPage = new ItemStack(Material.ARROW);
        ItemMeta meta = prevPage.getItemMeta();
        String displayName = Main.color(main.getSkillsConfig().getString("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-PreviousPage-Title")
                .replace("{PlayerName}", player.getName())
                .replace("{CurrentPage}", String.valueOf(currentPage + 1))
                .replace("{TotalPages}", String.valueOf(totalPages)));
        String withDisplayNamePAPISet = main.setPlaceholders(player, displayName);

        meta.setDisplayName(withDisplayNamePAPISet);
        ArrayList<String> prevlore = new ArrayList<>();
        for (String WorldInfo : main.getSkillsConfig().getStringList("Leaderboard-Gui.Leaderboard-Menu.Leaderboard-Menu-PreviousPage-Lore")) {
            String withPAPISet = main.setPlaceholders(player, WorldInfo);
            prevlore.add(Main.color(withPAPISet)
                    .replace("{PlayerName}", player.getName())
                    .replace("{CurrentPage}", String.valueOf(currentPage + 1))
                    .replace("{TotalPages}", String.valueOf(totalPages)));
        }
        meta.setLore(prevlore);
        prevPage.setItemMeta(meta);
        return prevPage;
    }


    private static PlayerProfile getProfile(UUID uuid) {
        PlayerProfile profile = Bukkit.createPlayerProfile(uuid); // Get a new player profile
        PlayerTextures textures = profile.getTextures();

        profile.setTextures(textures); // Set the textures back to the profile
        return profile;
    }


}
