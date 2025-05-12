package Es6.wsdl;

import java.io.Serializable;

public class Corso implements Serializable {
    String nomeCorso, programma, docente;
    int numeroCrediti, oreLezione, oreEsercitazione;

    public Corso(String nomeCorso, String programma, String docente, int numeroCrediti, int oreLezione, int oreEsercitazione) {
        this.nomeCorso = nomeCorso;
        this.programma = programma;
        this.docente = docente;
        this.numeroCrediti = numeroCrediti;
        this.oreLezione = oreLezione;
        this.oreEsercitazione = oreEsercitazione;
    }//costruttore

    public Corso(String nomeCorso) {//non obbligatorio
        this.nomeCorso = nomeCorso;
    }

    public String toString() {//non obbligatorio
        return nomeCorso+" , "+numeroCrediti+" CFU";
    }

    /*
    per sbrigarci in questa classe non abbiamo messo modificatori d'accesso alle variabili cosí da non scrivere
    getter e setter.
     */
}//Corso
