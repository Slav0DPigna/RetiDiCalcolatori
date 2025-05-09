package Es6.PrimoAppello;

import java.io.*;
import java.net.*;

public class Ente {
    public static void main(String[] args) {
        String GIUDICE_ADDRESS = "127.0.0.1";
        int GIUDICE_PORT = 2000;
        try{
            //invio richesta gara d'appalto
            Socket socket = new Socket(GIUDICE_ADDRESS, GIUDICE_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Richiesta("Costruzione stadio Arcavacata", 100000));
            //mi metto in attesa dell'offerta migliore
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Offerta offertaMiugliore = (Offerta) in.readObject();
            System.out.println("Ricevuta offerta migliore "+ offertaMiugliore);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
