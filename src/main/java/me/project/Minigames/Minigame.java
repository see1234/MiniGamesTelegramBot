package me.project.Minigames;

import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class Minigame {
    public void sendTutorial() {

    }
    public abstract void MessageHandler(User user, long chatId, String msg);
    public abstract void ClickHandler(User user,long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup);
}
