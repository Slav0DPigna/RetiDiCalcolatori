package Es3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLTest2 {
    public static void main(String[] args) {
        try{
            URL url = new URL("https://www.w3.org");
            URLConnection connection = url.openConnection();
            connection.connect();
            //print headers fields
            int n=1;
            String key;
            while ((key = connection.getHeaderField(n)) != null) {
                String value = connection.getHeaderField(n);
                System.out.println(key + ": " + value);
                n++;
            }
            System.out.println("----------");
            System.out.println("getContentType: "+connection.getContentType());
            System.out.println("getContentLength: "+connection.getContentLength());
            System.out.println("getContentEncoding: "+connection.getContentEncoding());
            System.out.println("getDate: "+connection.getDate());
            System.out.println("getExpiration: "+connection.getExpiration());
            System.out.println("getLastModified: "+connection.getLastModified());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;n=1;
            while ((line = in.readLine()) != null && n<=10) {
                System.out.println(line);
                n++;
            }
            if (line != null)
                System.out.println("...");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
/*
Questo appena scritto è un altro modo per interagire con gli oggetti URL
in questo caso noi ci connettiamo al sito e poi stampiamo l'header.
in oltre dopo il for ci sono un sacco di metodi con i quali noi possiamo interagire con URLConnection.
 */
