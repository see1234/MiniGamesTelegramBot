package me.project.Main;

import me.project.ConfigManager.ConfigHandler;
import me.project.ConsoleManager.Console;
import me.project.Minigames.Minigame;
import me.project.Minigames.TicTacToe;
import me.project.Telegram.TelegramManager;

import java.util.ArrayList;

public class Main {
    public TelegramManager bot;
    public ArrayList<Minigame> minigames;
    public static Main instance;
    public Console console;
    public String version;
    public Main() { onEnable(); }

    public void onEnable() {
        instance = this;
        version = "1.0.0 BETA";
        startConsole();
        ConfigHandler.loadConfig();
        registerGames();
        registerBot();
    }

    public static Main getInstance() {
        return instance;
    }

    private void registerBot() {
        bot = new TelegramManager(ConfigHandler.config.getBotName(), ConfigHandler.config.getBotToken());
    }
    private void registerGames() {
        minigames = new ArrayList<Minigame>();
        minigames.add(new TicTacToe());
    }
    private void startConsole() {
        console = new Console();
        console.start();
    }

    public void onDisable() {
        bot.getBot().onClosing();
        instance = null;
        ConfigHandler.config = null;
        minigames = null;
        System.exit(0);
    }
}
