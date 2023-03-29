package me.project.Main;

import me.project.ConfigManager.ConfigHandler;
import me.project.ConsoleManager.Console;
import me.project.Minigames.*;
import me.project.Telegram.TelegramBot;
import me.project.Telegram.TelegramManager;
import me.project.database.DatabaseManager;
import me.project.helper.Helper;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;

public class Main {
    public TelegramManager bot;
    public ArrayList<Minigame> minigames;
    public static Main instance;
    public Console console;
    public String version;
    public Main() { onEnable(); }
    private DatabaseManager databaseManager;

    public ArrayList<Minigame> getMinigames() {
        return minigames;
    }

    public void onEnable() {
        instance = this;
        version = "1.0.0 BETA";
        databaseManager = new DatabaseManager();
       inilizateThreads();
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
        minigames.add(new TicTacToeWithoutBot());
        minigames.add(new Help());
        minigames.add(new Sapper());
        minigames.add(new RegisterInList());
    }
    private void inilizateThreads() {
        console = new Console();
        console.start();
        new Helper().start();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void onDisable() {
        bot.getBot().onClosing();
        databaseManager.closeConnection();
        instance = null;
        ConfigHandler.config = null;
        minigames = null;
        System.exit(0);
    }
}
