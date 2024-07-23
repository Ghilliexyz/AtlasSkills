package com.atlasplugins.atlasskills;

import com.atlasplugins.atlasskills.commands.CommandRouter;
import com.atlasplugins.atlasskills.listeners.onPlayerEvents;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import com.atlasplugins.atlasskills.skills.*;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    public static Main instance;
    // Change chat colors
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    // PlaceholderAPI
    private boolean isPlaceholderAPIPresent;

    // Config Stuff
    private FileConfiguration skillsConfig;
    private File skillsConfigFile;
    private FileConfiguration settingsConfig;
    private File settingsConfigFile;

    // Level System
    private LevelManager levelManager;
    private BukkitTask saveTask;

    // Command Router Stuff
    private CommandRouter commandRouter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // check if placeholderAPI is present on the server.
        isPlaceholderAPIPresent = checkForPlaceholderAPI();
        if (isPlaceholderAPIPresent) {
            getLogger().info("PlaceholderAPI found, placeholders will be used.");
        } else {
            getLogger().info("PlaceholderAPI not found, placeholders will not be used.");
        }

        // Load custom configs
        loadSkillsConfig();
        loadSettingsConfig();

        // Level System
        levelManager = new LevelManager(this);

        // Loads all player data
        // NOTE: This is used for /reload confirm
        // it will make sure every player's stats who is currently on the server will keep their stats instead of being reset.
        for (Player player : Bukkit.getOnlinePlayers()) {
            levelManager.loadPlayerData(player);
        }

        // Schedule periodic saving every 5 minutes (6000 ticks)
//        this.saveTask = Bukkit.getScheduler().runTaskTimer(this, () -> {
//            for (Player player : Bukkit.getOnlinePlayers()) {
//                levelManager.savePlayerData(player);
//            }
//        }, 6000L, 6000L);

        // Register events
        getServer().getPluginManager().registerEvents(new onPlayerEvents(),this);
        getServer().getPluginManager().registerEvents(new AcrobaticsSkill(this),this);
        getServer().getPluginManager().registerEvents(new AlchemySkill(this),this);
        getServer().getPluginManager().registerEvents(new ArcherySkill(this),this);
        getServer().getPluginManager().registerEvents(new AxeSkill(this),this);
        getServer().getPluginManager().registerEvents(new ExcavationSkill(this),this);
        getServer().getPluginManager().registerEvents(new FishingSkill(this),this);
        getServer().getPluginManager().registerEvents(new HerbalismSkill(this),this);
        getServer().getPluginManager().registerEvents(new MiningSkill(this),this);
        getServer().getPluginManager().registerEvents(new SwordSkill(this),this);
        getServer().getPluginManager().registerEvents(new TamingSkill(this),this);
        getServer().getPluginManager().registerEvents(new UnarmedSkill(this),this);
        getServer().getPluginManager().registerEvents(new WoodcuttingSkill(this),this);

        // Register commands
        this.commandRouter = new CommandRouter(this);
        getCommand("atlasskills").setExecutor(commandRouter);
        getCommand("atlasskills").setTabCompleter(commandRouter);

        // Plugin Startup Message
        Bukkit.getConsoleSender().sendMessage(color("&4---------------------"));
        Bukkit.getConsoleSender().sendMessage(color("&7&l[&c&lAtlas Skills&7&l] &e1.0.0"));
        Bukkit.getConsoleSender().sendMessage(color(""));
        Bukkit.getConsoleSender().sendMessage(color("&cMade by _Ghillie"));
        Bukkit.getConsoleSender().sendMessage(color(""));
        Bukkit.getConsoleSender().sendMessage(color("&cPlugin &aEnabled"));
        Bukkit.getConsoleSender().sendMessage(color("&4---------------------"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Save all player data
        for (Player player : Bukkit.getOnlinePlayers()) {
            levelManager.savePlayerData(player);
        }
        levelManager.closeConnection();

        // Cancel the save task
        if (saveTask != null) {
            saveTask.cancel();
        }

        // Plugin Shutdown Message
        Bukkit.getConsoleSender().sendMessage(color("&4---------------------"));
        Bukkit.getConsoleSender().sendMessage(color("&7&l[&c&lAtlas Skills&7&l] &e1.0.0"));
        Bukkit.getConsoleSender().sendMessage(color(""));
        Bukkit.getConsoleSender().sendMessage(color("&cMade by _Ghillie"));
        Bukkit.getConsoleSender().sendMessage(color(""));
        Bukkit.getConsoleSender().sendMessage(color("&cPlugin &4Disabled"));
        Bukkit.getConsoleSender().sendMessage(color("&4---------------------"));
    }

    public String setPlaceholders(Player p, String text)
    {
        if(checkForPlaceholderAPI())
        {
            return PlaceholderAPI.setPlaceholders(p, text);
        }else{
            return text;
        }
    }

    private boolean checkForPlaceholderAPI() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        return plugin != null && plugin.isEnabled();
    }

    public FileConfiguration getSkillsConfig() {
        return skillsConfig;
    }

    public void saveSkillsConfig() {
        try {
            skillsConfig.save(skillsConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSkillsConfig() {
        skillsConfigFile = new File(getDataFolder(), "skills.yml");
        if (!skillsConfigFile.exists()) {
            saveResource("skills.yml", false);
        }
        skillsConfig = YamlConfiguration.loadConfiguration(skillsConfigFile);
    }

    public FileConfiguration getSettingsConfig() {
        return settingsConfig;
    }

    public void saveSettingsConfig() {
        try {
            settingsConfig.save(settingsConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettingsConfig() {
        settingsConfigFile = new File(getDataFolder(), "settings.yml");
        if (!settingsConfigFile.exists()) {
            saveResource("settings.yml", false);
        }
        settingsConfig = YamlConfiguration.loadConfiguration(settingsConfigFile);
    }

    public LevelManager getLevelManager(){
        return levelManager;
    }
}
