package Es9.esercizio2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Aereoporto {

    private HashMap<Data,HashMap<String,LinkedList<Volo>>> db;
    public Aereoporto() {
        db = new HashMap<Data,HashMap<String,LinkedList<Volo>>>();
    }

    public String primoVolo(String citta, Data data){
        if(!db.containsKey(data) || !db.get(data).containsKey(citta))
            return "Nessun aereo";
        return db.get(data).get(citta).getFirst().getVoloId();
    }

    public Orario orarioVolo(String voloId, Data data){
        LinkedList<Volo> tmp;
        if(db.containsKey(data)){
            for(Map.Entry<String,LinkedList<Volo>> e : db.get(data).entrySet()){
                tmp = e.getValue();
                for(Volo v : tmp){
                    if(v.getVoloId().equals(voloId))
                        return v.getOrario();
                }
            }
        }
        return new Orario(-1,-1);
    }
}//Aereoporto
