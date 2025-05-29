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
        for (int i = 0; i < 11; i++) {
            int numBigliettiDaComprare = 1000;
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
        System.out.println(bigliettiTot);
    }//main
}//Client
