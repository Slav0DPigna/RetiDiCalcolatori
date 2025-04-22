package Es2;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        try{
            Socket s = new Socket("127.0.0.1", 8189);//localhost Echo server
            s.setSoTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            System.out.println(in.readLine());
            String output="Ciao";
            System.out.println(output);
            out.println(output);
            System.out.println(in.readLine());
            output="bye";
            System.out.println(output);
            out.println(output);
            System.out.println(in.readLine());
            output="BYE";
            System.out.println(output);
            out.println(output);
            System.out.println(in.readLine());
            s.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
