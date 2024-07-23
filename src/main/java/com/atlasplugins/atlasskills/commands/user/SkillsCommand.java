package com.atlasplugins.atlasskills.commands.user;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.commands.AbstractCommand;
import com.atlasplugins.atlasskills.managers.levelsystem.LevelManager;
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
        LevelManager levelManager = main.getLevelManager();

        int acrobaticsLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.ACROBATICS);
        int acrobaticsXp = levelManager.getXP((Player) sender, LevelManager.Skill.ACROBATICS);
        int acrobaticsXpToNextLvl = levelManager.getXPForNextLevel(acrobaticsLevel);

        int alchemyLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.ALCHEMY);
        int alchemyXp = levelManager.getXP((Player) sender, LevelManager.Skill.ALCHEMY);
        int alchemyXpToNextLvl = levelManager.getXPForNextLevel(alchemyLevel);

        int archeryLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.ARCHERY);
        int archeryXp = levelManager.getXP((Player) sender, LevelManager.Skill.ARCHERY);
        int archeryXpToNextLvl = levelManager.getXPForNextLevel(archeryLevel);

        int axesLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.AXES);
        int axesXp = levelManager.getXP((Player) sender, LevelManager.Skill.AXES);
        int axesXpToNextLvl = levelManager.getXPForNextLevel(axesLevel);

        int excavationLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.EXCAVATION);
        int excavationXp = levelManager.getXP((Player) sender, LevelManager.Skill.EXCAVATION);
        int excavationXpToNextLvl = levelManager.getXPForNextLevel(excavationLevel);

        int fishingLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.FISHING);
        int fishingXp = levelManager.getXP((Player) sender, LevelManager.Skill.FISHING);
        int fishingXpToNextLvl = levelManager.getXPForNextLevel(fishingLevel);

        int herbalismLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.HERBALISM);
        int herbalismXp = levelManager.getXP((Player) sender, LevelManager.Skill.HERBALISM);
        int herbalismXpToNextLvl = levelManager.getXPForNextLevel(herbalismLevel);

        int miningLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.MINING);
        int miningXp = levelManager.getXP((Player) sender, LevelManager.Skill.MINING);
        int miningXpToNextLvl = levelManager.getXPForNextLevel(miningLevel);

        int swordLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.SWORDS);
        int swordXp = levelManager.getXP((Player) sender, LevelManager.Skill.SWORDS);
        int swordXpToNextLvl = levelManager.getXPForNextLevel(swordLevel);

        int tamingLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.TAMING);
        int tamingXp = levelManager.getXP((Player) sender, LevelManager.Skill.TAMING);
        int tamingXpToNextLvl = levelManager.getXPForNextLevel(tamingLevel);

        int unarmedLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.UNARMED);
        int unarmedXp = levelManager.getXP((Player) sender, LevelManager.Skill.UNARMED);
        int unarmedXpToNextLvl = levelManager.getXPForNextLevel(unarmedLevel);

        int woodcuttingLevel = levelManager.getLevel((Player) sender, LevelManager.Skill.WOODCUTTING);
        int woodcuttingXp = levelManager.getXP((Player) sender, LevelManager.Skill.WOODCUTTING);
        int woodcuttingXpToNextLvl = levelManager.getXPForNextLevel(woodcuttingLevel);


        // if all checks, check out then move on to the command
        sender.sendMessage(Main.color("&c&m&l------------&f&l [&x&F&F&3&C&3&C&lA&x&F&F&4&F&3&E&lt&x&F&E&6&2&4&1&ll&x&F&E&7&5&4&3&la&x&F&D&8&8&4&6&ls &x&F&D&9&C&4&8&lS&x&F&D&A&F&4&A&lk&x&F&C&C&2&4&D&li&x&F&C&D&5&4&F&ll&x&F&B&E&8&5&2&ll&x&F&B&F&B&5&4&ls&f&l] &c&m&l-------------"));
        sender.sendMessage(Main.color(""));
        sender.sendMessage(Main.color("&c● &7Player: &c&l" + ((Player) sender).getPlayer().getName()));
        sender.sendMessage(Main.color(""));
        sender.sendMessage(Main.color("&c● &eAcrobatics &7Lvl: &e" + acrobaticsLevel + " &7| XP: &e" + acrobaticsXp + " &7/ &e"+ acrobaticsXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eAlchemy &7Lvl: &e" + alchemyLevel + " &7| XP: &e" + alchemyXp + " &7/ &e"+ alchemyXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eArchery &7Lvl: &e" + archeryLevel + " &7| XP: &e" + archeryXp + " &7/ &e"+ archeryXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eAxes &7Lvl: &e" + axesLevel + " &7| XP: &e" + axesXp + " &7/ &e"+ axesXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eExcavation &7Lvl: &e" + excavationLevel + " &7| XP: &e" + excavationXp + " &7/ &e"+ excavationXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eFishing &7Lvl: &e" + fishingLevel + " &7| XP: &e" + fishingXp + " &7/ &e"+ fishingXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eHerbalism &7Lvl: &e" + herbalismLevel + " &7| XP: &e" + herbalismXp + " &7/ &e"+ herbalismXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eMining &7Lvl: &e" + miningLevel + " &7| XP: &e" + miningXp + " &7/ &e"+ miningXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eSwords &7Lvl: &e" + swordLevel + " &7| XP: &e" + swordXp + " &7/ &e"+ swordXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eTaming &7Lvl: &e" + tamingLevel + " &7| XP: &e" + tamingXp + " &7/ &e"+ tamingXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eUnarmed &7Lvl: &e" + unarmedLevel + " &7| XP: &e" + unarmedXp + " &7/ &e"+ unarmedXpToNextLvl));
        sender.sendMessage(Main.color("&c● &eWoodcutting &7Lvl: &e" + woodcuttingLevel + " &7| XP: &e" + woodcuttingXp + " &7/ &e"+ woodcuttingXpToNextLvl));
        sender.sendMessage(Main.color(""));
        sender.sendMessage(Main.color("&c&m&l-------------------------------------"));
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


