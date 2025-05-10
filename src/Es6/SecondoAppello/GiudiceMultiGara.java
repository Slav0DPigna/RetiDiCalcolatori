package Es6.SecondoAppello;

import Es6.PrimoAppello.Giudice;

import java.io.IOException;

public class GiudiceMultiGara extends Giudice {

    private final static RegistroGare registro = new RegistroGare();

    public void avvia(){
        try{
            new ThreadRichiesteHandler(registro).start();
            new TheadOfferteHandler(registro).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }//avvia

    public static void main(String[] args) {
        new GiudiceMultiGara().avvia();
    }
}
