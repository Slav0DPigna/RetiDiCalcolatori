package Appello11_1_23.wsdl;

import java.io.*;

public class Struttura implements Serializable {

    private String nome, citta;
    private int stelle, posti;

    public Struttura(String nome, String citta, int stelle, int posti) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.posti = posti;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public int getStelle() {
        return stelle;
    }

    public int getPosti() {
        return posti;
    }

    public String toString() {
        return nome + " " + citta + " " + stelle + " " + posti;
    }
}//Struttura
