package com.atlasplugins.atlasskills.commands.user;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.commands.AbstractCommand;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;

public class Test extends AbstractCommand {

    private Main main;
    public Test(Main main) {this.main = main;}

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String label, List<String> args) {
        // if all checks, check out then move on to the command
        Player p = (Player) sender;

        // Make a boss bar
        UIManager.getBossBarManager().createBossBar(p, Main.color("&c&lPROGRESS"), BarColor.WHITE, BarStyle.SEGMENTED_10, .5);

        // Hide Bossbar after x amount of seconds
        UIManager.getBossBarManager().hideProgressBar(p, 2);

        UIManager.getActionBarManager().createTimerBar(p, "&a&lCOMPLETE!", "&6&lLOADING ", 5, "&e", "&8");

        new BukkitRunnable() {
            @Override
            public void run() {
                // Code to execute after the delay
                if(UIManager.getActionBarManager().getHasCompleted())
                {
                    p.sendMessage(Main.color("&4I COMPLETED THE LOADING BAR!"));
                }
                // Add any additional code you want to execute here
            }
        }.runTaskLater(plugin, 5 * 21); // delayInSeconds * 20 ticks = delayInSeconds seconds
    }

    @Override
    public void complete(JavaPlugin plugin, CommandSender sender, String label, List<String> args, List<String> completions) {

    }

    @Override
    public List<String> getLabels() {
        return Collections.singletonList("test");
    }

    @Override
    public String getPermission() {
        return null;  // permission required for help command
    }
}
