package me.project.Main;

import me.project.ConfigManager.ConfigHandler;
import me.project.Minigames.Minigame;
import me.project.Minigames.TicTacToe;
import me.project.Telegram.TelegramManager;

import java.util.ArrayList;

public class Main {
    public static TelegramManager bot;
    public static ArrayList<Minigame> minigames;
    public Main() {
        onEnable();
    }

    public void onEnable() {
        ConfigHandler.loadConfig();
        registerGames();
        bot = new TelegramManager(ConfigHandler.config.getBotName(), ConfigHandler.config.getBotToken());
    }
    private void registerGames() {
        minigames = new ArrayList<Minigame>();
        minigames.add(new TicTacToe());
    }
    public void onDisable() {
        minigames = null;
    }
}
