package com.atlasplugins.atlasskills.commands.user;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class LeaderboardCommand extends AbstractCommand {

    private Main main;
    public LeaderboardCommand(Main main) {this.main = main;}

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String label, List<String> args) {
        Player player = (Player) sender;

        // open gui
        main.openLeaderboardGui(player);
    }

    @Override
    public void complete(JavaPlugin plugin, CommandSender sender, String label, List<String> args, List<String> completions) {

    }

    @Override
    public List<String> getLabels() {
        return Collections.singletonList("leaderboard");
    }

    @Override
    public String getPermission() {
        return null;  // permission required for help command
    }
}


