package com.example.sweet;

import junit.framework.TestSuite;


import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class MyConnectionTest {


    MyConnection con = new MyConnection();



    @Test
    public void Test_userExist() {
        //expect true when the input is a integer
        try {
            assertEquals(true, con.userExist(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //expect false when the input is not a integer
        try {
            assertEquals(false, con.userExist(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_password_validation() {
        //test case for correct password
        try {
            assertEquals(true, con.password_validation("1", "123"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //text case for incorrect password
            assertEquals(true, con.password_validation("2", "123"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //text case for incorrect password
            assertEquals(false, con.password_validation("2", "13433"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //text case for incorrect password
            assertEquals(false, con.password_validation("135", "2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getMailboxState() {
        try {
            assertEquals(false,con.getMailboxState(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(false,con.getMailboxState(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_threshold_exist(){
        try {
            assertEquals(true,con.isThresholdExist(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(false,con.isThresholdExist(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_get_threshold(){
        try {

            assertEquals(28.5,con.getThreshold(1),0.2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
