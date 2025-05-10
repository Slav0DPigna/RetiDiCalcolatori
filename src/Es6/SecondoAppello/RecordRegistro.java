package Es6.SecondoAppello;

import Es6.SecondoAppello.Offerta;
import Es6.SecondoAppello.Richiesta;

import java.net.*;

public class RecordRegistro {

    Socket enteSocket;
    Offerta miglioreOfferta = null;
    Richiesta richiesta;
    boolean status;

    public RecordRegistro(Socket socket, Richiesta richiesta) {
        this.enteSocket = socket;
        this.richiesta = richiesta;
        this.status = true;
    }

    public void setMiglioreOfferta(Offerta miglioreOfferta) {
        this.miglioreOfferta = miglioreOfferta;
    }

    public boolean isActive(){
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}//RecordRegistro
