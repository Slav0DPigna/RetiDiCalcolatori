package Es4;

import java.io.*;
import java.net.*;

public class SocketListener extends Thread {
    int tcpPort;
    InetAddress ip;
    int remTCPPort;

    public SocketListener(int port) {
        this.tcpPort = port;
        System.out.println("\nSL>Porta TCP locale" + this.tcpPort);
    }//costruttore

    public void run(){
        try{
            ServerSocket sListener = new ServerSocket(tcpPort);
            while(true){
                Socket sock = sListener.accept();
                InetAddress remAddress = sock.getInetAddress();
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String line = in.readLine();
                remTCPPort = Integer.parseInt(line);
                System.out.println("\nSL>Ricevuta risposta da : " + remAddress.getHostAddress()+":"+sock.getPort());
                System.out.println("\nSL>Stringa socket ricevuta: " + remTCPPort);
                sock.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }//run

}//SocketListener
