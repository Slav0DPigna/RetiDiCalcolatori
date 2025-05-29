package Lotteria;

import Appello11_1_23.wsdl.Struttura;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Banco {

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
            ServerSocket server = new ServerSocket(3000);
            while(true){
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
                        Biglietto bigliettoTmp = lotteriaTmp.vendiBiglietto();
                        System.out.println("Venduto biglietto : "+bigliettoTmp);
                        r[i]=bigliettoTmp;
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }//vendiBiglietti

    public void estraivincente(){
        //TODO
    }//estraiVincente

    public static void main(String[] args){
        Lotteria[] l={new Lotteria("Napoli")};
        Banco b= new Banco(l);
        b.vendiBiglietti();
    }
}//Banco