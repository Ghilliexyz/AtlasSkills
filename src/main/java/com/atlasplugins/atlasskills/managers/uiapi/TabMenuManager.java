package com.atlasplugins.atlasskills.managers.uiapi;

import com.atlasplugins.atlasskills.Main;
import org.bukkit.entity.Player;

public class TabMenuManager {

    public void setPlayerListHeaderFooter(Player player, String header, String footer) {
        player.setPlayerListHeaderFooter(Main.color(header), Main.color(footer));
    }
}