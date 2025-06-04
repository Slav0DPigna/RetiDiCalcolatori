package Appello11_1_23.secondaParte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashSet;

public class Server{
    public static void main(String[] args){
        HashSet<Misura> misure = new HashSet<>();
        sensorHandler(misure);
        clientRemponse(misure);
    }//main

    public static void sensorHandler(HashSet<Misura> misure){
        new Thread(()->{
            while(true){
                try{
                    InetAddress address = InetAddress.getByName("230.0.0.1");
                    MulticastSocket ms = new MulticastSocket(4000);
                    ms.joinGroup(address);
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    ms.receive(packet);
                    String message = new String(packet.getData());
                    System.out.println("Ricevuto messaggio: "+message);
                    String[] split = message.split(":");
                    Misura m = new Misura(Integer.parseInt(split[0]));
                    m.setValue(Double.parseDouble(split[1]));
                    m.setDataTime(Long.parseLong(split[2]));
                    synchronized(misure){
                        misure.add(m);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }//sensorHandler


    public static void clientRemponse(HashSet<Misura> misure){
        new Thread(()->{
            while(true) {
                try {
                    ServerSocket s = new ServerSocket(3000);
                    Socket client = s.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    String line = in.readLine();
                    int idSensor = Integer.parseInt(line);
                    synchronized (misure) {
                        boolean found = false;
                        for (Misura misura : misure) {
                            if (misura.equals(new Misura(idSensor))) {
                                System.out.println("Invio misura: "+misura);
                                out.writeObject(misura);
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("Sensore non presente");
                        }
                        client.close();
                        s.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//clientHandler
}//Server
