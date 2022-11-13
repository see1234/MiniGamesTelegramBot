package me.project.Minigames;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class Minigame {
    public void sendTutorial() {

    }
    public abstract void MessageHandler(long chatId, String msg);
    public abstract void ClickHandler(long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup);
}
