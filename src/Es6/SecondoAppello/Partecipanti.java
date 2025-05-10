package Es6.SecondoAppello;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Random;

public class Partecipanti extends Es6.PrimoAppello.Partecipanti{

    public Partecipanti(int id) {
        super(id);
    }

    private void gestisciRichiesta(String[] richiestaParts) throws IOException, InterruptedException {
        int idGara = Integer.parseInt(richiestaParts[1]);
        int importoMax = Integer.parseInt(richiestaParts[3].trim());
        sleep(new Random().nextInt(60000));

        //Rispondo al giudice

        Socket socket = new Socket(super.getGIUDICE_ADDRESS(), 4000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        Offerta offerta = new Offerta(super.getPartId(), idGara, new Random().nextInt(importoMax));
        out.writeObject(offerta);
        System.out.println("Inviata offerta: "+offerta);
        socket.close();
    }

    public void run(){
        System.out.println("Avvio partecipanti con ID:"+ super.getPartId());
        MulticastSocket mSocket = null;
        try{
            mSocket = new MulticastSocket(3000);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            mSocket.joinGroup(group);
            while(true){
                byte[] buf = new byte[512];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                mSocket.receive(packet);
                String richiesta = new String(packet.getData(), 0, packet.getLength());
                String[] richiestaParts = richiesta.split("-");
                System.out.println("Ricevuto pacchetto multicast: "+richiesta);
                if(richiestaParts[0].equals("RICHIESTA")){
                    gestisciRichiesta(richiestaParts);
                }else{
                    System.out.println(richiesta);
                }
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }//run

    public static void main(String[] args){
        int n=10;
        for(int i=0; i<n; i++){
            new Partecipanti(i).start();
        }
    }
}
