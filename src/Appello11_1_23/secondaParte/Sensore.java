package Appello11_1_23.secondaParte;

import java.io.Serializable;

public class Sensore implements Serializable {

    private final int id;
    private Misura misura;

    public Sensore(int id) {
        this.id = id;
        misura = new Misura(this.id,0);
    }

    public Misura getMisura(){
        return misura;
    }

    public void setMisura(double value){
        this.misura.setValue(value);
    }

    public int hashCode(){
        return id;
    }

    public boolean equals(Object o){
        if(o == null)
            return false;
        if(!(o instanceof Sensore))
            return false;
        Sensore s = (Sensore) o;
        return id == s.id;
    }//equals

    public String toString(){
        return ""+id;
    }

}//Sensore
