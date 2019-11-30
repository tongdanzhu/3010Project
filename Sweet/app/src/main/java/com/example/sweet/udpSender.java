package com.example.sweet;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class udpSender {
    private final static int PACKETSIZE = 1000 ;
    private InetAddress host;
    private String ip = "192.168.0.19";
    private int port = 8688;
    int counter = 1;

    public static void main(String args[]){
        String answer;
        udpSender sender = new udpSender();
        answer = sender.SendMsg("i need a string value");
        System.out.println( answer);

    }


    //Send a msg to get a string or int value
    //to convert string to int: Integer.parseInt(str);
    public String SendMsg(String s)
    {
        String text = null;
        DatagramSocket socket = null ;
        try
        {
            // Convert the arguments first, to ensure that they are valid
            host = InetAddress.getByName( ip) ;
            counter = 1;
            socket = new DatagramSocket() ;
            String message = s;

            while (true)
            {
                for(int x = 1; x <= counter; x++)
                {
                    //command to be send to the pi
                    //message = s;
                    byte [] data = message.getBytes() ;
                    DatagramPacket packet = new DatagramPacket( data, data.length, host, port ) ;
                    DatagramPacket rePacket = new DatagramPacket( new byte[PACKETSIZE], PACKETSIZE ) ;
                    socket.setSoTimeout(1000);
                    socket.send( packet ) ;

                    while(true)
                    {
                        socket.receive( rePacket ) ;
                        text = new String(rePacket.getData(),0,rePacket.getLength());
                        System.out.println( rePacket.getAddress() + " ++ " + rePacket.getPort() + ": " + text );
                        break;
                    }
                }
                break;
            }
            System.out.println ("Closing down");

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
        return text;
    }

}
