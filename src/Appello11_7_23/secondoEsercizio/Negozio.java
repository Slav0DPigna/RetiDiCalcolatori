package Appello11_7_23.secondoEsercizio;

import java.io.*;
import java.net.*;

public class Negozio extends Thread {

    public String pIva, siglaNazione;

    public Negozio(String pIva,String siglaNazione){
        this.pIva = pIva;
        this.siglaNazione = siglaNazione;
    }

    public void run(){
        try{
            Socket socket=new Socket(InetAddress.getByName("127.0.0.1"),3000);
            Richiesta r = new Richiesta(this.pIva,this.siglaNazione,"123",25,8);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(r);
            MulticastSocket ms =new MulticastSocket(3000);
            InetAddress group=InetAddress.getByName("230.0.0.1");
            ms.joinGroup(group);
            DatagramPacket dp=new DatagramPacket(new byte[1024],1024);
            ms.receive(dp);
            String recive=new String(dp.getData(),0,dp.getLength());
            System.out.println("Offerta migliore "+recive);
        }catch(IOException e){}
    }//run

    public static void main(String[] args){
        for(int i=0; i<10; i++){
            new Negozio("123456","IT").start();
        }
    }
}//Negozio
