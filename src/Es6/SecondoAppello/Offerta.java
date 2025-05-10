package Es6.SecondoAppello;

public class Offerta extends Es6.PrimoAppello.Offerta {

    private int idGara;

    public Offerta(int id, int importoRichiesto, int idGara) {
        super(id, importoRichiesto);
        this.idGara = idGara;
    }

    public int getIdGara() {
        return idGara;
    }

    public int getIdPartecipante(){
        return super.getId();
    }

    @Override
    public String toString() {
        return "Offerta [idPartecipante= "+getIdPartecipante()+
                ", idGara= "+ idGara+
                ", importoRichiesto= "+getImportoRichiesto()+
                "]";
    }
}
