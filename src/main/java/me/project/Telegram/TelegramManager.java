package me.project.Telegram;


import org.telegram.telegrambots.meta.*;
import org.telegram.telegrambots.meta.generics.*;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramManager {
    private TelegramBot bot;


    public TelegramManager(String token, String gameToken) {
        try {
            final TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this.bot = new TelegramBot(token, gameToken));
        } catch (Throwable $ex) {
            $ex.printStackTrace();
        }
    }

    public TelegramBot getBot() {
        return this.bot;
    }

}
