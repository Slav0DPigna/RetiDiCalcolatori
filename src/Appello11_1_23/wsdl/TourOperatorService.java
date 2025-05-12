package Appello11_1_23.wsdl;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TourOperatorService implements Serializable {

    int data= LocalDate.now().getYear();

    HashMap<String,Struttura> strutture=new HashMap<>();//nome struttura, struttura
    HashMap<String,LinkedList<Prenotazione>> prenotazioni=new HashMap<>();//nome struttura, prenotazioni per quella struttura


    public void addPrenotazione(Prenotazione prenotazione) {
        if (!prenotazioni.keySet().contains(prenotazione.nome))
            prenotazioni.put(prenotazione.nome,new LinkedList<>());
        prenotazioni.get(prenotazione.nome).add(prenotazione);
    }

    public int numeroPrenotazioni(String nomeStruttura) {
        int c=0;
        ArrayList<Prenotazione> allPren = new ArrayList<>(prenotazioni.get(nomeStruttura));
        ArrayList<Prenotazione> pren = new ArrayList<>();
        for (Prenotazione prenotazione : allPren)
            if (prenotazione.anno==data)
                pren.add(prenotazione);
        for (Prenotazione prenotazione : pren)
            c+=prenotazione.numeroPersone;
        return c;
    }

    public List<Struttura> migliroreStruttura(String citta, int numStelle){
        ArrayList<Struttura> struttureCitta=new ArrayList<>();
        ArrayList<Struttura> ms=new ArrayList<>();
        struttureCitta.add(strutture.get(citta));
        for(int i=0;i<struttureCitta.size();i++){
            if(struttureCitta.get(i).stelle>=numStelle){
                ms.add(struttureCitta.get(i));
            }
        }
        return ms;
    }
}//TourOperatorService
