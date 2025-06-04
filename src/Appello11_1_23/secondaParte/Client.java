package Appello11_1_23.secondaParte;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client extends Thread {

    public void run() {
        int tPort=3000;
        try{
            while(true) {
                Thread.sleep(new Random().nextInt(1000));
                Socket socket=new Socket("localhost",tPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                int sensorId = new Random().nextInt(0, 10);
                out.println("" + sensorId);
                System.out.println("Inviato id sensore: " + sensorId + "\nAspetto dal server le informazioni del sensore");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Misura m = (Misura) in.readObject();
                System.out.println("Ricevuto misura: " + m.toString());
                out.close();
                in.close();
                socket.close();
            }
        }catch (IOException | ClassNotFoundException | InterruptedException e){
            e.printStackTrace();
        }
    }//run

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}//Client
