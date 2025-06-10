package Appello11_7_23.wsdl;

public class Grossista {

    public String pIva,provincia;

    public Grossista(String pIva, String provincia) {
        this.pIva = pIva;
        this.provincia = provincia;
    }

    public String toString() {
        return this.pIva+this.provincia;
    }

    public int hashCode(){
        return this.pIva.hashCode()+this.provincia.hashCode();
    }

    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof Grossista))
            return false;
        Grossista g = (Grossista)o;
        return this.pIva.equals(g.pIva) && this.provincia.equals(g.provincia);
    }
}
