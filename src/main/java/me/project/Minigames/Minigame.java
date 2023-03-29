package me.project.Minigames;

import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;

public abstract class Minigame {
    public static HashMap<Long,String> players = new HashMap<Long, String>();
    public void sendTutorial() {

    }
    public static HashMap<Long,String> getPlayers() {
        return players;
    }
    public abstract void MessageHandler(User user, long chatId, String msg);
    public abstract void ClickHandler(User user,long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup, String query_msg);
}
