package Lotteria;

import java.io.Serializable;
import java.util.Random;

public class Biglietto implements Serializable {
    private final String nomeLotteria;
    private int id;


    public Biglietto(String nomeLotteria) {
        this.nomeLotteria = nomeLotteria;
        this.id = new Random().nextInt(1,10000);
    }

    public String getNomeLotteria() {
        return nomeLotteria;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id=id;
    }

    public int hashCode(){
        return nomeLotteria.hashCode()+this.id;
    }

    public boolean equals(Object o){
        if (o == null)
            return false;
        if (!(o instanceof Biglietto))
            return false;
        Biglietto b = (Biglietto) o;
        return this.id == b.getId() && this.nomeLotteria.equals(b.getNomeLotteria());
    }

    public String toString() {
        return nomeLotteria+":"+id;
    }
}//Biglietto
