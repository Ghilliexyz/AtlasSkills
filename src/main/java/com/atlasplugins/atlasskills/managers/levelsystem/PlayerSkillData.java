package com.atlasplugins.atlasskills.managers.levelsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerSkillData {
    private UUID uuid;
    private int totalLevel;
    private int totalXP;

    public PlayerSkillData(UUID uuid, int totalLevel, int totalXP) {
        this.uuid = uuid;
        this.totalLevel = totalLevel;
        this.totalXP = totalXP;
    }

    public UUID getUuid() {
        return uuid;
    }

//    public String getName() {
//        // You would need a method to get the player's name from the UUID, possibly using Bukkit's API.
//        return Bukkit.getOfflinePlayer(uuid).getName();
//
//    }

    public String getName() {
        String name = Bukkit.getOfflinePlayer(uuid).getName();
        return name != null ? name : "Unknown Player";
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }


    public int getTotalLevel() {
        return totalLevel;
    }

    public int getTotalXP() {
        return totalXP;
    }
}

