package com.atlasplugins.atlasskills.managers.levelsystem;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class LevelManager {

    private Main main;
    private Map<Player, EnumMap<Skill, Integer>> playerLevels;
    private Map<Player, EnumMap<Skill, Integer>> playerXP;
    private Connection connection;

    /**
     * Constructor for the LevelManager.
     * Initializes the main reference and maps for player levels and XP.
     *
     * @param main The main plugin instance.
     */
    public LevelManager(Main main) {
        this.main = main;
        this.playerLevels = new HashMap<>();
        this.playerXP = new HashMap<>();
        try {
            openConnection();
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a connection to the SQLite database.
     *
     * @throws SQLException If a database access error occurs.
     */
    private void openConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + main.getDataFolder() + "/playerData.db");
    }

    /**
     * Creates the player data table if it does not exist.
     *
     * @throws SQLException If a database access error occurs.
     */
    private void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS player_data (uuid TEXT, skill TEXT, level INTEGER, xp INTEGER, PRIMARY KEY (uuid, skill))";
        try (PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
            pstmt.executeUpdate();
        }
    }

    /**
     * Saves a specific player's levels and XP to the database.
     */
    public void savePlayerData(Player player) {
        String insertOrUpdateSQL = "INSERT OR REPLACE INTO player_data (uuid, skill, level, xp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertOrUpdateSQL)) {
            connection.setAutoCommit(false);  // Start transaction

            for (Skill skill : Skill.values()) {
                pstmt.setString(1, player.getUniqueId().toString());
                pstmt.setString(2, skill.name());
                pstmt.setInt(3, getLevel(player, skill));
                pstmt.setInt(4, getXP(player, skill));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            connection.commit();  // Commit transaction
//            System.out.println("Player data saved successfully for: " + player.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();  // Rollback transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);  // Reset auto-commit
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Loads a specific player's levels and XP from the database.
     */
    public void loadPlayerData(Player player) {
        String selectSQL = "SELECT * FROM player_data WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Skill skill = Skill.valueOf(rs.getString("skill"));
                    int level = rs.getInt("level");
                    int xp = rs.getInt("xp");

                    playerLevels.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, level);
                    playerXP.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, xp);
                }
            }
//            System.out.println("Player data loaded successfully for: " + player.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Gets the level of a specific skill for a player.
     *
     * @param player The player whose skill level is being queried.
     * @param skill  The skill whose level is being queried.
     * @return The level of the specified skill for the player.
     */
    public int getLevel(Player player, Skill skill) {
        return playerLevels.getOrDefault(player, new EnumMap<>(Skill.class)).getOrDefault(skill, 1);
    }

    /**
     * Gets the XP of a specific skill for a player.
     *
     * @param player The player whose skill XP is being queried.
     * @param skill  The skill whose XP is being queried.
     * @return The XP of the specified skill for the player.
     */
    public int getXP(Player player, Skill skill) {
        return playerXP.getOrDefault(player, new EnumMap<>(Skill.class)).getOrDefault(skill, 0);
    }

    /**
     * Calculates the XP required for the next level.
     *
     * @param level The current level.
     * @return The XP required to reach the next level.
     */
    public int getXPForNextLevel(int level) {
        return (int) ((50 * Math.pow(level, 2) - 150 * level + 200) / 4);
    }

    /**
     * Adds XP to a player's skill and handles level-up logic.
     *
     * @param player The player gaining XP.
     * @param skill  The skill gaining XP.
     * @param xp     The amount of XP to add.
     */
    public void addXP(Player player, Skill skill, int xp) {
        int currentXP = getXP(player, skill);
        int newXP = currentXP + xp;
        int currentLevel = getLevel(player, skill);

        while (newXP >= getXPForNextLevel(currentLevel)) {
            newXP -= getXPForNextLevel(currentLevel);
            currentLevel++;

            boolean isSkillRewardXP = main.getSkillsConfig().getBoolean("Skill-Addons.Skill-LvlUp-Rewards.Skill-LvlUp-Reward-XP-Multiplier-Toggle");

            int xpReward = 0;

            if(isSkillRewardXP)
            {
                if(currentLevel % 10 == 0)
                {
                    xpReward = currentLevel * 100;
                    player.giveExp(xpReward);

                    boolean isLevelUpRewardMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-LvlUPReward-Message-Toggle");
                    if(isLevelUpRewardMessageEnabled) {
                        for (String levelUpMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-LvlUPReward-Message")) {
                            String withPAPISet = main.setPlaceholders(player, levelUpMessage);
                            player.sendMessage(Main.color(withPAPISet)
                                    .replace("{player}", player.getName())
                                    .replace("{skillName}", ReformatName(skill.toString()))
                                    .replace("{skillLevel}", String.valueOf(currentLevel))
                                    .replace("{skillRewardXP}", String.valueOf(xpReward)));
                        }
                    }
                }else {
                    boolean isLevelUpMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-LvlUP-Message-Toggle");
                    if (isLevelUpMessageEnabled) {
                        for (String levelUpMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-LvlUP-Message")) {
                            String withPAPISet = main.setPlaceholders(player, levelUpMessage);
                            player.sendMessage(Main.color(withPAPISet)
                                    .replace("{player}", player.getName())
                                    .replace("{skillName}", ReformatName(skill.toString()))
                                    .replace("{skillLevel}", String.valueOf(currentLevel)));
                        }
                    }
                }
            }else {
                boolean isLevelUpMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-LvlUP-Message-Toggle");
                if (isLevelUpMessageEnabled) {
                    for (String levelUpMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-LvlUP-Message")) {
                        String withPAPISet = main.setPlaceholders(player, levelUpMessage);
                        player.sendMessage(Main.color(withPAPISet)
                                .replace("{player}", player.getName())
                                .replace("{skillName}", ReformatName(skill.toString()))
                                .replace("{skillLevel}", String.valueOf(currentLevel)));
                    }
                }
            }

            Sound levelUpSound = Sound.valueOf(main.getSettingsConfig().getString("SkillSounds.Skill-LvlUP-Sound"));
            float levelUpVolume = main.getSettingsConfig().getInt("SkillSounds.Skill-LvlUP-Volume");
            float levelUpPitch = main.getSettingsConfig().getInt("SkillSounds.Skill-LvlUP-Pitch");

            boolean levelUpPlaySound = main.getSettingsConfig().getBoolean("SkillSounds.Skill-LvlUP-Sound-Toggle");

            if (levelUpPlaySound) {
                player.playSound(player.getLocation(), levelUpSound, levelUpVolume, levelUpPitch);
            }
        }

        // Saves the players data
//        Main.instance.getLevelManager().savePlayerData(player);

        playerXP.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, newXP);
        playerLevels.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, currentLevel);
    }

    /**
     * Sets the level of a specific skill for a player and resets the XP.
     *
     * @param player The player whose skill level is being set.
     * @param skill  The skill whose level is being set.
     * @param level  The level to set.
     */
    public void setLevel(Player player, Player sender, Skill skill, int level) {
        playerLevels.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, level);
        playerXP.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, 0);

        boolean isSetLvlMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-SetSkillLvl-Message-Toggle");
        if (isSetLvlMessageEnabled) {
            for (String setLevelMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-SetSkillLvl-Message")) {
                String withPAPISet = main.setPlaceholders(player, setLevelMessage);
                sender.sendMessage(Main.color(withPAPISet)
                        .replace("{player}", player.getName())
                        .replace("{skillName}", ReformatName(skill.toString()))
                        .replace("{skillNewLevel}", String.valueOf(level)));
            }
        }

        Sound setLevelSound = Sound.valueOf(main.getSettingsConfig().getString("SkillSounds.Skill-SetSkillLvl-Sound"));
        float setLevelVolume = main.getSettingsConfig().getInt("SkillSounds.Skill-SetSkillLvl-Volume");
        float setLevelPitch = main.getSettingsConfig().getInt("SkillSounds.Skill-SetSkillLvl-Pitch");

        boolean setLevelPlaySound = main.getSettingsConfig().getBoolean("SkillSounds.Skill-SetSkillLvl-Sound-Toggle");

        if (setLevelPlaySound) {
            sender.playSound(sender.getLocation(), setLevelSound, setLevelVolume, setLevelPitch);
        }
    }

    /**
     * Resets a player's levels and XP for all skills.
     *
     * @param player The player whose levels and XP are being reset.
     */
    public void resetPlayer(Player player, Player sender) {
        playerLevels.remove(player);
        playerXP.remove(player);

        // Saves the players data
        savePlayerData(player);

        boolean isResetMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-ResetSkills-Message-Toggle");
        if (isResetMessageEnabled) {
            for (String resetMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-ResetSkills-Message")) {
                String withPAPISet = main.setPlaceholders(player, resetMessage);
                sender.sendMessage(Main.color(withPAPISet)
                        .replace("{player}", player.getName()));
            }
        }

        Sound resetSound = Sound.valueOf(main.getSettingsConfig().getString("SkillSounds.Skill-ResetSkills-Sound"));
        float resetVolume = main.getSettingsConfig().getInt("SkillSounds.Skill-ResetSkills-Volume");
        float resetPitch = main.getSettingsConfig().getInt("SkillSounds.Skill-ResetSkills-Pitch");

        boolean resetPlaySound = main.getSettingsConfig().getBoolean("SkillSounds.Skill-ResetSkills-Sound-Toggle");

        if (resetPlaySound) {
            sender.playSound(sender.getLocation(), resetSound, resetVolume, resetPitch);
        }
    }

    /**
     * Closes the connection to the SQLite database.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Enum representing various skills that players can level up.
     */
    public enum Skill {
        ACROBATICS,
        ALCHEMY,
        ARCHERY,
        AXES,
        EXCAVATION,
        FISHING,
        HERBALISM,
        MINING,
        SWORDS,
        TAMING,
        UNARMED,
        WOODCUTTING
    }

    public String ReformatName(String name) {
        if (name == null || name.isEmpty()) {
            return name; // Return the original value if it's null or empty
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}