package com.atlasplugins.atlasskills.managers.uiapi;

public class UIManager {
    private static final BossBarManager bossBarManager = new BossBarManager();
    private static final TabMenuManager tabMenuManager = new TabMenuManager();
    private static final ScoreboardManagerr scoreboardManager = new ScoreboardManagerr();
    private static final ActionBarManager actionBarManager = new ActionBarManager();

    public static BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public static TabMenuManager getTabMenuManager() {
        return tabMenuManager;
    }

    public static ScoreboardManagerr getScoreboardManager() {
        return scoreboardManager;
    }

    public static ActionBarManager getActionBarManager() {
        return actionBarManager;
    }
}

