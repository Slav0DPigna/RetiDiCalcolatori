package Appello11_1_23.wsdl;

import java.io.*;

public class Struttura implements Serializable {

    String nome, citta;
    int stelle, posti;

    public Struttura(String nome, String citta, int stelle, int posti) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.posti = posti;
    }

    public String toString() {
        return nome + " " + citta + " " + stelle + " " + posti;
    }
}//Struttura
