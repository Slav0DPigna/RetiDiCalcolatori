package Es6.SecondoAppello;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ThreadTimeoutHandler extends Thread {

    private static final long TIMEOUT = 60000;
    private static final int gPort = 3000;
    private static final String gAddress = "230.0.0.1";
    private final int idGara;
    private final MulticastSocket mSocket;
    private final RegistroGare registro;

    public ThreadTimeoutHandler(int idGara, RegistroGare registro) throws IOException {
        this.idGara = idGara;
        this.registro = registro;
        mSocket = new MulticastSocket(gPort);
    }

    public void run(){
        try{
            //invio richiesta a tutti i partecipanti
            inviaRichiestaAiPartecipanti(registro.getRichiesta(idGara));
            //attendo per un certo periodo
            sleep(TIMEOUT);
            //allo scadere del timeout chiudo la gara
            this.registro.chiudiGara(idGara);
            System.out.println("Gara con id " + idGara+ " chiusa");
            //invio all'ente e ai partecipanti l'esito della gara
            inviaEsitoGara();
        }catch (IOException | InterruptedException e){

        }
    }//run

    private void inviaRichiestaAiPartecipanti(Richiesta richiesta) throws IOException {
        String r = "RICHIESTA-" + richiesta.getIdEnte()+"-"+richiesta.getDescrizione()+"-"+richiesta.getImportoMassimo();
        byte[] buffer = r.getBytes();
        InetAddress address = InetAddress.getByName(gAddress);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, gPort);
        mSocket.send(packet);
        System.out.println("Inviata richiesta in multicast: "+richiesta);
    }

    private void inviaEsitoGara() throws IOException, InterruptedException {
        //invio notifica ai partecipanti
        System.out.println("Ricerco nel registro gara "+idGara);
        Offerta oVincente = this.registro.getOfferta(idGara);
        if(oVincente == null)
            oVincente = new Offerta(-1,-1,idGara);
        System.out.println(oVincente);
        String message = "ESITO-"+this.idGara+"-"+oVincente.getIdPartecipante()+"-"+oVincente.getImportoRichiesto();
        byte[] buffer = message.getBytes();
        InetAddress address = InetAddress.getByName(gAddress);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, gPort);
        mSocket.send(packet);
        //invio notifica all'ente
        ObjectOutputStream oos = new ObjectOutputStream(this.registro.getSocketEnte(idGara).getOutputStream());
        oos.writeObject(oVincente);
        System.out.println("Inviata offerta vincente all'ente");
        this.registro.getSocketEnte(idGara).close();
    }

}
