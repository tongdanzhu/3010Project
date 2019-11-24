package com.example.sweet;

import java.net.*;

public class UDPReceiver {

    private final static int PACKETSIZE = 1000 ;

    public static void main( String args[] )
    {
        //System.out.println( "Receiving on port " ) ;
        try
        {
            int port = 8688;

            // Construct the socket
            DatagramSocket socket = new DatagramSocket( port ) ;

            for(;;)
            {
                System.out.println( "Receiving on port " + port ) ;
                DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;
                socket.receive( packet ) ;

               // String message = "ACK: " + new String(packet.getData()).trim();
                String message = "20";

                byte [] data = message.getBytes();

                DatagramPacket sePacket = new DatagramPacket( data, data.length, packet.getAddress(), packet.getPort() ) ;

                socket.send( sePacket) ;

                System.out.println( packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()).trim() ) ;

            }
        }
        catch( Exception e )
        {
            System.out.println( e ) ;
        }
    }
}