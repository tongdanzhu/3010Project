package com.example.sweet;

import junit.framework.TestSuite;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class houseTest {
   house h=new house();
   @org.junit.Test
    public void houseIDtest() throws Exception{
       assertEquals(true,h.house_id_validation("4"));

   }

}
