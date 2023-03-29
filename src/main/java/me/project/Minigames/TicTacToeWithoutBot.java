package me.project.Minigames;

import me.project.Main.Main;
import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class TicTacToeWithoutBot extends Minigame {


    @Override
    public void MessageHandler(User user, long chatId, String msg) {
        if(msg.equals("/tictactoe")) {
            getPlayers().put(chatId,this.getClass().getName());
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
    public void ClickHandler(User user, long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup, String query_msg) {
        if(msg.equals("tictactoe")) {
            getPlayers().put(chatId,this.getClass().getName());
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
            return;
        }
        if(query_msg.contains("Начали")  || query_msg.contains("Крестики-нолики")) {


            if (Integer.parseInt(msg) >= 0 && Integer.parseInt(msg) <= 9) {
                int buttonId = Integer.parseInt(msg);

                if (keyboardMarkup.getKeyboard().get((buttonId / 3)).get((buttonId - ((buttonId / 3) * 3))).getText().equals("X") || keyboardMarkup.getKeyboard().get((buttonId / 3)).get((buttonId - ((buttonId / 3) * 3))).getText().equals("O")) {
                    return;
                }
                keyboardMarkup.getKeyboard().get((buttonId / 3)).get((buttonId - ((buttonId / 3) * 3))).setText((String)user.hash.get("key"));


                user.hash.put("key", user.hash.get("key").equals("X") ? "O" : "X");
                Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Крестики-нолики", keyboardMarkup);
                 isWin(keyboardMarkup, chatId, messageId);

            }
        }
    }

    public String isWin(InlineKeyboardMarkup keyboardMarkup, long chatId, long messageId) {
        String winids = "";
        String[] board = new String[] {"0","0","0","0","0","0","0","0","0"};
        for (int a = 0; a < 9; a++) {
            board[a] = keyboardMarkup.getKeyboard().get((a / 3)).get((a - ((a / 3) * 3))).getText();
            //System.out.print(board[a]);
        }
        String line = null;
        for (int a = 0; a < 8; a++) {
           switch (a) {
                case 0:

                    line = board[0] + board[1] + board[2];
                    winids = "012";
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    winids = "345";
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    winids = "678";
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    winids = "036";
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    winids = "147";
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];

                    winids = "258";
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    winids = "048";
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    winids = "246";
                    break;
            }

            if (line.equals("XXX")) {
                break;
            }

            else if (line.equals("OOO")) {
                break;
            }
        }


        int count = 0;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3;j++) {
                if (keyboardMarkup.getKeyboard().get(i).get(j).getText().equals("X") || keyboardMarkup.getKeyboard().get(i).get(j).getText().equals("O")) {
                    count += 1;
                }
            }

        }


        if (line.equals("XXX")) {

            for(String id : winids.split(""))
            {
                keyboardMarkup.getKeyboard().get((Integer.parseInt(id) / 3)).get((Integer.parseInt(id) - ((Integer.parseInt(id) / 3) * 3))).setText("\u274C");
            }
            Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Выиграл крестик", keyboardMarkup);
            players.remove(chatId);
            return "X";
        }
        else if (line.equals("OOO")) {

            for(String id : winids.split(""))
            {

                keyboardMarkup.getKeyboard().get((Integer.parseInt(id) / 3)).get((Integer.parseInt(id) - ((Integer.parseInt(id) / 3) * 3))).setText("\u2B55");
            }
            Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Выиграл нолик", keyboardMarkup);
            players.remove(chatId);
            return "O";
        }
        else if (count == 9) {
            Main.getInstance().bot.getBot().editMessage(chatId, messageId, "Ничья", keyboardMarkup);
            players.remove(chatId);
            return "null";
        }
        return "game";
    }
}
