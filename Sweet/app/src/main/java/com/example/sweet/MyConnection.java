package com.example.sweet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyConnection {
    // check whether the combination of house id and password are correct
    private static final String USER_VALIDATION = "SELECT * FROM user WHERE houseID=? and password=?";
    // check whether the house id exists in database
    private static final String USER_EXIST = "SELECT * FROM user WHERE houseID=?";
    // get current mailbox state
    private static final String GET_MAILBOX_STATE = "SELECT mailboxState FROM house WHERE houseID=?";


    private static Connection conn = null;

    public static Connection getConnection() {

        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://192.168.43.112:3306/sysc?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                // String url = "jdbc:mysql://10.0.2.2:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                //String url = "jdbc:mysql://localhost:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                String user = "root";
                //String password = "sysc3010!";
                String password = "password";
                conn = DriverManager.getConnection(url, user, password);
                System.out.println(url);
                System.out.println(user);
                System.out.println(password);
                System.out.println("Connection made!");
                System.out.println("Schema Name:"+conn.isValid(20)+"\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return conn;
    }

    public boolean userExist(int houseID) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(USER_EXIST);
        pstmt.setInt(1, houseID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return true;
        }

        return false;
    }

    public boolean password_validation(String houseID, String password) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(USER_VALIDATION);
        pstmt.setInt(1, Integer.parseInt(houseID));
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getInt("houseID"));
            System.out.println(rs.getString("password"));
            rs.close();
            pstmt.close();
            return true;
        }
        return false;
    }
    // get current mailbox state
    public boolean getMailboxState(int houseID) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(GET_MAILBOX_STATE);
        pstmt.setInt(1, houseID);
        ResultSet rs = pstmt.executeQuery();
        houseVO house = new houseVO();
        while (rs.next()) {
            house.setMailboxState(rs.getBoolean("mailboxState"));
        }
        if (house.isMailboxState()) {
            return true;
        } else {
            return false;
        }

    }



}
