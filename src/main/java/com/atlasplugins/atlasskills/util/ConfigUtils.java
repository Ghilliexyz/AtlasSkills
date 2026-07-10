package com.atlasplugins.atlasskills.util;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * Helpers for robust, crash-safe reading of config-driven values.
 *
 * <p>On MC 1.21+ {@code org.bukkit.Sound} became an interface, but Spigot still
 * ships a static {@code Sound.valueOf(String)} that maps legacy names to the
 * correct registry entry. We keep using it, only wrapped so a bad/missing config
 * value logs a warning instead of throwing.</p>
 */
public final class ConfigUtils {

    private ConfigUtils() {
    }

    /**
     * Back-fills any keys missing from a user config with the values from the
     * bundled default resource, preserving the user's existing values, then
     * persists the merged config back to disk.
     *
     * @param plugin       the plugin (used to read the jar resource + log)
     * @param config       the already-loaded user config
     * @param file         the on-disk file to save the merged result to
     * @param resourceName the bundled default resource name (e.g. "settings.yml")
     */
    public static void applyDefaults(JavaPlugin plugin, FileConfiguration config, File file, String resourceName) {
        InputStream in = plugin.getResource(resourceName);
        if (in == null) {
            plugin.getLogger().warning("Bundled default resource '" + resourceName
                    + "' not found in jar; cannot back-fill missing config keys.");
            return;
        }
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(defaults);
            config.options().copyDefaults(true);
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to back-fill defaults for '" + resourceName + "': " + e.getMessage());
        }
    }

    /**
     * Resolves a {@link Sound} from a config name. Returns {@code null} (with a
     * warning) if the name is missing or not a valid sound, so callers should
     * null-check before playing.
     */
    public static Sound soundFromName(JavaPlugin plugin, String name) {
        if (name == null) {
            plugin.getLogger().warning("Missing sound name in config; skipping sound.");
            return null;
        }
        try {
            return Sound.valueOf(name);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid sound name in config: '" + name + "'; skipping sound.");
            return null;
        }
    }

    /** Resolves a {@link Material} from a config name, falling back on missing/invalid input. */
    public static Material materialFromName(JavaPlugin plugin, String name, Material fallback) {
        if (name == null) {
            plugin.getLogger().warning("Missing material name in config; using " + fallback + ".");
            return fallback;
        }
        try {
            return Material.valueOf(name);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid material name in config: '" + name + "'; using " + fallback + ".");
            return fallback;
        }
    }

    /** Resolves a {@link BarColor} from a config name, falling back on missing/invalid input. */
    public static BarColor barColorFromName(JavaPlugin plugin, String name, BarColor fallback) {
        if (name == null) {
            return fallback;
        }
        try {
            return BarColor.valueOf(name);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid boss-bar color in config: '" + name + "'; using " + fallback + ".");
            return fallback;
        }
    }

    /** Resolves a {@link BarStyle} from a config name, falling back on missing/invalid input. */
    public static BarStyle barStyleFromName(JavaPlugin plugin, String name, BarStyle fallback) {
        if (name == null) {
            return fallback;
        }
        try {
            return BarStyle.valueOf(name);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid boss-bar style in config: '" + name + "'; using " + fallback + ".");
            return fallback;
        }
    }
}
