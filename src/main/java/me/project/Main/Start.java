package me.project.Main;

import me.project.ConfigManager.ConfigHandler;

import me.project.Telegram.TelegramManager;

import java.io.File;
import java.io.IOException;

public class Start {

    public static void main(String[] args) {
        printWelcome();
        Main main = new Main();
        main.console.print("VERSION: " + main.version);
    }

    public static void printWelcome() {
        System.out.println("TELEGRAM BOT WITH MINIGAMES, and work on UBUNTU/DEBIAN");
    }
}
