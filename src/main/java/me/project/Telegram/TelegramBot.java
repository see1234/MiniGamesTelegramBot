package me.project.Telegram;

import me.project.Main.Main;
import me.project.Minigames.Minigame;
import me.project.Minigames.TicTacToe;
import me.project.users.User;
import org.telegram.telegrambots.bots.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.*;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;

import org.telegram.telegrambots.meta.api.methods.send.*;

import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
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

    public void sendMessage(long chatId, String msg) {
        try {
            execute(SendMessage.builder().chatId(chatId + "").text(msg).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendImageFromUrl(String url, String chatId) {
        // Create send method
        try {
            SendPhoto sendPhotoRequest = new SendPhoto();
            // Set destination chat id
            sendPhotoRequest.setChatId(chatId);
            // Set the photo url as a simple photo
            sendPhotoRequest.setPhoto(new InputFile(url));
            try {
                // Execute the method
                execute(sendPhotoRequest);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        catch(Exception ex) {
ex.printStackTrace();
        }
    }
    public void getIdUserName() {

    }
    public void sendFile(String url, String chatId) {
        // Create send method
        try {
            SendDocument sendDocumentRequest = new SendDocument();
            // Set destination chat id
            sendDocumentRequest.setChatId(chatId);
            // Set the photo url as a simple photo
            sendDocumentRequest.setDocument(new InputFile(url));
            try {
                // Execute the method
                execute(sendDocumentRequest);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendKeyBoardInMenu(long chatId, String msg, ReplyKeyboardMarkup replykeyboardmarkup) {
        try {
            execute(SendMessage.builder().replyMarkup(replykeyboardmarkup).chatId(chatId + "").text(msg).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendKeyBoardInMessage(long chatId, String msg, InlineKeyboardMarkup inlinekeyboardmarkup) {
        try {
            execute(SendMessage.builder().replyMarkup(inlinekeyboardmarkup).chatId(chatId + "").text(msg).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void editMessage(long chatId, long messageId, String msg, InlineKeyboardMarkup inlinekeyboardmarkup) {
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(String.valueOf(chatId));
        new_message.setMessageId(Math.toIntExact(messageId));
        new_message.setText(msg);
        if(inlinekeyboardmarkup != null) {
            new_message.setReplyMarkup(inlinekeyboardmarkup);
        }
        try {

            execute(new_message);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void callbackAnswer(String callbackId) throws IOException, InterruptedException {
        HttpClient telegramApiClient = HttpClient.newHttpClient();
        HttpRequest telegramCallbackAnswerReq = HttpRequest.newBuilder(URI
                        .create("https://api.telegram.org/bot" + Main.getInstance().bot.getBot().botToken +"/answerCallbackQuery?callback_query_id=" + callbackId))
                .GET().build();

        telegramApiClient.send(telegramCallbackAnswerReq, HttpResponse.BodyHandlers
                .ofString());
    }
    @Override
    public void onUpdateReceived(final Update update) {
        try {
            final Message message = update.getMessage();

            if (update.hasMessage() && message.hasText()) {
                String query = message.getText();
                long chatId = message.getFrom().getId();
                Main.getInstance().console.print("[" + chatId + " | " + message.getFrom().getUserName() + "]" + " (MESSAGE) " + query);
            }
            if (update.hasCallbackQuery()) {
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                String query = update.getCallbackQuery().getData();
                Main.getInstance().console.print("[" + chatId + " | " + update.getCallbackQuery().getFrom().getUserName() + "]" + " (BUTTON) " + query);
            }
            for (Minigame game : Main.getInstance().minigames) {

                if (update.hasMessage() && message.hasText()) {
                    String query = message.getText();
                    long chatId = message.getFrom().getId();
                    User user = User.getUser(chatId);
                    game.MessageHandler(user, chatId, query);

                }
                if (update.hasCallbackQuery()) {
                    String query = update.getCallbackQuery().getData();
                    String querymsg = update.getCallbackQuery().getMessage().getText();

                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    long messageId = update.getCallbackQuery().getMessage().getMessageId();
                    User user = User.getUser(chatId);
                    if(!game.getClass().getName().contains("Help")) {
                        if (!game.getPlayers().containsKey(chatId)) {

                            continue;
                        }

                        if (!game.getPlayers().get(chatId).equals(game.getClass().getName())) {
                            continue;
                        }
                    }
                    InlineKeyboardMarkup keyboardMarkup = update.getCallbackQuery().getMessage().getReplyMarkup();
                    if(keyboardMarkup != null) {
                        game.ClickHandler(user, chatId, messageId, query, keyboardMarkup, querymsg);
                    }
                    else {
                        game.ClickHandler(user, chatId, messageId, query, null, querymsg);
                    }
                    callbackAnswer(update.getCallbackQuery().getId());

                }
            }
        }
        catch (Throwable $ex) {


            try {
                throw $ex;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


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