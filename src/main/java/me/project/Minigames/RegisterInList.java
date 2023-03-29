package me.project.Minigames;

import me.project.Main.Main;
import me.project.users.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterInList extends Minigame {
    @Override
    public void MessageHandler(User user, long chatId, String msg) {
        if(msg.contains("/registerinlist")) {
            if(!playerExists(chatId + "")) {
                Main.getInstance().bot.getBot().sendMessage(chatId, "�� ����������� �� �������� ����������\n��� ������ ��������� � ���� ������������\n���� ��� �� ��������, ������ �� ��������, ����� ����� ��������, �� ���������� ���������� ���");
                Main.getInstance().getDatabaseManager().update("INSERT INTO " + Main.getInstance().getDatabaseManager().getGameTable() + "(name) VALUES('" + chatId + "')");
            }
            else {
                Main.getInstance().bot.getBot().sendMessage(chatId, "�� ��� ���������������� � ����");
            }
        }
        else if(msg.contains("/unregisterinlist")) {
            if(playerExists(chatId + "")) {
                Main.getInstance().bot.getBot().sendMessage(chatId, "�� ���������� �� ��������, ������ �� �� ������ �������� �������� ����������, ��������.");
                Main.getInstance().getDatabaseManager().update("DELETE FROM " + Main.getInstance().getDatabaseManager().getGameTable() + " WHERE name='" + chatId + "'");
            }
            else {
                Main.getInstance().bot.getBot().sendMessage(chatId, "�� �� �������� ����...");
            }
        }

    }
    @Override
    public void ClickHandler(User user, long chatId, long messageId, String msg, InlineKeyboardMarkup keyboardMarkup, String query_msg) {

    }
    public static boolean playerExists(String chatId) {
        ResultSet resultSet = Main.getInstance().getDatabaseManager().getResult("SELECT * FROM " + "db" + " WHERE name='" + chatId + "'");
        try {
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
        }
        return false;
    }

}
