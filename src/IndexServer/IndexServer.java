package IndexServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class IndexServer {
    private Map<File, InetAddress> data;

    private final int uPort = 3000;
    private final int tPort = 4000;

    private ServerSocket server;
    private DatagramSocket uSocket;

    public IndexServer() {
        data = Collections.synchronizedMap(new HashMap<File, InetAddress>());/*importantissimo questo passaggio
        altrimenti il codice solleverà eccezioni*/
        System.out.println("IndexServer started");
        inizia();
    }

    public void inizia() {
        try{
            server = new ServerSocket(tPort);
            System.out.println(server);
            uSocket = new DatagramSocket(uPort);
        }catch(IOException e){
            e.printStackTrace();
        }
        new GestisciFile().start();//thread per la gestione delle richiesta di StorageServer
        new GestisciClient().start();//thread per la gestione delle richieste dai Client
    }

    class GestisciFile extends Thread {
        public void run() {
            while(true){
                try{
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    System.out.println("Attesa di un nuovo pacchetto");
                    uSocket.receive(packet);
                    String msg = new String(packet.getData());
                    System.out.println(msg);
                    String[] parti = msg.split("#");
                    String[] keywords = parti[1].split(",");
                    File file = new File(parti[0],keywords,"");
                    System.out.format("Aggiungo il file %s inviato da %s%n", parti[0], packet.getAddress());
                    data.put(file,packet.getAddress());//salvo il file nella struttura dati
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }//GestisciFile

    class GestisciClient extends Thread {
        public void run() {
            while(true){
                try{
                    System.out.println("Attesa di una nuova ricerca...");
                    Socket tSocket = server.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(tSocket.getInputStream()));
                    String msg = br.readLine();
                    System.out.println("Ricevuta ricerca : "+msg);
                    String[] parti = msg.split("#");
                    String[] keywords = parti[1].split(",");
                    System.out.format("Ricevuta ricerca con successo la ricerca con filename %s e chiavi %s%n", parti[0], parti[1]);
                    String ret = "";
                    synchronized (data) {
                        for (Map.Entry<File, InetAddress> entry : data.entrySet()) {
                            File file = entry.getKey();
                            if (file.getFileName().equals(parti[0])) {
                                boolean trovato = true;
                                for (int i = 0; i < keywords.length && trovato; i++) {
                                    trovato = false;
                                    for(int j=0;j<file.getKeywords().length;j++){
                                        if(keywords[i].equals(file.getKeywords()[j])){
                                            trovato = true;
                                            break;
                                        }//if
                                    }//for j
                                }//for i
                                if(trovato){
                                    ret += entry.getValue().toString();
                                }
                            }//if
                        }//for each
                    }//syncronized
                    //rispondi al client
                    System.out.println("Invio della risposta : "+ret);
                    PrintWriter pw = new PrintWriter(tSocket.getOutputStream(),true);
                    pw.println(ret);
                    br.close();pw.close();tSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }//Gestisci clienti

    public static void main(String[] args) {
        IndexServer indexServer = new IndexServer();
    }
}//IndexServer
