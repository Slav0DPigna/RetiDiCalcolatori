package Es2;

import java.io.*;
import java.net.*;

public class SocketOpener extends Thread {
    private String host;
    private int port;
    private Socket socket;

    public SocketOpener(String host, int port) {
        this.host = host;
        this.port = port;
        this.socket=null;
    }

    public static Socket openSocket(String host, int port,int timeout){
        SocketOpener opener = new SocketOpener(host,port);
        opener.start();
        try{
            opener.join(timeout);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return opener.getSocket();
    }

    private Socket getSocket() {
        return socket;
    }

    public void run() {
        try {
            this.socket = new Socket(host, port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "www.dimes.unical.it";
        int port = 1234;
        int timeout = 20000;
        Socket socket = SocketOpener.openSocket(host, port,timeout);
        if(socket == null) {
            System.out.println("Socket coult not be opened.");
            System.exit(1);
        }else
            System.out.println(socket);
    }
}

