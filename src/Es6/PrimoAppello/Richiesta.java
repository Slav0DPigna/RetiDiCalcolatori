package Es6.PrimoAppello;

import java.io.Serializable;

public class Richiesta implements Serializable {
    //importantissimo che sia serializzabile questa é linformazione che viaggia su rete
    private String descrizione;
    private int importoMassimo;

    public Richiesta(String descrizione, int importoMassimo) {
        this.descrizione = descrizione;
        this.importoMassimo = importoMassimo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getImportoMassimo() {
        return importoMassimo;
    }

    @Override
    public String toString() {
        return "Richiesta{descrizione="+descrizione + " importoMassimo= " + importoMassimo+" }";
    }
}
