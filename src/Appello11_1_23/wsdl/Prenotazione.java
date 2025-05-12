package Appello11_1_23.wsdl;

import java.io.Serializable;

public class Prenotazione implements Serializable {

    private String nomeStruttura;
    private int anno;
    private int numPersone;

    public Prenotazione(String nomeStruttura, int anno, int numPersone) {
        this.nomeStruttura = nomeStruttura;
        this.anno = anno;
        this.numPersone = numPersone;
    }

    public String getNomeStruttura() {
        return nomeStruttura;
    }

    public int getAnno() {
        return anno;
    }

    public int getNumPersone() {
        return numPersone;
    }
}
