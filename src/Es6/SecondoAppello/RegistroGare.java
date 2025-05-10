package Es6.SecondoAppello;

import Es6.SecondoAppello.Offerta;
import Es6.SecondoAppello.Richiesta;
import java.net.*;

import java.util.HashMap;

public class RegistroGare {

    private static int COUNTER = 0;
    HashMap<Integer,RecordRegistro> gare = new HashMap<>();

    public Richiesta getRichiesta(int idGara){
        return this.gare.get(idGara).richiesta;
    }

    public Offerta getOfferta(int idGara){
        return this.gare.get(idGara).miglioreOfferta;
    }

    public Socket getSocketEnte(int idGara){
        return this.gare.get(idGara).enteSocket;
    }

    public synchronized int addGara(Socket enteSocket, Richiesta richiesta){
        //Andare a vedere cosa vuol dire e come si usa synchronized in java. in teoria funziona come async in python.
        int idGara = COUNTER++;
        this.gare.put(idGara,new RecordRegistro(enteSocket,richiesta));
        return idGara;
    }

    public synchronized void chiudiGara(int idGara){
        if(this.gare.containsKey(idGara)){
            this.gare.get(idGara).setStatus(false);
        }
    }

    public synchronized void aggiungiOfferta(Offerta offerta){
        int idGara = offerta.getIdGara();
        if(this.gare.containsKey(idGara)){
            RecordRegistro gara = this.gare.get(idGara);
            if(gara.isActive()){
                Offerta oVincente=gara.miglioreOfferta;
                if (
                        (oVincente == null && offerta.getImportoRichiesto() <= gara.richiesta.getImportoMassimo()) ||
                        oVincente.getImportoRichiesto() > offerta.getImportoRichiesto() ||
                        (oVincente.getImportoRichiesto() == offerta.getImportoRichiesto() &&
                        offerta.getIdPartecipante() < oVincente.getIdPartecipante())){
                    gara.setMiglioreOfferta(offerta);
                    System.out.println("Aggiunta offerta per gara "+ idGara);
                }else{
                    System.out.println("Offerta rifiutata per gara "+ idGara);
                }
            }
        }
    }//aggiungiOfferta

}//RegistroGare
