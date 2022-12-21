package me.project.ConsoleManager;

import me.project.Main.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Console extends Thread {


    public void print(Object obj) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd [HH:mm:ss]");
        System.out.println(df.format(new Date().getTime()) + ": " + obj);
    }
    @Override
    public void run(){
        Scanner scan = new Scanner(System.in);
        String input = scan.next();
        if(input.contains("q")) {
            Main.getInstance().onDisable();
        }


        scan.close();
    }

}
