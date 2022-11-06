package me.project.Telegram;

import me.project.Minigames.Minigame;
import me.project.Minigames.TicTacToe;
import org.telegram.telegrambots.bots.*;
import java.util.function.*;

import org.telegram.telegrambots.meta.api.objects.*;

import org.telegram.telegrambots.meta.api.methods.send.*;

import org.telegram.telegrambots.meta.exceptions.*;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import java.util.*;

public class TelegramBot extends TelegramLongPollingBot
{
    private final String botUsername;
    private final String botToken;





    public void sendMessage(int message, String msg) {
        try {
            execute(SendMessage.builder().chatId(message + "").text(msg).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(final Update update) {
        try {
            final Message message = update.getMessage();
            ArrayList<Minigame> minigames = new ArrayList<Minigame>();
            minigames.add(new TicTacToe());
            for (Minigame game : minigames) {
                if (update.hasMessage() && message.hasText()) {
                    String query = message.getText();
                    long chatId = message.getFrom().getId();
                }
                if (update.hasCallbackQuery()) {
                    String query = update.getCallbackQuery().getData();
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                }
            }
        }
        catch (Throwable $ex) {
            throw $ex;
        }
    }
    

    

    
    @Override
    public String getBotUsername() {
        return this.botUsername;
    }
    
    @Override
    public String getBotToken() {
        return this.botToken;
    }
    

    
    public TelegramBot(final String botUsername, final String botToken) {

        this.botUsername = botUsername;
        this.botToken = botToken;
    }
}