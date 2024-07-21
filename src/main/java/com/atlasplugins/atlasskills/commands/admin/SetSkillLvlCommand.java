package com.atlasplugins.atlasskills.commands.admin;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.commands.AbstractCommand;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class SetSkillLvlCommand extends AbstractCommand {

    private final Main main;

    public SetSkillLvlCommand(Main main) {
        this.main = main;
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String label, List<String> args) {
        if (args.size() != 3) {
            // Send Usage Message in chat when called.
            for (String UsageMessage : main.getSettingsConfig().getStringList("Command-Messages.Command-Messages-SetSkill-Usage-Message")) {
                String withPAPISet = main.setPlaceholders((Player) sender, UsageMessage);
                String message = Main.color(withPAPISet);
                sender.sendMessage(message);
            }
            return;
        }

        Player player = Bukkit.getPlayer(args.get(0));
        if (player == null) {
            // Send PlayerNotFound Message in chat when called.
            for (String PlayerNotFoundMessage : main.getSettingsConfig().getStringList("Command-Messages.Command-Messages-SetSkill-PlayerNotFound-Message")) {
                String withPAPISet = main.setPlaceholders((Player) sender, PlayerNotFoundMessage);
                String message = Main.color(withPAPISet);
                sender.sendMessage(message);
            }
            return;
        }

        LevelManager.Skill skill;
        try {
            skill = LevelManager.Skill.valueOf(args.get(1).toUpperCase());
        } catch (IllegalArgumentException e) {
            // Send SkillNotFound Message in chat when called.
            for (String SkillNotFound : main.getSettingsConfig().getStringList("Command-Messages.Command-Messages-SetSkill-SkillNotFound-Message")) {
                String withPAPISet = main.setPlaceholders((Player) sender, SkillNotFound);
                String message = Main.color(withPAPISet);
                sender.sendMessage(message);
            }
            return;
        }

        int level;
        try {
            level = Integer.parseInt(args.get(2));
        } catch (NumberFormatException e) {
            // Send LevelNotFound Message in chat when called.
            for (String LevelNotFound : main.getSettingsConfig().getStringList("Command-Messages.Command-Messages-SetSkill-LevelRange-Message")) {
                String withPAPISet = main.setPlaceholders((Player) sender, LevelNotFound);
                String message = Main.color(withPAPISet);
                sender.sendMessage(message);
            }
            return;
        }
        main.getLevelManager().setLevel((Player) sender, skill, level);
    }

    @Override
    public void complete(JavaPlugin plugin, CommandSender sender, String label, List<String> args, List<String> completions) {
        if (args.size() == 1) {
            // Suggest player names
            completions.addAll(Bukkit.getServer().getOnlinePlayers().stream()
                    .map(Player::getName)
                    .toList());
        } else if (args.size() == 2) {
            // Suggest skill names
            for (LevelManager.Skill skill : LevelManager.Skill.values()) {
                completions.add(skill.name().toLowerCase());
            }
        } else if (args.size() == 3) {
            // Suggest level values (e.g., 1 to 100)
            for (int i = 1; i <= 100; i++) {
                completions.add(String.valueOf(i));
            }
        }
    }

    @Override
    public List<String> getLabels() {
        return Collections.singletonList("setlvl");
    }

    @Override
    public String getPermission() {
        return "atlaskills.setskilllvl";  // permission required for help command
    }
}
