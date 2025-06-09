package Appello8_2_2023.secondaParte;

import java.io.Serializable;

public class Partecipazione implements Serializable {
    private int idConcorso;
    private String nome,cognome,codiceFiscale,curriculum;

    public Partecipazione(int idConcorso, String nome, String cognome, String codiceFiscale, String curriculum) {
        this.idConcorso = idConcorso;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.curriculum = curriculum;
    }

    public int getIdConcorso() {
        return idConcorso;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Partecipazione))
            return false;
        Partecipazione p = (Partecipazione)o;
        return this.idConcorso == p.idConcorso && this.codiceFiscale.equals(p.codiceFiscale);
    }

    public int hashCode() {
        return this.idConcorso+this.codiceFiscale.hashCode();
    }

    public String toString() {
        return idConcorso+":"+nome+":"+cognome+":"+codiceFiscale+":"+curriculum;
    }
}//Partecipazione
