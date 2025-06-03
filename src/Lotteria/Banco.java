package Lotteria;

import Appello11_1_23.wsdl.Struttura;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Banco extends Thread{

    private final int tport=3000;
    private final int uPort=4000;
    private ArrayList<Lotteria> lotterie;

    public Banco(){
        lotterie=new ArrayList<Lotteria>();
    }

    public Banco(Lotteria[] args){
        lotterie=new ArrayList<Lotteria>();
        for(int i=0;i<args.length;i++)
            lotterie.add(args[i]);
    }

    public void vendiBiglietti(){
        try{
            ServerSocket server = new ServerSocket(tport);
            server.setSoTimeout(10000);
            AtomicBoolean venditaFinita = new AtomicBoolean(false);
            new Thread(()->{
                try{
                    Thread.sleep(10000);
                    venditaFinita.set(true);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
            while(!venditaFinita.get()){
                System.out.println("Attesa di un nuovo cliente...");
                Socket tSocket = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(tSocket.getInputStream()));
                String richiesta = br.readLine();
                System.out.println("Ricevuta richiesta: "+richiesta);
                String[] parts = richiesta.split(",");
                String nome = parts[0];
                Lotteria lotteriaTmp = null;
                int numBiglietti = Integer.parseInt(parts[1]);
                boolean esisteLotteria = false;
                for(int i=0;i<lotterie.size();i++){
                    if(lotterie.get(i).getNome().equals(nome)) {
                        esisteLotteria = true;
                        lotteriaTmp = lotterie.get(i);
                        break;
                    }
                }
                Biglietto[] r = new Biglietto[numBiglietti];
                if(esisteLotteria && numBiglietti<=lotteriaTmp.getNumBigliettiDisponibili())
                    for(int i=0;i<numBiglietti;i++){
                        if(venditaFinita.get()){
                            Biglietto bigliettoTmp = new Biglietto(lotteriaTmp.getNome());
                            bigliettoTmp.setId(0);
                            System.out.println("Venduto biglietto : "+bigliettoTmp);
                            r[i]=bigliettoTmp;
                        }else{
                            Biglietto bigliettoTmp = lotteriaTmp.vendiBiglietto();
                            System.out.println("Venduto biglietto : "+bigliettoTmp);
                            r[i]=bigliettoTmp;
                        }
                    }
                if(!esisteLotteria){
                    System.out.println("La lotteria non esiste");
                }
                if(!(numBiglietti<=lotteriaTmp.getNumBigliettiDisponibili()) && esisteLotteria)
                    System.out.println("Biglietti non disponibili");

                ObjectOutputStream oos = new ObjectOutputStream(tSocket.getOutputStream());
                oos.writeObject(r);
                oos.flush();
                oos.close();tSocket.close();
            }
            System.out.println("Vendita finita");
        }catch (IOException e){
            e.printStackTrace();
        }
    }//vendiBiglietti

    public void estraivincente(){
        HashMap<Lotteria,Biglietto> r = new HashMap<Lotteria,Biglietto>();
        try {
            DatagramSocket uSocket = new MulticastSocket(uPort);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            byte[] buf = new byte[1024];
            for (int i = 0; i < lotterie.size(); i++) {
                r.put(lotterie.get(i), lotterie.get(i).estraiVincente());
            }
            for (Lotteria k : r.keySet()) {
                System.out.println("Estrazione vincente per la lotteria: " + k.getNome() + ":" + r.get(k));
                String stringBuf = ""+r.get(k);
                buf = stringBuf.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, group, uPort);
                uSocket.send(dp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }//estraiVincente

    public void run(){
        vendiBiglietti();
        estraivincente();
    }

    public static void main(String[] args){
        Lotteria[] l={new Lotteria("Napoli")};
        Banco b= new Banco(l);
        b.start();
    }
}//Banco