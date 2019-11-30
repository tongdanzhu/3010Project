package com.example.sweet;

import java.net.*;

public class UDPReceiver {

    private final static int PACKETSIZE = 100 ;

    public static void main( String args[] )
    {
        // Check the arguments
        /*if( args.length != 1 )
        {
            System.out.println( "usage: UDPReceiver port" ) ;
            return ;
        }*/
        try
        {
            // Convert the argument to ensure that is it valid
            int port = 8685;
                 //   Integer.parseInt( args[0] ) ;

            // Construct the socket
            DatagramSocket socket = new DatagramSocket( port ) ;

            //System.out.println( "usage: UDPReceiver port" ) ;

            {
                System.out.println( "Receiving on port " + port ) ;
                DatagramPacket packet = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;
                System.out.println( "134343434" ) ;

                socket.receive( packet ) ;
                //System.out.println( "234343434" ) ;
                String message = "ACK: " + new String(packet.getData()).trim();
                //System.out.println( "34343434" ) ;
                byte [] data = message.getBytes();
                //System.out.println( "434343434" ) ;
                DatagramPacket sePacket = new DatagramPacket( data, 20, packet.getAddress(), packet.getPort() ) ;
                //System.out.println( "534343434" ) ;
                socket.send( sePacket) ;
                //System.out.println( "634343434" ) ;
                System.out.println( packet.getAddress() + " " + packet.getPort() + ": " + new String(packet.getData()).trim() ) ;
                //System.out.println( "734343434" ) ;
            }
        }
        catch( Exception e )
        {
            System.out.println( e ) ;
        }
    }
}