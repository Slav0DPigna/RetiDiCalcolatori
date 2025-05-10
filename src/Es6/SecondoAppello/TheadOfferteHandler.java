package Es6.SecondoAppello;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TheadOfferteHandler extends Thread {

    private final RegistroGare registro;
    private ServerSocket socket;
    private final static int oPort=4000;

    public TheadOfferteHandler(RegistroGare registro) {
        this.registro=registro;
    }

    public void run() {
        try{
            this.socket=new ServerSocket(oPort);
            while(true){
                Socket partecipante=this.socket.accept();
                ObjectInputStream ois=new ObjectInputStream(partecipante.getInputStream());
                Offerta offerta = (Offerta) ois.readObject();
                this.registro.aggiungiOfferta(offerta);
                partecipante.close();
            }
        }catch (IOException  | ClassNotFoundException e){

        }
    }

}
