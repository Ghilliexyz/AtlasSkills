package com.atlasplugins.atlasskills.commands.user;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class SkillsCommand extends AbstractCommand {

    private Main main;
    public SkillsCommand(Main main) {this.main = main;}

    @Override
    public void execute(JavaPlugin plugin, CommandSender sender, String label, List<String> args) {
        Player player = (Player) sender;

        // open gui
        main.openSkillsGui(player);

        // if all checks, check out then move on to the command
//        player.sendMessage(Main.color("&c&m&l------------&f&l [&x&F&F&3&C&3&C&lA&x&F&F&4&F&3&E&lt&x&F&E&6&2&4&1&ll&x&F&E&7&5&4&3&la&x&F&D&8&8&4&6&ls &x&F&D&9&C&4&8&lS&x&F&D&A&F&4&A&lk&x&F&C&C&2&4&D&li&x&F&C&D&5&4&F&ll&x&F&B&E&8&5&2&ll&x&F&B&F&B&5&4&ls&f&l] &c&m&l-------------"));
//        player.sendMessage(Main.color(""));
//        player.sendMessage(Main.color("&c● &7Player: &c&l" + player.getPlayer().getName()));
//        player.sendMessage(Main.color(""));
//        player.sendMessage(Main.color("&c● &eAcrobatics &7Lvl: &e" + acrobaticsLevel + " &7| XP: &e" + acrobaticsXp + " &7/ &e"+ acrobaticsXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eAlchemy &7Lvl: &e" + alchemyLevel + " &7| XP: &e" + alchemyXp + " &7/ &e"+ alchemyXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eArchery &7Lvl: &e" + archeryLevel + " &7| XP: &e" + archeryXp + " &7/ &e"+ archeryXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eAxes &7Lvl: &e" + axesLevel + " &7| XP: &e" + axesXp + " &7/ &e"+ axesXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eExcavation &7Lvl: &e" + excavationLevel + " &7| XP: &e" + excavationXp + " &7/ &e"+ excavationXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eFishing &7Lvl: &e" + fishingLevel + " &7| XP: &e" + fishingXp + " &7/ &e"+ fishingXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eHerbalism &7Lvl: &e" + herbalismLevel + " &7| XP: &e" + herbalismXp + " &7/ &e"+ herbalismXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eMining &7Lvl: &e" + miningLevel + " &7| XP: &e" + miningXp + " &7/ &e"+ miningXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eSwords &7Lvl: &e" + swordLevel + " &7| XP: &e" + swordXp + " &7/ &e"+ swordXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eTaming &7Lvl: &e" + tamingLevel + " &7| XP: &e" + tamingXp + " &7/ &e"+ tamingXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eUnarmed &7Lvl: &e" + unarmedLevel + " &7| XP: &e" + unarmedXp + " &7/ &e"+ unarmedXpToNextLvl));
//        player.sendMessage(Main.color("&c● &eWoodcutting &7Lvl: &e" + woodcuttingLevel + " &7| XP: &e" + woodcuttingXp + " &7/ &e"+ woodcuttingXpToNextLvl));
//        player.sendMessage(Main.color(""));
//        player.sendMessage(Main.color("&c&m&l-------------------------------------"));
    }

    @Override
    public void complete(JavaPlugin plugin, CommandSender sender, String label, List<String> args, List<String> completions) {

    }

    @Override
    public List<String> getLabels() {
        return Collections.singletonList("skills");
    }

    @Override
    public String getPermission() {
        return null;  // permission required for help command
    }
}


