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

public class ResetSkillsCommand extends AbstractCommand {

    private final Main main;

    public ResetSkillsCommand(Main main) {
        this.main = main;
    }

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String label, List<String> args) {
        if (args.size() != 1) {
            // Send Usage Message in chat when called.
            for (String UsageMessage : main.getSettingsConfig().getStringList("Command-Messages.Command-Messages-ResetSkills-Usage-Message")) {
                String withPAPISet = main.setPlaceholders((Player) sender, UsageMessage);
                String message = Main.color(withPAPISet);
                sender.sendMessage(message);
            }
            return;
        }

        Player player = Bukkit.getPlayer(args.get(0));
        if (player == null) {
            // Send PlayerNotFound Message in chat when called.
            for (String PlayerNotFoundMessage : main.getSettingsConfig().getStringList("Command-Messages.Command-Messages-ResetSkills-PlayerNotFound-Message")) {
                String withPAPISet = main.setPlaceholders((Player) sender, PlayerNotFoundMessage);
                String message = Main.color(withPAPISet);
                sender.sendMessage(message);
            }
            return;
        }

        main.getLevelManager().resetPlayer(player);
    }

    @Override
    public void complete(JavaPlugin plugin, CommandSender sender, String label, List<String> args, List<String> completions) {
        if (args.size() == 1) {
            // Suggest player names
            completions.addAll(Bukkit.getServer().getOnlinePlayers().stream()
                    .map(Player::getName)
                    .toList());
        }
    }

    @Override
    public List<String> getLabels() {
        return Collections.singletonList("reset");
    }

    @Override
    public String getPermission() {
        return "atlaskills.resetskills";  // permission required for help command
    }
}
