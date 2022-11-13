package me.project.Telegram;


import org.telegram.telegrambots.meta.*;
import org.telegram.telegrambots.meta.generics.*;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramManager {
    private TelegramBot bot;


    public TelegramManager(String botName,String botToken) {
        try {
            final TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this.bot = new TelegramBot(botName, botToken));
        } catch (Throwable $ex) {
            $ex.printStackTrace();
        }
    }

    public TelegramBot getBot() {
        return this.bot;
    }

}
