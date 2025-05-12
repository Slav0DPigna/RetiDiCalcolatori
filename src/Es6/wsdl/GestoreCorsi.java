package Es6.wsdl;

import java.util.HashMap;

public class GestoreCorsi {

    HashMap<String,Corso> corsi= new HashMap<>();

    public void aggiungiCorso(Corso corso){
        String nome = corso.nomeCorso;
        corsi.put(nome,corso);
    }

    public Corso getCorso(String nome){
        Corso c=null;
        if(corsi.containsKey(nome))
            c=corsi.get(nome);
        return c;
    }
}
