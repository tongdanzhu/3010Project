package com.example.sweet;

import org.junit.Test;

import static org.junit.Assert.*;

public class temperatureTest {

    temperature t=new temperature();
    @Test
    public void onCreate() {
    }

    @Test
    public void isThresholdValid() {
        assertEquals(false,t.isThresholdValid("40.1"));
        assertEquals(true,t.isThresholdValid("39.9"));
        assertEquals(false,t.isThresholdValid("-2.0"));
        assertEquals(true,t.isThresholdValid("0.3"));
    }
}