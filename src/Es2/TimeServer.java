package Es2;

import java.io.*;
import java.net.*;
import java.util.*;

/*
* Questa classe è diversa rispetto a quella della prima esercitazione perchè
* questa è basata sui datagram socket che hanno un diverso modo di funzionare rispetto ai socker precedenti.
* Questo programma usa il protocollo UDP rispetto a quello usato prima che era il TCP, questo nuovo protocollo
* non garantisce la consegna del pacchetto però abbiamo una velocità maggiore.
*/

public class TimeServer {
    public static void main(String[] args) {
        DatagramSocket socket =null;
        try {
            socket=new DatagramSocket(3575);
            int n = 1;
            while (n <= 10) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String dString = new Date().toString();
                buf = dString.getBytes();
                InetAddress ip = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, ip, port);
                socket.send(packet);
                n++;
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
            socket.close();
        }
    }
}
