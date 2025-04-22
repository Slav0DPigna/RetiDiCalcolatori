package Es2;

import java.io.*;
import java.net.*;

public class SendObject {
    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket( 3575);
            Socket client = server.accept();
            System.out.println(client);
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject("<HELLO>");
            out.writeObject(new Studente(201286,"Salvatore","Pignataro","INGINFO"));
            out.writeObject("</HELLO>");
            System.out.println("Oggetto inviato");
            client.close();
            server.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
