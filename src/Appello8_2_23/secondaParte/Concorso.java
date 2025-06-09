package Appello8_2_23.secondaParte;

import java.io.Serializable;
import java.util.Date;

public class Concorso implements Serializable {

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

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Concorso))
            return false;
        Concorso c = (Concorso)o;
        return this.id==c.id;
    }

    public int hashCode(){
        return this.id;
    }

    public String toString(){
        return id+":"+numPosti+":"+scadenza;
    }
}//Concorso
