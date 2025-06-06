package Appello8_2_2023.secondaParte;

import java.util.Date;

public class Concorso {

    private int id;
    private int numPosti;
    private Date scadenza;

    public Concorso(int id, int numPosti, Date scadenza) {
        this.id = id;
        this.numPosti = numPosti;
        this.scadenza = scadenza;
    }

    public int getId() {
        return id;
    }

    public int getNumPosti() {
        return numPosti;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void decrPosti(){
        numPosti--;
    }

    public String toString() {
        return this.id+":"+this.numPosti+":"+this.scadenza;
    }

    public int hashCode(){
        return this.id;
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Concorso))
            return false;
        Concorso c = (Concorso)o;
        return this.id==c.id;
    }
}//Concorso
