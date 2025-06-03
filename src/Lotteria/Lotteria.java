package Lotteria;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Lotteria {

    private HashSet<Biglietto> bigliettiVenduti;
    private String nome;
    private int numBigliettiDisponibili;

    public Lotteria(String nome) {
        this.nome = nome;
        bigliettiVenduti = new HashSet<>();
        numBigliettiDisponibili = 10000 -1;
    }

    public String getNome(){
        return nome;
    }

    public int getNumBigliettiDisponibili(){
        return numBigliettiDisponibili;
    }

    public HashSet<Biglietto> getBigliettiVenduti(){
        return bigliettiVenduti;
    }

    public Biglietto vendiBiglietto(){
        Biglietto r = new Biglietto(nome);
        r.setId(0);
        if(numBigliettiDisponibili > 0) {
            r = new Biglietto(this.nome);
            while (bigliettiVenduti.contains(r)) {
                r = new Biglietto(this.nome);
            }
            numBigliettiDisponibili--;
        }
        bigliettiVenduti.add(r);
        return r;
    }

    public Biglietto estraiVincente(){
        int ret = new Random().nextInt(1,bigliettiVenduti.size());
        int i=0;
        Iterator<Biglietto> iterator = bigliettiVenduti.iterator();
        Biglietto r = null;
        while (i<ret && iterator.hasNext()) {
            r = iterator.next();
            i++;
        }
        return r;
    }

    @Override
    public String toString() {
        return "Nome lotteria: "+nome+" ,Biglietti disponibili: "+numBigliettiDisponibili;
    }

    public boolean equals(Object o){
        if (o == null)
            return false;
        if(!(o instanceof Lotteria))
            return false;
        Lotteria l = (Lotteria)o;
        return nome.equals(l.getNome());
    }
}//Lotteria
