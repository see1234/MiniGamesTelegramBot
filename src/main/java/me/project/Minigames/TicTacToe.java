package me.project.Minigames;

import me.project.Main.Main;
import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TicTacToe extends Minigame {

    @Override
    public void MessageHandler(long chatId, String msg) {
       if(msg.contains("1")) {
           List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
           for(int i = 0; i < 3; i++) {
               ArrayList<InlineKeyboardButton> list_buttons = new ArrayList<>();
               for (int j = i*3; j < (i+1)*3; j++) {
                   list_buttons.add(InlineKeyboardButton.builder().text(" ").callbackData(String.valueOf(j)).build());
               }

               buttons.add(list_buttons);
           }
           InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder().keyboard(buttons).build();
           Main.bot.getBot().sendKeyBoardInMessage(chatId, User.users.get(chatId).nickname + " Начали!!!", keyboard);

       }

    }
    @Override
    public void ClickHandler(long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup) {
        if(msg.equals("0") || msg.equals("1") || msg.equals("2") || msg.equals("3") || msg.equals("4")
        || msg.equals("5") || msg.equals("6") || msg.equals("7") || msg.equals("8")) {
            int buttonId = Integer.parseInt(msg);
            if (buttonId > 5) {
                keyboardMarkup.getKeyboard().get(2).get(buttonId - 6).setText("X");
            }
            else if (buttonId > 2) {
                keyboardMarkup.getKeyboard().get(1).get(buttonId - 3).setText("X");
            }
            else {
                keyboardMarkup.getKeyboard().get(0).get(buttonId).setText("X");
            }
            Main.bot.getBot().editMessage(chatId, messageId, "Крестики-нолики", keyboardMarkup);
        }

    }

}
