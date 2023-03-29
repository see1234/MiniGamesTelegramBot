package me.project.Minigames;

import me.project.Main.Main;
import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Help extends Minigame {
    @Override
    public void MessageHandler(User user, long chatId, String msg) {
        if(msg.contains("/start")) {
            Main.getInstance().bot.getBot().sendMessage(chatId, "Telegram Bot with MiniGames:" +
                                                              "\n" + "TGMINIGAMES - игровой бот.\n" +
                    "\n" +
                    "Реализована поддержка игр: \"Сапер\", \"Крестики-нолики\"" +
                    "\n" +
                    "Навигация в меню помощи - \"/menu\"\n" +
                    "\n" +
                    "Автор - see1\n");

        }
        else if(msg.contains("/menu")) {
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            List<InlineKeyboardButton> buttons1 = new ArrayList<>();
            buttons1.add(InlineKeyboardButton.builder().text("Сапер").callbackData("/sapper").build());
            List<InlineKeyboardButton> buttons2 = new ArrayList<>();
            buttons2.add(InlineKeyboardButton.builder().text("Крестики-нолики").callbackData("/tictactoe").build());
            List<InlineKeyboardButton> buttons3 = new ArrayList<>();
            buttons3.add(InlineKeyboardButton.builder().text("Крестики-нолики без бота").callbackData("/tictactoewithbot").build());
            buttons.add(buttons1);
            buttons.add(buttons2);
            buttons.add(buttons3);
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(buttons).build();
            keyboard.setKeyboard(buttons);
            Main.getInstance().bot.getBot().sendKeyBoardInMessage(chatId, "Меню-клавиатура", keyboard);
        }
        else if(msg.contains("/cancel")) {
            if(players.containsKey(chatId)) {
                players.remove(chatId);
            }
        }

    }
    @Override
    public void ClickHandler(User user, long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup, String query_msg) {


           for (Minigame minigame : Main.getInstance().getMinigames()) {
                 if(!minigame.getClass().getName().contains("Help")) {

                     minigame.MessageHandler(user, chatId, msg);
                 }
           }

    }


}
