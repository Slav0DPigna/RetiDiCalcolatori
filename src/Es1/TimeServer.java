package Es1;

import java.net.*;
import java.io.*;

public class TimeServer {
        public static void main(String[] args) {
            try {
                Socket socket = new Socket("time.inrim.it", 13);
                socket.setSoTimeout(5000);//se i dati non arrivano in questo tempo la connessione si chiude
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                boolean more = true;
                while (more) {
                    String line = in.readLine();
                    if (line != null)
                        System.out.println(line);
                    else
                        more = false;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
}
