package com.atlasplugins.atlasskills.managers.levelsystem;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class LevelManager {

    private Main main;
    private Map<Player, EnumMap<Skill, Integer>> playerLevels;
    private Map<Player, EnumMap<Skill, Integer>> playerXP;

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

            // Check if the user wants to send a chat message when leveling up.
            boolean isLevelUpMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-LvlUP-Message-Toggle");
            if (isLevelUpMessageEnabled) {
                // Send levelUpMessage in chat when called.
                for (String levelUpMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-LvlUP-Message")) {
                    String withPAPISet = main.setPlaceholders(player, levelUpMessage);
                    player.sendMessage(Main.color(withPAPISet)
                            .replace("{player}", player.getName())
                            .replace("{skillName}", skill.toString())
                            .replace("{skillLevel}", String.valueOf(currentLevel)));
                }
            }

            // Get Level Up sound via config.
            Sound levelUpSound = Sound.valueOf(main.getSettingsConfig().getString("SkillSounds.Skill-LvlUP-Sound"));
            float levelUpVolume = main.getSettingsConfig().getInt("SkillSounds.Skill-LvlUP-Volume");
            float levelUpPitch = main.getSettingsConfig().getInt("SkillSounds.Skill-LvlUP-Pitch");

            // Get the bool to check if the user wants to play the Enchantment Disabled sound
            boolean levelUpPlaySound = main.getSettingsConfig().getBoolean("SkillSounds.Skill-LvlUP-Sound-Toggle");

            // check if the user doesn't want to play the sound then return if not.
            if (levelUpPlaySound) {
                // Play sound for when enchant is blacklisted.
                player.playSound(player.getLocation(), levelUpSound, levelUpVolume, levelUpPitch);
            }
        }

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
    public void setLevel(Player player, Skill skill, int level) {
        playerLevels.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, level);
        playerXP.computeIfAbsent(player, k -> new EnumMap<>(Skill.class)).put(skill, 0);

        // Check if the user wants to send a chat message when setting the level.
        boolean isSetLvlMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-SetSkillLvl-Message-Toggle");
        if (isSetLvlMessageEnabled) {
            // Send setLevelMessage in chat when called.
            for (String setLevelMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-SetSkillLvl-Message")) {
                String withPAPISet = main.setPlaceholders(player, setLevelMessage);
                player.sendMessage(Main.color(withPAPISet)
                        .replace("{player}", player.getName())
                        .replace("{skillName}", skill.toString())
                        .replace("{skillNewLevel}", String.valueOf(level)));
            }
        }

        // Get Set Level sound via config.
        Sound setLevelSound = Sound.valueOf(main.getSettingsConfig().getString("SkillSounds.Skill-SetSkillLvl-Sound"));
        float setLevelVolume = main.getSettingsConfig().getInt("SkillSounds.Skill-SetSkillLvl-Volume");
        float setLevelPitch = main.getSettingsConfig().getInt("SkillSounds.Skill-SetSkillLvl-Pitch");

        // Get the bool to check if the user wants to play the Enchantment Disabled sound
        boolean setLevelPlaySound = main.getSettingsConfig().getBoolean("SkillSounds.Skill-SetSkillLvl-Sound-Toggle");

        // check if the user doesn't want to play the sound then return if not.
        if (setLevelPlaySound) {
            // Play sound for when enchant is blacklisted.
            player.playSound(player.getLocation(), setLevelSound, setLevelVolume, setLevelPitch);
        }
    }

    /**
     * Resets all skills for a player, removing all levels and XP.
     *
     * @param player The player whose skills are being reset.
     */
    public void resetPlayer(Player player) {
        playerLevels.remove(player);
        playerXP.remove(player);

        // Check if the user wants to send a chat message when resetting skills.
        boolean isResetLevelsMessageEnabled = main.getSettingsConfig().isBoolean("SkillMessages.Skill-ResetSkills-Message-Toggle");
        if (isResetLevelsMessageEnabled) {
            // Send resetLevelsMessage in chat when called.
            for (String resetLevelsMessage : main.getSettingsConfig().getStringList("SkillMessages.Skill-ResetSkills-Message")) {
                String withPAPISet = main.setPlaceholders(player, resetLevelsMessage);
                player.sendMessage(Main.color(withPAPISet)
                        .replace("{player}", player.getName()));
            }
        }

        // Get Reset Levels sound via config.
        Sound resetLevelsSound = Sound.valueOf(main.getSettingsConfig().getString("SkillSounds.Skill-ResetSkills-Sound"));
        float resetLevelsVolume = main.getSettingsConfig().getInt("SkillSounds.Skill-ResetSkills-Volume");
        float resetLevelsPitch = main.getSettingsConfig().getInt("SkillSounds.Skill-ResetSkills-Pitch");

        // Get the bool to check if the user wants to play the Enchantment Disabled sound
        boolean resetLevelsPlaySound = main.getSettingsConfig().getBoolean("SkillSounds.Skill-ResetSkills-Sound-Toggle");

        // check if the user doesn't want to play the sound then return if not.
        if (resetLevelsPlaySound) {
            // Play sound for when enchant is blacklisted.
            player.playSound(player.getLocation(), resetLevelsSound, resetLevelsVolume, resetLevelsPitch);
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
        CROSSBOWS,
        EXCAVATION,
        FISHING,
        HERBALISM,
        MACES,
        MINING,
        REPAIR,
        SWORDS,
        TAMING,
        TRIDENTS,
        UNARMED,
        WOODCUTTING
    }
}