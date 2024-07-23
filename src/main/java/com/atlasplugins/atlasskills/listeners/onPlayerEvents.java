package com.atlasplugins.atlasskills.listeners;

import com.atlasplugins.atlasskills.Main;
import com.atlasplugins.atlasskills.managers.uiapi.UIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class onPlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        // Saves the players data
        Main.instance.getLevelManager().loadPlayerData(p);
    }


    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();

        // Saves the players data
        Main.instance.getLevelManager().savePlayerData(p);

        UIManager.getBossBarManager().removeBossBar(p);
        UIManager.getScoreboardManager().clearScoreboard(p);
    }
}
