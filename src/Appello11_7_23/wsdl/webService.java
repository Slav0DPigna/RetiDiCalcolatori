package Appello11_7_23.wsdl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class webService implements Serializable {

    HashMap<Grossista,ArrayList<PrezzoProdotto>> grossisti;

    public webService(){
        grossisti=new HashMap<>();
    }

    /*
    Prezzo prodotto:
    String pIva,nomeProdotto;
    double newPrice;
     */

    public void updatePrezzo(PrezzoProdotto p){
        Grossista currentG= null;
        for(Grossista g: grossisti.keySet()){
            if(g.pIva.equals(p.pIva)){
                currentG=g;
                break;
            }
        }
        for(int i=0;i<grossisti.get(currentG).size();i++){
            if( grossisti.get(currentG).get(i).nomeProdotto.equals(p.nomeProdotto)){
                grossisti.get(currentG).get(i).price=p.price;
                System.out.println("prezzo aggiornato");
                break;
            }
        }
    }//updatePrezzo

    public String minorPrezzoMedio(String ortaggio){//restituisce la regione col prezzo medio minore
        String r="";
        int count=0;
        int total=0;
        int media=0;
        HashSet<String> province=new HashSet<String>();
        for(Grossista g: grossisti.keySet()){
           province.add(g.provincia);
        }
        for(String p: province){
            if(r.isEmpty())
                r=p;
            for(Grossista g: grossisti.keySet()){
                if(g.provincia.equals(p)){
                    for(PrezzoProdotto pp: grossisti.get(g)){
                        total+=pp.price;
                        count++;
                    }
                }
            }
            int mediaNew=0;
            if(count!=0)
                mediaNew=total/count;
            if(mediaNew>media) {
                media = mediaNew;
                r=p;
            }
            media=0;
            total=0;
            count=0;
        }
            return r;
    }//minorPrezzoMedio

}
