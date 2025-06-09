package Appello11_7_23.secondoEsercizio;

import Es9.esercizio2.Data;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread{

    HashMap<String, ArrayList<Richiesta>> richiesta;

    public Server(){
        this.richiesta = new HashMap<String,ArrayList<Richiesta>>();
    }

    public void negHandler(){
        new Thread(()->{
            while(true){
                try{
                    ServerSocket s = new ServerSocket(3000);
                    Socket client = s.accept();
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                    Richiesta ric = (Richiesta) in.readObject();
                    synchronized(richiesta) {
                        for (Richiesta r : richiesta.get(ric.sigla)) {
                            if (r.equals(ric) && ric.prezzo != 0 && ric.prezzo < r.prezzo) {
                                richiesta.get(ric.sigla).remove(r);//caso in cui é l'offerta vicente
                                richiesta.get(ric.sigla).add(ric);
                                MulticastSocket ms = new MulticastSocket(5000);
                                InetAddress group = InetAddress.getByName("230.0.0.1");
                                String toSend = ric.codicePordotto + ":" + ric.prezzo + ":" + ric.pIva + ":" + ric.sigla;
                                DatagramPacket d = new DatagramPacket(toSend.getBytes(), toSend.length(), group, 5000);
                                ms.send(d);
                                System.out.println("inviato riscontro positivo per l'offerta: " + toSend);
                                break;
                            }
                            if (ric.prezzo == 0 && r.equals(ric)) {
                                System.out.println("Cancellata offerta: " + richiesta.get(ric.sigla).remove(r));
                                break;//caso di cancellazione
                            }
                        }
                    }
                }catch(IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }//negHandler

    public void clientHandler(){
        new Thread(()->{
            try{
                ServerSocket s = new ServerSocket(4000);
                Socket client = s.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String recive = in.readLine();
                System.out.println("Ricevuta richiesta: "+recive);
                String[] parts = recive.split(":");
                String codice = parts[0];
                String sigla  = parts[1];
                boolean bool = Boolean.getBoolean(parts[2]);
                if(bool){
                    notifyBest(codice,sigla);
                }
                Richiesta toReturn = null;
                synchronized (richiesta) {
                    for (Richiesta r : richiesta.get(sigla)) {
                        if (toReturn == null) {
                            toReturn = r;
                        }
                        else if (r.codicePordotto.equals(codice) && toReturn.prezzo>r.prezzo) {
                            toReturn=r;
                        }
                    }
                }
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(toReturn);
                out.flush();
                System.out.println("Inviata offerta migliore"+toReturn);
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }//clientHandler

    public void notifyBest(String codice, String sigla){
        new Thread(()->{
            Richiesta best = null;
            synchronized (richiesta) {
                for (Richiesta r : richiesta.get(sigla)) {
                    if (best==null)
                        best=r;
                    else if(r.codicePordotto.equals(codice) && best.prezzo>r.prezzo)
                        best=r;
                }
            }
            while(true){
                try {
                    Thread.sleep(10000);
                    boolean nuovo = false;
                    synchronized (richiesta) {
                        for (Richiesta r : richiesta.get(sigla)) {
                            if(r.codicePordotto.equals(codice) && best.prezzo>r.prezzo){
                                nuovo=true;
                                best=r;
                            }
                        }
                    }
                    if(nuovo){
                        MulticastSocket ms = new MulticastSocket(6000);
                        InetAddress group = InetAddress.getByName("230.0.0.1");
                        String toSend = codice + ":" + sigla + ":" + best.prezzo;
                        DatagramPacket dp =new DatagramPacket(toSend.getBytes(), toSend.length(), group, 6000);
                        ms.send(dp);
                        System.out.println("Inviato il prezzo migliore");
                    }
                }catch (InterruptedException | IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }//notifyBest

    public void run(){
        negHandler();
        clientHandler();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }//main
}//Server
