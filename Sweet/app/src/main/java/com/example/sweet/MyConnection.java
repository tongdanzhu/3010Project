package com.example.sweet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyConnection {
    /*
        mysql statements
     */
    // check whether the combination of house id and password are correct
    private static final String USER_VALIDATION = "SELECT * FROM user WHERE houseID=? and password=?";
    // check whether the house id exists in database
    private static final String USER_EXIST = "SELECT * FROM user WHERE houseID=?";
    // get current mailbox state
    private static final String GET_MAILBOX_STATE = "SELECT mailboxState FROM user WHERE houseID=?";
    // check whether the threshold exist
    private static final String THRESHOLD_EXIST = "SELECT threshold FROM temperatureControl WHERE houseID=?";
    // update threshold
    private static final String UPDATE_THRESHOLD = "UPDATE temperatureControl SET threshold =? WHERE houseID =?";
    // get threshold
    private static final String GET_THRESHOLD = "SELECT threshold FROM temperatureControl WHERE houseID=?";
    //get latest temperature
    private static final String GET_LATEST_TEMP = "SELECT * FROM temperature WHERE houseID=? and currDate=curDate() ORDER BY currTime desc";
    //insert threshold for house
    private static final String INSERT_THRESHOLD = "INSERT INTO temperatureControl (houseID, threshold, fanState) VALUES (?,?,0)";
    // update mailbox confirm
    private static final String UPDATE_MAILBOX_CONFIRM = "UPDATE user SET mailboxState=? WHERE houseID=?;";
    // update manual control
    private static final String UPDATE_MANUAL_CONTROL = "UPDATE light SET manualControl=? WHERE houseID=?";

    // get manual control
    private static final String GET_MANUAL_CONTROL = "UPDATE light SET manualControl=? WHERE houseID=?";

    private static Connection conn = null;
    private double defaultValue = -0.5;

    // establish connection to database in raspberry bi
    public static Connection getConnection() {

        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                //url for app connects to remote database
                //String url = "jdbc:mysql://172.20.10.10:3306/sysc?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";

                //url for app connects to local database
                String url = "jdbc:mysql://10.0.2.2:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";

                //url for test
                //String url = "jdbc:mysql://localhost:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";

                String user = "root";
                String password = "sysc3010!";//password for local database
                //String password = "password"; //password for remote database
                conn = DriverManager.getConnection(url, user, password);
                System.out.println(url);
                System.out.println(user);
                System.out.println(password);
                System.out.println("Connection made!");
                System.out.println("Schema Name:" + conn.isValid(20) + "\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return conn;
    }

    /*
        update manual control
     */
    public int updateManualControl(int houseID, boolean manualControl) throws SQLException {
        int updateCount = 0;
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_MANUAL_CONTROL);
        pstmt.setBoolean(1, manualControl);
        pstmt.setInt(2, houseID);
        updateCount = pstmt.executeUpdate();
        return updateCount;
    }

    /*
        update mailbox state, confirm
     */
    public int updateMailboxConfirm(int houseID) throws SQLException {
        int updateCount = 0;
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_MAILBOX_CONFIRM);
        pstmt.setBoolean(1, false);
        pstmt.setInt(2, houseID);
        updateCount = pstmt.executeUpdate();
        return updateCount;
    }

    /*
        get current mailbox state
     */
    public boolean getMailboxState(int houseID) throws SQLException {
        Connection conn = getConnection();
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

    /*
        insert new Threshold
     */
    public int insertThreshold(int houseID, double threshold) throws SQLException {
        Connection conn = getConnection();
        int updateCount = 0;
        PreparedStatement pstmt = conn.prepareStatement(INSERT_THRESHOLD);
        pstmt.setInt(1, houseID);
        pstmt.setDouble(2, threshold);
        updateCount = pstmt.executeUpdate();
        return updateCount;
    }

    /*
        update threshold into database
     */
    public int updateThreshold(int houseID, double threshold) throws SQLException {
        Connection conn = getConnection();
        int updateCount = 0;
        PreparedStatement pstmt = conn.prepareStatement(UPDATE_THRESHOLD);
        pstmt.setDouble(1, threshold);
        pstmt.setInt(2, houseID);
        updateCount = pstmt.executeUpdate();
        return updateCount;
    }

    /*
        get the latest temperature (currTemp) for a certain house from database
     */
    public double getLatestTemp(int houseID) throws SQLException {
        Connection conn = getConnection();
        temperatureVO temperature = new temperatureVO();
        PreparedStatement pstmt = conn.prepareStatement(GET_LATEST_TEMP);
        pstmt.setInt(1, houseID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            temperature.setCurrTemp(rs.getDouble("currTemp"));

            return temperature.getCurrTemp();
        } else {
            return defaultValue;
        }
    }


    /*
        get the threshold of the certain house id from the database
     */
    public double getThreshold(int houseID) throws SQLException {
        Connection conn = getConnection();
        temperatureControlVO temperature = new temperatureControlVO();
        PreparedStatement pstmt = conn.prepareStatement(GET_THRESHOLD);
        pstmt.setInt(1, houseID);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {

            temperature.setThreshold(rs.getDouble("threshold"));
            return temperature.getThreshold();
        } else {
            return defaultValue;
        }

    }

    /*
        search the database whether the threshold exists or not for a certain houseID
     */
    public boolean isThresholdExist(int houseID) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(THRESHOLD_EXIST);
        pstmt.setInt(1, houseID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    /*
        search the database for whether the houseID is exist or not
     */
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

    /*
        search the database whether the password and houseID are correct or not
     */
    public boolean password_validation(String houseID, String password) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(USER_VALIDATION);
        pstmt.setInt(1, Integer.parseInt(houseID));
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {

            rs.close();
            pstmt.close();
            return true;
        }
        return false;
    }


}
