package Appello8_2_2023.secondaParte;

import java.io.Serializable;
import java.util.Date;

public class Partecipazione implements Serializable {

    private int idConcorso;
    private String nome, cognome, codiceFiscale ,curriculum;
    private Date data;

    public Partecipazione(int idConcorso, String nome, String cognome, String codiceFiscale, String curriculum) {
        this.idConcorso = idConcorso;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.curriculum = curriculum;
        this.data = new Date();//verificare che restituisca la data corrente come mi aspetto
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

    public Date getData() {
        return data;
    }

    public String toString() {
        return this.idConcorso+":"+this.nome+":"+this.cognome+":"+this.codiceFiscale+":"+this.curriculum;
    }

    public int hashCode(){
        return (this.idConcorso+""+this.codiceFiscale).hashCode();
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Partecipazione))
            return false;
        Partecipazione p = (Partecipazione)o;
        return this.idConcorso==p.idConcorso && this.codiceFiscale.equals(p.codiceFiscale);
    }
}//Partecipazione
