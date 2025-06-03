package Lotteria;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        final int tPort=3000;
        final int uPort=4000;
        ArrayList<Biglietto> bigliettiTot = new ArrayList<Biglietto>();
        for (int i = 0; i < 5; i++) {
            int numBigliettiDaComprare = 12;
            System.out.println("Biglietti da comprare: " + numBigliettiDaComprare);
            Biglietto[] biglietti = null;
            try {
                Socket s = new Socket("localhost", tPort);
                PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
                pw.println("Napoli," + numBigliettiDaComprare);
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                biglietti = (Biglietto[]) ois.readObject();
                for (int j = 0; j < numBigliettiDaComprare; j++) {
                    System.out.println(biglietti[j]);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            for (Biglietto biglietto : biglietti) {
                bigliettiTot.add(biglietto);
            }
        }
        System.out.println("Finito di comprare");
        try {
            MulticastSocket uSocket = new MulticastSocket(uPort);
            uSocket.joinGroup(InetAddress.getByName("230.0.0.1"));
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            uSocket.receive(packet);
            String message = new String(packet.getData(),0,packet.getLength());
            System.out.println(message);
            for(Biglietto biglietto : bigliettiTot)
                if(biglietto.toString().equals(message))
                    System.out.println("Hai vinto col bigleitto "+biglietto);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }//main
}//Client
