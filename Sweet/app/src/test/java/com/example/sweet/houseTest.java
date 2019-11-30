package com.example.sweet;

import org.junit.Test;

import static org.junit.Assert.*;

public class houseTest {

    house h=new house();
    @Test
    public void Test_houseIDisDigital(){
        assertEquals(true,h.houseIDisDigital("3"));
        assertEquals(true,h.houseIDisDigital("346"));
        assertEquals(false,h.houseIDisDigital("giehg"));

    }
}