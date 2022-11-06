package me.project.Main;

import me.project.ConfigManager.ConfigHandler;
import me.project.Telegram.TelegramManager;

public class Main {
    public TelegramManager bot;
    public Main() {
        onEnable();
    }

    public void onEnable() {
        ConfigHandler.loadConfig();
        bot = new TelegramManager(ConfigHandler.config.getBotToken(), ConfigHandler.config.getBotName());
    }

    public void onDisable() {

    }
}
