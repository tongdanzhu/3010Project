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
    private static final String GET_THRESHOLD="SELECT threshold FROM temperatureControl WHERE houseID=?";

    //get latest temperature
    private static final String GET_LATEST_TEMP ="SELECT * FROM temperature WHERE houseID=? and currDate=curDate() ORDER BY currTime desc";
    //get visitor information
    private static final String GET_visitorList = "SELECT * FROM visitor WHERE houseID=? confirm=?";
    //confirm the visitor list
    private static final String Confirm_Visitors = "UPDATE visitor SET confirm =? WHERE houseID =?";
    //confirm the mailbox
    private static final String Confirm_Mailbox = "UPDATE user SET mailboxState = ? WHERE houseID = ?";

    private static Connection conn = null;

    // establish connection to database in raspberry bi
    public static Connection getConnection() {

        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                //String url = "jdbc:mysql://172.20.10.10:3306/sysc?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                 //String url = "jdbc:mysql://10.0.2.2:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                String url = "jdbc:mysql://192.168.19:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                //String url = "jdbc:mysql://localhost:3306/sysc3010?useSSL=false&useUnicode = true&characterEncoding = utf-8&serverTimezone = GMT";
                String user = "root";
                String password = "sysc3010!";
                //String password = "password";
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

    public double getThreshold(int houseID) throws SQLException {
        Connection conn = getConnection();
        temperatureControlVO temperature=new temperatureControlVO();
        PreparedStatement pstmt = conn.prepareStatement(GET_THRESHOLD);
        pstmt.setInt(1, houseID);
        ResultSet rs =  pstmt.executeQuery();
        if (rs.next()) {

            temperature.setThreshold(rs.getDouble("threshold"));
        }
        return temperature.getThreshold();
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
       update mailbox state
    */
    public int confirmMailbox(int houseID) throws SQLException{
        int updateCount = 0;
        PreparedStatement pstmt = conn.prepareStatement(Confirm_Mailbox);
        pstmt.setBoolean(1,true);
        pstmt.setInt(2,houseID);
        updateCount = pstmt.executeUpdate();
        return updateCount;
    }


    /*
        get visitors list
     */
    public List<visitorVO> getVisitorList(int houseID) throws SQLException{
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(GET_visitorList);
        pstmt.setInt(1, houseID);
        pstmt.setBoolean(2,false);
        houseVO house = new houseVO();
        List<visitorVO> visitors = new ArrayList<visitorVO>();
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            visitorVO visitor = new visitorVO();
            visitor.setVisitorID(rs.getInt("visitorID"));
            visitor.setHouseID(rs.getInt("houseID"));
            visitor.setCurrDate(rs.getString("currDate"));
            visitor.setCurrTime(rs.getString("currTime"));
            visitor.setConfirm(rs.getBoolean("confirm"));

            visitors.add(visitor);
        }
        return visitors;
    }


    /*
        confirm visitor
     */
    public int confirmVisitor(int houseID) throws SQLException{
        int updateCount = 0;
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(Confirm_Visitors);
        pstmt.setBoolean(1,true);
        pstmt.setInt(2,houseID);
        updateCount = pstmt.getUpdateCount();
        return updateCount;

    }


}
