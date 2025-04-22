package Es1;

import java.io.*;
import java.net.*;

public class ClientTest {
    public static void main(String[] args) {
        String URL = "unical.it";
        int PORT = 80;
        try {
            Socket socket = new Socket(URL, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("GET /lt-lmcu HTTP/1.1");
            out.println("Host: "+URL);
            out.println();//questo sarebbe il doppio invio per inviare la richiesta HTTP
            boolean more = true;
            while (more) {
                String line = in.readLine();
                if (line == null)
                    more = false;
                else
                    System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
