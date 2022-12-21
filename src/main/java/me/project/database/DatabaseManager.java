package me.project.database;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import me.project.ConfigManager.ConfigHandler;
import me.project.Main.Config;
import me.project.Main.Main;


public class DatabaseManager {
    private Config config;
    private String host;
    private String name;
    private String name_table;
    private String user;
    private int port;
    private String password;
    private String type;
    private Connection connection;

    public DatabaseManager(Main main) {
        host = ConfigHandler.config.host;
        name = ConfigHandler.config.name;
        name_table = ConfigHandler.config.name_table;
        user = ConfigHandler.config.user;
        password = ConfigHandler.config.password;
        port = ConfigHandler.config.port;
        type = ConfigHandler.config.type;
        openConnection();
        loadTables();
    }



    private void openConnection() {
        if (!isConnected()) {
            try {
                String type = this.type.toUpperCase();
                if(type == "MYSQL") {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + name + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true", user, password);
                }
                else {
                    Class.forName("org.sqlite.JDBC");
                    Path currentRelativePath = Paths.get("");

                    connection = DriverManager.getConnection("jdbc:sqlite:" +  currentRelativePath.toAbsolutePath().toString() + File.separator + "database.db");
                }
            } catch (SQLException ex) {
            } catch (ClassNotFoundException ex) {
            }
        }
    }

    private void loadTables() {
        if (isConnected()) {
            update("CREATE TABLE IF NOT EXISTS " + name_table + "(name VARCHAR(16), level INT(16), exp DOUBLE(15,5), resources DOUBLE(15,5), crystals INT(16), blocks INT(16), kills INT(16), deaths INT(16), boss_kills INT(16), last_kit LONG, stat_log VARCHAR(256), block_log VARCHAR(2560), mob_log VARCHAR(2560), quest_log VARCHAR(1024), artefacts VARCHAR(512), PRIMARY KEY (name))");
        }
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ex) {
            }
        }
    }

    public void update(String query) {
        if (isConnected()) {
            try {
                connection.prepareStatement(query).executeUpdate();
            } catch (SQLException ex) {
            }
        }
    }

    public ResultSet getResult(String query) {
        if (isConnected()) {
            try {
                return connection.prepareStatement(query).executeQuery();
            } catch (SQLException ex) {
            }
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public String getDBName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }
}