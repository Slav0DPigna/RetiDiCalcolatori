package Es9.esercizio2;

import java.io.Serializable;

public class Orario implements Serializable {
    private int minuti,ore;

    public Orario(int minuti, int ore) {
        this.minuti = minuti;
        this.ore = ore;
    }

    public int getMinuti() {
        return minuti;
    }

    public int getOre() {
        return ore;
    }

    public int hashCode(){
        return toString().hashCode();
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Orario))
            return false;
        Orario or = (Orario) o;
        return or.minuti == minuti && or.ore == ore;
    }

    public String toString(){
        return minuti+":"+ore;
    }

}//Orario
