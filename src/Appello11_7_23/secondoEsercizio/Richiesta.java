package Appello11_7_23.secondoEsercizio;

import java.io.Serializable;

public class Richiesta implements Serializable {

    public String pIva, sigla, codicePordotto;
    public double prezzo;
    public int quantita;

    public Richiesta(String pIva, String sigla, String codicePordotto, double prezzo, int quantita) {
        this.pIva = pIva;
        this.sigla = sigla;
        this.codicePordotto = codicePordotto;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public String toString(){
        return pIva+":"+sigla+":"+codicePordotto+":"+prezzo+":"+quantita;
    }

    public int hashCode(){
        return pIva.hashCode()+codicePordotto.hashCode()+Double.hashCode(prezzo);
    }

    public boolean equals(Object o){
        if(o==null) return false;
        if(!(o instanceof Richiesta)) return false;
        Richiesta r = (Richiesta)o;
        return this.pIva.equals(r.pIva) && this.prezzo==r.prezzo && this.codicePordotto.equals(r.codicePordotto) ;
    }

}//Richiesta
