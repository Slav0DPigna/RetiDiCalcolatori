package Es6.SecondoAppello;

public class Richiesta extends Es6.PrimoAppello.Richiesta{

    private int idEnte;

    public Richiesta(String descrizione, int importoMassimo, int idEnte) {
        super(descrizione, importoMassimo);
        this.idEnte = idEnte;
    }

    public int getIdEnte() {
        return idEnte;
    }

    @Override
    public String toString() {
        return "Richiesta [idEnte=" + idEnte +
                ", descrizione= "+ super.getDescrizione()+ '\''+
                ", importoMassimo= "+ super.getImportoMassimo()+
                "]";
    }
}
