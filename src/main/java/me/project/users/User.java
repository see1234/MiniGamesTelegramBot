package me.project.users;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class User {

    public long chatId;
    public String nickname;
    public int game_id;
    public HashMap<String, Object> hash = new HashMap<String, Object>();
    public static ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<Long, User>();

    public User(long chatId) {
        if(!hasPlayer(chatId)) {
            this.chatId = chatId;
            this.nickname = "Player" + new Random().nextInt(1000);
            this.game_id = -1;
            users.put(chatId, this);
        }
    }//Скоро будет SQL

    public static boolean hasPlayer(long chatId) {
        return users.containsKey(chatId);
    }

    public static User getUser(long chatId) {
        if(hasPlayer(chatId)) {
            return users.get(chatId);
        }
        else {
            return new User(chatId);
        }
    }
}
