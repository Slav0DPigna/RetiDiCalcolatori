package Appello11_1_23.wsdl;

import java.io.Serializable;

public class Prenotazione implements Serializable {

    int id,anno,numeroPersone;
    String nome;
    Struttura strutta;

    public Prenotazione(int id, int anno, int numeroPersone,String nome, Struttura strutta) {
        this.id = id;
        this.anno=anno;
        this.numeroPersone=numeroPersone;
        this.nome = nome;
        this.strutta = strutta;
    }

    public String toString(){
        return "Prenotazione [id=" + id + ", nome=" + nome + "]";
    }
}
