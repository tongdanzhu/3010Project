package com.example.sweet;

import org.junit.Test;

import static org.junit.Assert.*;

public class udpSenderTest {

    udpSender sender= new udpSender();
    @Test
    public void send() {

        assertEquals(true,sender.checkValidationHouseID("jit"));
    }
}