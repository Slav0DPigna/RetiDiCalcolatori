package Es2;

import java.io.*;
import java.net.*;

public class EchoServer {
    /*
    * Possiamo interagire con quest'app tramite telnet oppure scrivendo un'altra classe credo che faró cosí.
    * Scriverò anche un'altra app che può gestire più connessioni contemporaneamente.
    * */
    public static void main(String[] args) {
        try{
            ServerSocket s = new ServerSocket(8189);
            System.out.println("Socket "+s.toString()+" opened");
            Socket incoming = s.accept();
            System.out.println("Socket "+incoming+" accepted");
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
            out.println("Hello! Enter BYE to exit");
            boolean done = false;
            while (!done) {
                String line = in.readLine();
                if (line == null)
                    done = true;
                else{
                    out.println("Echo "+line.toUpperCase());
                    if (line.trim().equals("BYE")){
                        done = true;
                    }
                }
            }//while
            incoming.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
