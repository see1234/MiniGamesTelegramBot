package me.project.helper;

import me.project.Main.Main;
import me.project.database.DatabaseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Helper extends Thread {



    @Override
    public void run(){


    }

    public static boolean playerExists(String date) {
        ResultSet resultSet = Main.getInstance().getDatabaseManager().getResult("SELECT * FROM " + "db_date" + " WHERE date='" + date + "'");
        try {
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
        }
        return false;
    }
    public static void sendPhotoIdAll(String date) {
        ResultSet resultSet = Main.getInstance().getDatabaseManager().getResult("SELECT * FROM " + "db");
        try {
            while (resultSet.next()) {

            }

        } catch (SQLException e) {
        }

    }
    public static void sendFileIdAll(String date) {
        ResultSet resultSet = Main.getInstance().getDatabaseManager().getResult("SELECT * FROM " + "db");

        try {
            while (resultSet.next()) {

            }

        } catch (SQLException e) {
        }

    }
    public static String getURLSource(String url) throws IOException
    {
        URL urlObject = new URL(url);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return toString(urlConnection.getInputStream());
    }
    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            return stringBuilder.toString();
        }
    }
}