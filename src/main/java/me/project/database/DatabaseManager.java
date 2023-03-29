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

import me.project.Main.Main;


public class DatabaseManager {


    private String name_table;

    private Connection connection;

    public DatabaseManager() {
        name_table = "db";
        openConnection();
        loadTables();
    }

    public String getGameTable() {
        return name_table;
    }

    private void openConnection() {
        if (!isConnected()) {
            try {

                    Class.forName("org.sqlite.JDBC");
                    Path currentRelativePath = Paths.get("");

                    connection = DriverManager.getConnection("jdbc:sqlite:" +  currentRelativePath.toAbsolutePath().toString() + File.separator + "database.db");

            } catch (SQLException ex) {
            } catch (ClassNotFoundException ex) {
            }
        }
    }

    private void loadTables() {
        if (isConnected()) {
            update("CREATE TABLE IF NOT EXISTS " + name_table + "(name VARCHAR(128), PRIMARY KEY (name))");
            update("CREATE TABLE IF NOT EXISTS " + name_table + "_date(date VARCHAR(128), PRIMARY KEY (date))");
            update("CREATE TABLE IF NOT EXISTS " + name_table + "_data(id VARCHAR(128), wincount DOUBLE, lostcount DOUBLE, PRIMARY KEY (date))");
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



    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }
}