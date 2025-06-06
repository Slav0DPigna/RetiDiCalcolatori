package Appello8_2_2023.secondaParte;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Server extends Thread {

    private final int tPort=3000;
    private final int uPort=4000;
    private final int uPort2=5000;
    private HashMap<Concorso,ArrayList<Integer>> concorsi;
    private static int idProtocollo=1;

    public Server(Concorso[] concorsi) {
        this.concorsi=new HashMap<>();
        for(int i=0;i<concorsi.length;i++) {
            this.concorsi.put(concorsi[i],new ArrayList<>());
        }
    }

    public void clientHandler(){
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(tPort);
                Socket c = serverSocket.accept();
                System.out.println("Il cliente: "+c+" si é collegato");
                ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
                Partecipazione p = (Partecipazione) ois.readObject();
                boolean concorsoPresente=false;
                String r="";
                synchronized (this.concorsi) {
                    Concorso tmp=null;
                    for(Concorso cc : this.concorsi.keySet()) {
                        if(p.getIdConcorso()==cc.getId()) {
                            concorsoPresente=true;
                            tmp=cc;
                            break;
                        }
                    }
                    if(!concorsoPresente || p.getNome().equals("") || p.getCognome().equals("")
                            || p.getCodiceFiscale().equals("")|| p.getData().after(tmp.getScadenza())
                        || tmp.getNumPosti()<=0) {
                        r="NOT_ACCEPTED";
                        System.out.println("Richiesta non valida");
                    }else{
                        System.out.println("Richiesta valida assegno id: "+idProtocollo);
                        r=+idProtocollo+":"+(new Date());
                        this.concorsi.get(tmp).add(idProtocollo);
                        idProtocollo++;
                        tmp.decrPosti();
                    }
                }
                PrintWriter out = new PrintWriter(c.getOutputStream(),true);
                out.println(r);
                out.close();
                c.close();
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }).start();
    }//clientHandler

    public void cancHandler(){
        new Thread(()->{
            try {
                while(true) {
                    MulticastSocket ms = new MulticastSocket(uPort);
                    InetAddress group = InetAddress.getByName("230.0.0.1");
                    ms.joinGroup(group);
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    ms.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    String[] parts = msg.split(":");
                    int id = Integer.parseInt(parts[0]);
                    boolean presente = false;
                    Concorso tmp = null;
                    synchronized (this.concorsi) {
                        if(this.concorsi.isEmpty())
                            break;
                        for (Concorso cc : this.concorsi.keySet()) {
                            if (this.concorsi.get(cc).contains(id)) {
                                presente = true;
                                tmp = cc;
                            }
                        }
                        ArrayList<Integer> ids = this.concorsi.get(tmp);
                        for (int i = 0; i < ids.size(); i++)
                            if (ids.get(i) == id) {
                                ids.remove(i);
                                break;
                            }
                    }
                    if (presente) {
                        System.out.println("Rimosso id: " + id);
                    } else
                        System.out.println("Rimosso nessun id");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }//cancHandler

    public void scadHandler(){
        new Thread(()->{
            while (true) {
                boolean cs = false;
                Concorso tmp = null;
                synchronized (concorsi) {
                    if(this.concorsi.isEmpty())
                        break;
                    for (Concorso cc : concorsi.keySet()) {
                        if (cc.getScadenza().before(new Date())) {
                            cs = true;
                            tmp = cc;
                        }
                    }
                }
                if (cs) {
                    try {
                        MulticastSocket ms = new MulticastSocket(uPort2);
                        InetAddress address = InetAddress.getByName("230.0.0.1");
                        byte[] buffer = null;
                        synchronized (this.concorsi) {
                            int toRemove = new Random().nextInt(concorsi.get(tmp).size());
                            buffer = concorsi.get(tmp).get(toRemove).toString().getBytes();
                            concorsi.get(tmp).remove(toRemove);
                            concorsi.remove(tmp);
                        }
                        System.out.println("Rimosso id: " + tmp.getId() + "Ha vinto il concorso");
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, uPort2);
                        ms.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }//scadHandler

    public void run(){
        clientHandler();
        cancHandler();
        scadHandler();
    }//run

    public static void main(String[] args) {
        Concorso c = new Concorso(1,10,new Date());
        Server s = new Server(new Concorso[]{c});
        s.start();
    }

}//Server
