package Es9.esercizio2;

import java.io.Serializable;

public class Volo{
    private String citta;
    private Data data;
    private Orario orario;
    private String voloId;

    public Volo(String citta, Data data, Orario orario, String voloId) {
        super();
        this.citta = citta;
        this.data = data;
        this.orario = orario;
        this.voloId = voloId;
    }

    public String getCitta() {
        return citta;
    }

    public Data getData() {
        return data;
    }

    public Orario getOrario() {
        return orario;
    }

    public String getVoloId() {
        return voloId;
    }

    public int hashCode(){
        return toString().hashCode();
    }

    public boolean equals(Object o){
        if (o==null)
            return false;
        if(!(o instanceof Volo))
            return false;
        Volo v = (Volo)o;
        return toString().equals(v.toString());
    }

    public String toString(){
        return "id: "+voloId+" cittá: "+citta+" data: "+data+" orario: "+orario;
    }

}//Volo
