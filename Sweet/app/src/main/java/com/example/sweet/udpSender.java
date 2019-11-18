package com.example.sweet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class udpSender {
    private final static int PACKETSIZE = 100 ;
    private InetAddress host;
    private int port;
    int counter;

    //check whether the house_id is valid
    public boolean checkValidationHouseID(String s)
    {

        DatagramSocket socket = null ;
        try
        {
            // Convert the arguments first, to ensure that they are valid
            host = InetAddress.getByName( "172.20.10.10") ;
            port         = Integer.parseInt( "8688" ) ;
            counter      = Integer.parseInt( "1" ) ;
            socket = new DatagramSocket() ;


            String message = null;
            while (true)
            {
                for(int x = 1; x <= counter; x++)
                {
                    //command to be send to the pi
                    message = s;
                    byte [] data = message.getBytes() ;
                    DatagramPacket packet = new DatagramPacket( data, data.length, host, port ) ;
                    DatagramPacket rePacket = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;
                    socket.send( packet ) ;
                    while(true)
                    {
                        //System.out.println(1);
                        socket.receive( rePacket ) ;
                        System.out.println( rePacket.getAddress() + "++ " + rePacket.getPort() + ": " +new String(rePacket.getData()).trim());
                        break;
                    }
                }
                break;
            }
            System.out.println ("Closing down");
            return true;
        }
        catch( Exception e )
        {
            System.out.println( e ) ;
        }
        finally
        {
            if( socket != null )
                socket.close() ;
        }
        return false;
    }
}
