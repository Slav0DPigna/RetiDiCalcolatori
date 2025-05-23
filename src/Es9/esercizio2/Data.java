package Es9.esercizio2;

import java.io.Serializable;

public class Data implements Serializable {
    private int giorno;
    private int mese;
    private int anno;

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public int getMese() {
        return mese;
    }

    public int hashCode(){
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Data))
            return false;
        Data data = (Data) o;
        return toString().equals(data.toString());
    }

    public String toString(){
        return giorno+"/"+mese+"/"+anno;
    }
}//Data
