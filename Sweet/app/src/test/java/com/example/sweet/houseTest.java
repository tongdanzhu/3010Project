package com.example.sweet;

import junit.framework.TestSuite;


import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class houseTest {
    house h = new house();

    @Test
    public void Test_houseID_digital(){
        assertEquals(true,h.houseIDisDigital("3"));
        assertEquals(true,h.houseIDisDigital("346"));
        assertEquals(false,h.houseIDisDigital("giehg"));

    }

    @Test
    public void Test_userExist() {
        //expect true when the input is a integer
        try {
            assertEquals(true, h.userExist(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //expect false when the input is not a integer
        try {
            assertEquals(false, h.userExist(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_password_validation() {
        //test case for correct password
        try {
            assertEquals(true, h.password_validation("1", "1"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //text case for incorrect password
            assertEquals(false, h.password_validation("1", "2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
