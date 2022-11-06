package me.project.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private String botToken;
    private String botName;



    Config() {}

    public String getBotToken() {
        return botToken;
    }
    public void setBotToken(String token) {
        this.botToken = token;
    }
    public String getBotName() {
        return botName;
    }
    public void setBotName(String name) {
        this.botName = name;
    }



    @Override
    public String toString() {
        return "Config [botToken=" + botToken + ", botName=" + botName + "]";
    }
}
