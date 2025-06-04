package Appello11_1_23.secondaParte;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class Sensore extends Thread {

    private final int tPort = 3000;
    private final int uPort = 4000;
    private final int id;
    private Misura misura;

    public Sensore(int id) {
        this.id = id;
        misura = new Misura(this.id);
    }

    public Misura getMisura(){
        return misura;
    }

    public void setMisura(double value){
        this.misura.setValue(value);
    }

    public int hashCode(){
        return id;
    }

    public boolean equals(Object o){
        if(o == null)
            return false;
        if(!(o instanceof Sensore))
            return false;
        Sensore s = (Sensore) o;
        return id == s.id;
    }//equals

    public String toString(){
        return ""+id;
    }

    public void run(){
        try {
            while(true) {
                Thread.sleep(new Random().nextInt(1000,10000));//dovrebbe essere ogni 5 minuti l'invio della misura
                this.misura.misura();
                InetAddress mcastAddress = InetAddress.getByName("230.0.0.1");
                MulticastSocket ms = new MulticastSocket(uPort);
                byte[] buffer = this.misura.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, mcastAddress, uPort);
                ms.send(packet);
                System.out.println("Inviata misura al server " + this.misura);
            }
        }catch(InterruptedException | IOException e){
            e.printStackTrace();
        }
    }//run

    public static void main(String[] args){
        for(int i = 0; i <= 10; i++){
            new Sensore(i).start();
        }
    }

}//Sensore
