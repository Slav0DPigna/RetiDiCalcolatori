package Appello8_2_2023.secondaParte;

import java.util.*;
import java.io.*;
import java.net.*;

public class Server extends Thread{

    private final int tPort=3000;
    private final int uPort=4000;
    private final int uPort2=5000;

    private HashMap<Concorso,ArrayList<Integer>> concorsi;

    public Server(Concorso[] concorsi){
        this.concorsi=new HashMap<>();
        for(int i=0;i<concorsi.length;i++){
            this.concorsi.put(concorsi[i],new ArrayList<>());
        }
    }

    public void clientAccept(){
        while(true) {
            try {
                ServerSocket socket = new ServerSocket(tPort);
                Socket s = socket.accept();
                clientHandler(s);
                System.out.println("Accettato client: " + s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }//clientAcccept

    public void clientHandler(Socket s){
        new Thread(()->{
            try {
                ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                PrintWriter pw=new PrintWriter(s.getOutputStream());
                Partecipazione p = (Partecipazione) ois.readObject();
                String r="";
                Concorso tmp=null;
                synchronized (concorsi) {
                    for (Concorso c : concorsi.keySet()) {
                        if (c.getId() == p.getIdConcorso())
                            tmp = c;
                    }
                }
                if(tmp==null|| p.getNome().equals("") || tmp.getNumPosti()>this.concorsi.get(tmp).size()
                        || p.getCognome().equals("") || p.getIdConcorso()==0
                        || p.getCurriculum().equals("") || p.getCodiceFiscale().equals("")){
                    r="NOT_ACCEPTED";
                    pw.write(r);
                }else{
                    synchronized (concorsi) {
                        r = this.concorsi.get(tmp).size() + ":" + (new Date());
                    }
                    pw.write(r);
                }
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }).start();
    }//clientHandler

    public void clientCanc(){
        new Thread(()->{
            while(true) {
                try {
                    MulticastSocket socket = new MulticastSocket(uPort);
                    socket.joinGroup(InetAddress.getByName("230.0.0.1"));
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Il messaggio contiente id di cancellazione: " + msg);
                    byte[] buffer = null;
                    boolean trovato = false;
                    synchronized (concorsi) {
                        for (Concorso c : concorsi.keySet()) {
                            for (Integer i : concorsi.get(c)) {
                                if (i == Integer.parseInt(msg)) {
                                    trovato = true;
                                    System.out.println("Richiesta del cliente eliminata: " + concorsi.get(c).remove(i));
                                    buffer = ("true").getBytes();
                                    packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("230.0.0.1"), uPort);
                                    socket.send(packet);
                                    break;
                                }
                                if (trovato)
                                    break;
                            }
                        }
                    }
                    if (!trovato) {
                        buffer = ("false").getBytes();
                        packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("230.0.0.1"), uPort);
                        socket.send(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//clientCanc

    public void concorsoTerm(){
        new Thread(()->{
            while(true) {
                try {
                    MulticastSocket ms = new MulticastSocket(uPort2);
                    InetAddress ip = InetAddress.getByName("230.0.0.1");
                    String r="";
                    boolean scaduto=false;
                    Thread.sleep(10000);
                    synchronized (concorsi) {
                        for (Concorso c : concorsi.keySet()) {
                            if(c.getScadenza().after(new Date())){
                                scaduto=true;
                                r = c.getId()+"";
                                for(int i=0;i<c.getNumPosti();i++) {
                                    int winnerIndex = new Random().nextInt(concorsi.get(c).size());
                                    r=r+":"+concorsi.get(c).get(winnerIndex);//andrebbero messi i codici fiscali ma come al solito sono una bestia e ho fatto male lastruttura dei salvataggi
                                }
                            }
                        }
                    }
                    if(scaduto)
                        ms.send(new DatagramPacket(r.getBytes(), r.getBytes().length, ip, uPort));
                }catch (InterruptedException | IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }//concorsoTerm

    public void run(){
        clientAccept();
        clientCanc();
        concorsoTerm();
    }//run

    public static void main(String[] args){

    }//main
}
