package com.example.sweet;

import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

public class udpSenderTest {

    udpSender sender = new udpSender();

    @Test
    public void checkValidationHouseID() {



           // assertEquals(true, sender.checkValidationHouseID("jit"));

<<<<<<< HEAD
        assertEquals("20",sender.SendMsg("i need a string value."));
=======
>>>>>>> b03ed3ce2dc3e5a26cf04a98e8cf62ab5b26a35a
    }
}