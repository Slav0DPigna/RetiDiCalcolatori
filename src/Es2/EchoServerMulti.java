package Es2;

import java.io.*;
import java.net.*;

public class EchoServerMulti {
    /*
    * Programma importante questo ci mostra come un programma può gestire più connessioni.
    * */
    public static class ThreadedEchoHandler extends Thread{

        Socket incoming;

        public ThreadedEchoHandler(Socket incoming){
            this.incoming = incoming;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
                PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
                out.println("Hello! Enter BYE to exit");
                boolean done = false;
                while (!done) {
                    String line = in.readLine();
                    if (line == null)
                        done = true;
                    else {
                        out.println("Echo " + line.toUpperCase());
                        if (line.trim().equals("BYE")) {
                            done = true;
                        }
                    }
                }//while
                System.out.println("Socket "+incoming+" closed");
                incoming.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8189);
            System.out.println("Socket "+s.toString()+" opened");
            while (true) {
                Socket incoming = s.accept();
                System.out.println("Socket "+incoming+" accepted");
                ThreadedEchoHandler t = new ThreadedEchoHandler(incoming);
                t.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
