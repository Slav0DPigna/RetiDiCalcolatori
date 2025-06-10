package Appello11_7_23.wsdl;

import java.io.Serializable;

public class PrezzoProdotto implements Serializable {
    String pIva,nomeProdotto;
    double price;

    public PrezzoProdotto(String pIva, String nomeProdotto, double price) {
        this.pIva = pIva;
        this.nomeProdotto = nomeProdotto;
        this.price = price;
    }

}
