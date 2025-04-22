package Es2;

import java.io.*;
import java.net.*;

public class ReciveObject {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("0.0.0.0", 3575);
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            String beginMessage= (String) in.readObject();
            System.out.println(beginMessage);
            Studente studente = (Studente) in.readObject();
            System.out.println(studente);
            String endMessage= (String) in.readObject();
            System.out.println(endMessage);
            s.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
