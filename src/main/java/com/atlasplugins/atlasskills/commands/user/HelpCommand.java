package com.atlasplugins.atlasskills.commands.user;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class HelpCommand extends AbstractCommand {

    private Main main;
    public HelpCommand(Main main) {this.main = main;}

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String label, List<String> args) {
        // if all checks, check out then move on to the command
        sender.sendMessage(Main.color("&c&m&l--------------&f&l [&x&F&F&3&C&3&C&lA&x&F&F&4&F&3&E&lt&x&F&E&6&2&4&1&ll&x&F&E&7&5&4&3&la&x&F&D&8&8&4&6&ls &x&F&D&9&C&4&8&lS&x&F&D&A&F&4&A&lk&x&F&C&C&2&4&D&li&x&F&C&D&5&4&F&ll&x&F&B&E&8&5&2&ll&x&F&B&F&B&5&4&ls&f&l] &c&m&l---------------"));
        sender.sendMessage(Main.color(""));
        sender.sendMessage(Main.color("&c● &7Reload command: &c/askills reload"));
        sender.sendMessage(Main.color("&c● &7reloads the Atlas Skills configs"));
        sender.sendMessage(Main.color(""));
        sender.sendMessage(Main.color("&c&m&l-----------------------------------------"));
    }

    @Override
    public void complete(JavaPlugin plugin, CommandSender sender, String label, List<String> args, List<String> completions) {

    }

    @Override
    public List<String> getLabels() {
        return Collections.singletonList("help");
    }

    @Override
    public String getPermission() {
        return "atlaskills.help";  // permission required for help command
    }
}


