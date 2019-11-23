package com.example.sweet;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    private static Connection conn = null;

    public static Connection getConnect() {

        if (conn == null) {
            /*new Thread(new Runnable() {//访问MySQL要开一个新线程
                @Override
                public void run() {*/
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/sysc3010?useSSL=false";
                        String user = "root";
                        String password = "sysc3010!";
                        conn = DriverManager.getConnection(url, user, password);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
           // });
       // }
        return conn;
    }
}
