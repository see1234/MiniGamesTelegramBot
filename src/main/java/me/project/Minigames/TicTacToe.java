package me.project.Minigames;

import me.project.Main.Main;
import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class TicTacToe extends Minigame {


    @Override
    public void MessageHandler(User user, long chatId, String msg) {
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
           Main.getInstance().bot.getBot().sendKeyBoardInMessage(chatId, User.users.get(chatId).nickname + " Начали!!!", keyboard);
           user.hash.put("key", "X");
       }

    }
    @Override
    public void ClickHandler(User user, long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup) {
        for (int i = 0; i < 9; i++) {
            if (msg.equals(String.valueOf(i))) {
                int buttonId = Integer.parseInt(msg);
                if (buttonId > 5) {
                    keyboardMarkup.getKeyboard().get(2).get(buttonId - 6).setText(user.hash.get("key"));
                } else if (buttonId > 2) {
                    keyboardMarkup.getKeyboard().get(1).get(buttonId - 3).setText(user.hash.get("key"));
                } else {
                    keyboardMarkup.getKeyboard().get(0).get(buttonId).setText(user.hash.get("key"));
                }
                Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Крестики-нолики", keyboardMarkup);
                user.hash.put("key", user.hash.get("key") == "X" ? "O" : "X");
                isWin(keyboardMarkup, chatId, messageId);
            }
        }
    }

    public void isWin(InlineKeyboardMarkup keyboardMarkup, long chatId, long messageId) {
        ArrayList<String> ans = new ArrayList<String>();
        for (int i = 0;i < 3;i++) {
            String temp = keyboardMarkup.getKeyboard().get(i).get(0).getText() + keyboardMarkup.getKeyboard().get(i).get(1).getText() + keyboardMarkup.getKeyboard().get(i).get(2).getText();
            ans.add(temp);
        }
        for (int i = 0;i < 3;i++) {
            String temp = keyboardMarkup.getKeyboard().get(0).get(i).getText() + keyboardMarkup.getKeyboard().get(1).get(i).getText() + keyboardMarkup.getKeyboard().get(2).get(i).getText();
            ans.add(temp);
        }
        int count = 0;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3;j++) {
                if (keyboardMarkup.getKeyboard().get(i).get(j).getText() == "X" || keyboardMarkup.getKeyboard().get(i).get(j).getText() == "O") {
                    count += 1;
                }
            }

        }

        String temp = keyboardMarkup.getKeyboard().get(0).get(0).getText() + keyboardMarkup.getKeyboard().get(1).get(1).getText() + keyboardMarkup.getKeyboard().get(2).get(2).getText();
        ans.add(temp);
        temp = keyboardMarkup.getKeyboard().get(0).get(2).getText() + keyboardMarkup.getKeyboard().get(1).get(1).getText() + keyboardMarkup.getKeyboard().get(2).get(0).getText();
        ans.add(temp);
        if (ans.contains("XXX")) {
            Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Выиграл крестик", null);
        }
        else if (ans.contains("OOO")) {
            Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Выиграл нолик", null);
        }
        else if (count == 9) {
            Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Ничья", null);
        }


    }
}
