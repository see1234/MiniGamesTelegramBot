package me.project.Minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.project.Main.Main;
import me.project.users.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Sapper extends Minigame {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int MINES = 10;
    private static final int[][] NEIGHBORS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};





    @Override
    public void MessageHandler(User user, long chatId, String msg) {
        if(msg.equals("/sapper")) {
            int[][] board = new int[ROWS][COLS];
            boolean[][] revealed = new boolean[ROWS][COLS];
            boolean[][] flagged = new boolean[ROWS][COLS];
            boolean gameOver = false;
            user.hash.put("sapper_board", board);
            user.hash.put("sapper_revealed", revealed);
            user.hash.put("sapper_flagged", flagged);
            user.hash.put("sapper_gameOver", gameOver);
            user.hash.put("sapper_mode", false);
            initializeBoard(user);
            sendBoard(user, chatId, "Игра сапера");
            players.put(chatId, this.getClass().getName());

        }
    }

    @Override
    public void ClickHandler(User user, long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup, String query_msg) {

        if(msg.equals("sapper")) {
            int[][] board = new int[ROWS][COLS];
            boolean[][] revealed = new boolean[ROWS][COLS];
            boolean[][] flagged = new boolean[ROWS][COLS];
            boolean gameOver = false;
            user.hash.put("sapper_board", board);
            user.hash.put("sapper_revealed", revealed);
            user.hash.put("sapper_flagged", flagged);
            user.hash.put("sapper_gameOver", gameOver);
            user.hash.put("sapper_mode", false);
            initializeBoard(user);
            sendBoard(user, chatId, "Игра сапера");
            players.put(chatId, this.getClass().getName());
            return;
        }
        if(!query_msg.contains("Игра сапера")) {
            return;
        }
        if(msg.contains("mode")) {
            if(((boolean)user.hash.get("sapper_mode")) == true) {
                user.hash.put("sapper_mode", false);
            }
            else {
                user.hash.put("sapper_mode", true);
            }
            reloadBoard(user, chatId, messageId, "Игра сапера");
        }
        if(((boolean)user.hash.get("sapper_mode")) != true) {


            String[] data = msg.split(",");
            int row = Integer.parseInt(data[0]);
            int col = Integer.parseInt(data[1]);

            if (((boolean[][]) user.hash.get("sapper_flagged"))[row][col]) {
                reloadBoard(user, chatId, messageId, "Игра сапера");
                sendText(chatId, "Эта клетка уже помечена.");
            } else if (((int[][]) user.hash.get("sapper_board"))[row][col] == -1) {
                revealCell(user, row, col);
                reloadBoard(user, chatId, messageId, "Вы проиграли вы попались на мину");

                players.remove("Sapper");
                user.hash.put("sapper_gameOver", true);
                user.hash.clear();
            } else {
                revealCell(user, row, col);
                if (checkWin(user)) {

                    reloadBoard(user, chatId, messageId, "Поздравляю вы выиграли в сапере!!!");

                    players.remove("Sapper");
                    user.hash.put("sapper_gameOver", true);
                    user.hash.clear();
                } else {
                    reloadBoard(user, chatId, messageId, "Игра сапера");
                }
            }
        }
        else {
            String[] data = msg.split(",");
            int row = Integer.parseInt(data[0]);
            int col = Integer.parseInt(data[1]);
            if(((boolean[][])user.hash.get("sapper_flagged"))[row][col]) {
                ((boolean[][]) user.hash.get("sapper_flagged"))[row][col] = false;
            }
            else {
                ((boolean[][]) user.hash.get("sapper_flagged"))[row][col] = true;
            }
            reloadBoard(user, chatId, messageId, "Игра сапера");
        }
    }


    private void initializeBoard(User user) {
        Random random = new Random();
        int count = 0;
        while (count < MINES) {
            int row = random.nextInt(ROWS);
            int col = random.nextInt(COLS);
            if (((int[][])user.hash.get("sapper_board"))[row][col] != -1) {
                ((int[][])user.hash.get("sapper_board"))[row][col] = -1;
                count++;
            }
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (((int[][])user.hash.get("sapper_board"))[row][col] != -1) {
                    int countMines = 0;
                    for (int[] neighbor : NEIGHBORS) {
                        int r = row + neighbor[0];
                        int c = col + neighbor[1];
                        if (r >= 0 && r < ROWS && c >= 0 && c < COLS && ((int[][])user.hash.get("sapper_board"))[r][c] == -1) {
                            countMines++;
                        }
                    }
                    ((int[][])user.hash.get("sapper_board"))[row][col] = countMines;
                }
            }
        }
    }
    private void reloadBoard(User user, long chatId, long messageId, String text) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            for (int col = 0; col < COLS; col++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                if (((boolean[][])user.hash.get("sapper_flagged"))[row][col]) {
                    button.setText("\uD83D\uDEA9");
                } else if (!((boolean[][])user.hash.get("sapper_revealed"))[row][col]) {
                    button.setText("•");
                } else if (((int[][])user.hash.get("sapper_board"))[row][col] == -1) {
                    button.setText("\uD83D\uDCA3");
                } else {
                    if(((int[][])user.hash.get("sapper_board"))[row][col] == 0) {
                        button.setText(" ");
                    }
                    else if(((int[][])user.hash.get("sapper_board"))[row][col] == 1) {
                        button.setText("1");
                    }
                    else if(((int[][])user.hash.get("sapper_board"))[row][col] == 2) {
                        button.setText("2");
                    }
                    else if(((int[][])user.hash.get("sapper_board"))[row][col] == 3) {
                        button.setText("3");
                    }
                    else if(((int[][])user.hash.get("sapper_board"))[row][col] == 4) {
                        button.setText("4");
                    }
                    else if(((int[][])user.hash.get("sapper_board"))[row][col] == 5) {
                        button.setText("5");
                    }
                    else if(((int[][])user.hash.get("sapper_board"))[row][col] == 6) {
                        button.setText("6");
                    }
                }
                button.setCallbackData(row + "," + col);
                rowButtons.add(button);
            }
            keyboard.add(rowButtons);
        }
        if(((boolean)user.hash.get("sapper_mode")) != true) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Режим: 'Clicker'");
            button.setCallbackData("mode");
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            rowButtons.add(button);
            keyboard.add(rowButtons);
        }
        else {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Режим: 'Flagger'");
            button.setCallbackData("mode");
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            rowButtons.add(button);
            keyboard.add(rowButtons);
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(markup);

        Main.getInstance().bot.getBot().editMessage(chatId, messageId, text, markup);
    }
    private void sendBoard(User user, long chatId, String text) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            for (int col = 0; col < COLS; col++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                if (((boolean[][])user.hash.get("sapper_flagged"))[row][col]) {
                    button.setText("\uD83D\uDEA9");
                } else if (!((boolean[][])user.hash.get("sapper_revealed"))[row][col]) {
                    button.setText("•");
                } else if (((int[][])user.hash.get("sapper_board"))[row][col] == -1) {
                    button.setText("\uD83D\uDCA3");
                } else {

                    button.setText(Integer.toString(((int[][])user.hash.get("sapper_board"))[row][col]));
                }
                button.setCallbackData(row + "," + col);
                rowButtons.add(button);
            }

            keyboard.add(rowButtons);
        }
        if(((boolean)user.hash.get("sapper_mode")) != true) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Режим: 'Clicker'");
            button.setCallbackData("mode");
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            rowButtons.add(button);
            keyboard.add(rowButtons);
        }
        else {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Режим: 'Flagger'");
            button.setCallbackData("mode");
            List<InlineKeyboardButton> rowButtons = new ArrayList<>();
            rowButtons.add(button);
            keyboard.add(rowButtons);
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(markup);

        Main.getInstance().bot.getBot().sendKeyBoardInMessage(chatId, "Игра сапера", markup);
    }

    private void sendText(long chatId, String text) {
        Main.getInstance().bot.getBot().sendMessage(chatId,text);
    }

    private void revealCell(User user, int row, int col) {
        if (((boolean[][])user.hash.get("sapper_revealed"))[row][col]) {
            return;
        }
        ((boolean[][])user.hash.get("sapper_revealed"))[row][col] = true;
        if (((int[][])user.hash.get("sapper_board"))[row][col] == 0) {
            for (int[] neighbor : NEIGHBORS) {
                int r = row + neighbor[0];
                int c = col + neighbor[1];
                if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
                    revealCell(user,r, c);
                }
            }
        }
    }

    private boolean checkWin(User user) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!((boolean[][])user.hash.get("sapper_revealed"))[row][col] && ((int[][])user.hash.get("sapper_board"))[row][col] != -1) {
                    return false;
                }
            }
        }
        return true;
    }


}