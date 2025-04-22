package Es4;

import java.net.*;
import java.io.*;

public class MulticastListener extends Thread {
    int mcastPort;
    InetAddress remAddress;
    int tcpPort;

    public MulticastListener(int port1, int port2){
        this.mcastPort = port1;
        this.tcpPort = port2;
        System.out.println("ML>porta multicast locale: "+this.mcastPort);
        System.out.println("ML>Porta TCP locale: "+this.tcpPort);
    }

    public void run(){
        try{
            MulticastSocket mSocket = new MulticastSocket(this.mcastPort);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            mSocket.joinGroup(group);
            while(true){
                byte[] buf = new byte[50];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                mSocket.receive(packet);
                remAddress = packet.getAddress();
                String received = new String(packet.getData());
                int i=0;
                while(Character.isDigit(received.charAt(i)))
                    i++;
                int remTCPPort = Integer.parseInt(received.substring(0,i));//indirizzo preso per i primi i char
                System.out.println("\nML>Ricevuto mcast datagram da "+remAddress.getHostAddress()+":"+packet.getPort());
                System.out.println("\nML>Contenuto del datagramma: "+remTCPPort);
                if (!(remAddress.equals(InetAddress.getLocalHost())) || (remTCPPort != this.tcpPort)){//per evitare di mandare un messaggio a me stesso
                    System.out.println("\nML>Invio risposta a "+remAddress.getHostAddress()+":"+remTCPPort);
                    Socket tcpSocket = new Socket(remAddress.getHostAddress(), remTCPPort);
                    PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
                    out.println(tcpPort);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }//run
}//MulticastLinstener
