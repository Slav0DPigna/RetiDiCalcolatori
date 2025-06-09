package Appello11_7_23.secondoEsercizio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args){
        try {
            Socket s = new Socket("localhost",4000);
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            out.println("IT:123123:false");
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Richiesta r = (Richiesta) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
