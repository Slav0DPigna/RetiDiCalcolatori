package Es6.PrimoAppello;

import java.net.*;
import java.io.*;
import java.util.*;

public class Partecipanti extends Thread {

    private int id;
    private final String GIUDICE_ADDRESS = "127.0.0.1";

    public Partecipanti(int id) {
        this.id = id;
    }

    public void run(){
        System.out.println("Partecipante: " + id);
        MulticastSocket mSocket = null;
        try{
            //ricevo richiesta offerta
            mSocket = new MulticastSocket(3000);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            mSocket.joinGroup(group);
            byte[] buffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            mSocket.receive(packet);
            String richiesta = new String(packet.getData());
            String[] richiestaParts = richiesta.split(" ");//fatto malissimo questa app é fatta per funzionare con una sola richiesta
            String descrizione = richiestaParts[0];
            int importoMax = Integer.parseInt(richiestaParts[4].trim());//qui é il problema
            System.out.println("Ricevuto pacchetto multicast " + richiesta);
            //rispondo al giudice
            Socket socket = new Socket(GIUDICE_ADDRESS, 4000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Offerta offerta = new Offerta(this.id,new Random().nextInt(10000));
            out.writeObject(offerta);
            out.flush();
            socket.close();
            System.out.println("Inviata offerta " + offerta);
            //attendo esito offerte
            mSocket.receive(packet);
            String offertaVincente=new String(packet.getData(), 0, packet.getLength());
            System.out.println("Ricevuto pacchetto multicast " + offertaVincente);
            String[] parts = offertaVincente.split("-");
            System.out.println("Vincitore offerta con ID: " + parts[0]);
            System.out.println("Importo vincente pari a: " + parts[1]);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }//run

    public static void main(String[] args){
        int n=10;
        for (int i=0;i<n;i++){
            new Partecipanti(i).start();
        }
    }
}//Partecipante
