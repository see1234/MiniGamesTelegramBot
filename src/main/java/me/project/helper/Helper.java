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
        /*
        try {
            while(true) {

try {
                String result = getURLSource("https://pgusa.ru/studentu/raspisanie-zanyatiy/licei"); //��� �����

                int len = result.split("https://pgusa.ru/sites/default/files/raspisanie/licei/").length - 1; //����� � ������� ����� ���������� ������
                System.out.println("UPDATE CLASSES HELPER");//����������� ��� ����� �������
                for (int i = 0; i < len; i++) { //��������� �� ����� � 32 ������, � �������� i++
                    String date = result.split("raspisanie/licei/")[1].split("\"")[0]; //�������� ������ �� raspisanie/licei/ �� \

                    String kb = result.split("raspisanie/licei/" + date)[1].split("\\(")[1].split("\\)")[0].replaceAll("[^0-9.]", "");
                   //������� ��������� �����, ����� ���� ���� ������� ���������� �� ������� �� �����
                    result = result.replace("raspisanie/licei/" + date, ""); //������� ��� ������ �� ���� �����
                    result = result.replace("������� (" + kb, ""); //������� ��� ������ �� ���� �����
                    try { //�������� �� ������
                        if (!playerExists(date + "_" + kb)) { //�������� ���� �� � ���� � ����� ����� � �����������
                            if (date.contains("jpg")) { //��������� ���� ���� jpg
                                sendPhotoIdAll(date);
                            } else if (date.contains("pdf")) { //��������� ���� ���� pdf
                                sendFileIdAll(date);
                            } else { //���� ������ ���� ����
                                sendFileIdAll(date);
                            }
                            Main.getInstance().getDatabaseManager().update("INSERT INTO " + Main.getInstance().getDatabaseManager().getGameTable() + "_date(date) VALUES('" + date + "_" + kb + "')");
                            //���������� ������ � ���� ���� �� ����� �� ������� ������� ���
                        }
                    } catch (Exception ex) {//�������� �� ������
                        ex.printStackTrace();//�������� �� ������
                    }//�������� �� ������


                }
                Thread.sleep(300000); //��������� �� 300 ������ (5 ���)
            } catch (Exception e) {
                e.printStackTrace();
            }
            }


        } catch (Exception e) {
           e.printStackTrace();
        }
*/

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
                Main.getInstance().bot.getBot().sendImageFromUrl("https://pgusa.ru/sites/default/files/raspisanie/licei/" + date, resultSet.getString("name"));
                Main.getInstance().console.print("��������� ���� " + date + "->" + resultSet.getString("name"));
            }

        } catch (SQLException e) {
        }

    }
    public static void sendFileIdAll(String date) {
        ResultSet resultSet = Main.getInstance().getDatabaseManager().getResult("SELECT * FROM " + "db");

        try {
            while (resultSet.next()) {
                Main.getInstance().bot.getBot().sendFile("https://pgusa.ru/sites/default/files/raspisanie/licei/" + date, resultSet.getString("name"));
                Main.getInstance().console.print("��������� ���� " + date + "->" + resultSet.getString("name"));
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