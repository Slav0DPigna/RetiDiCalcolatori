package Es3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/*
* Primo esercizio vero e proprio che potrei trovarmi all'esame:
* Si vuole realizzare un server per gestire delle scommesse ippiche.
* Il server riceve le scommesse da parte di client che indicano il numero del cavallo (tra 1 e 12) su cui vogliono puntare.
* Le scommesse sono ricevute, tramite il protocollo TCP, sulla porta 8001 del server.
* Il client effettua una scommessa inviando al server la stringa "<num_cavallo> <puntata>", in cui <num_cavallo> è il numero del cavallo,
* e <puntata> è l'ammontare in euro della scommessa.
* Il server accetta le scommesse fino all'ora <ora_limite>.
* A tal fine un thread del server controlla periodicamente, ad es. ogni minuto, l'ora del sistema.
* Quando l'ora corrente raggiunge l'ora <ora_limite>, il server deve chiudere l'accettazione delle scommesse.
* Il server memorizza in un'opportuna struttura dati le scommesse pervenute.
* Scaduto il tempo utile, il server verifica il numero del cavallo vincente
* (a tal proposito si può utilizzare un metodo che estrae un numero casuale da 1 a 12),
* e successivamente comunica a tutti gli scommettitori (con un messaggio in multicast) l'elenco degli scommettitori vincenti e la somma vinta.
* Tale comunicazione avviene tramite l'invio, sul gruppo multicast "230.0.0.1" e sulla porta "8002"
* cui sono collegati tutti i client, di coppie di stringhe "<ip_vincitore> <somma>" dove <ip_vincitore>
* è l'indirizzo ip di un client che ha puntato sul cavallo vincente, e <somma> è l'ammontare della somma vinta,
* corrispondente alla scommessa effettuata dal client moltiplicata per 12.
*/
public class BetClient {
    private int serverPort;
    private int myPort;
    private InetAddress groupAddress;
    private InetAddress serverAddress;
    private Socket s;

    public BetClient(InetAddress groupAddress, InetAddress serverAddress,int serverPort,int myPort) {
        this.groupAddress = groupAddress;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.myPort = myPort;
        try{
            s=new Socket(serverAddress,serverPort);
        }catch (IOException e){
            e.printStackTrace();
        }
    }//costruttore

    public boolean placeBet(int nCavallo, long puntata){
        String e="";
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            String bet = nCavallo+" "+puntata;
            System.out.println(bet);
            out.println(bet);//invia la scommessa
            e=br.readLine();//riceve il messaggio per capire se la scommessa é accettata o meno.
            System.out.println(e);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return e.equals("Scommessa accettata");
    }//placeBet

    public void riceviElencoVincitori(){
        try{
            MulticastSocket socket=new MulticastSocket(myPort);
            socket.joinGroup(groupAddress);
            byte[] buf=new byte[256];
            DatagramPacket packet=new DatagramPacket(buf,buf.length);//questo è il costruttore per ricevere un datagramma
            socket.receive(packet);
            String elenco = new String(packet.getData());
            System.out.println("Elenco:\n"+elenco);
        }catch (IOException e){
            e.printStackTrace();
        }
    }//riceviElencoVincitori

    public static void main(String[] args) {
        int serverPort = 8001;
        int myPort = 8002;
        try{
            InetAddress groupAddress = InetAddress.getByName("230.0.0.1");
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            BetClient client = new BetClient(groupAddress,serverAddress,serverPort,myPort);
            int cavallo = ((int)(Math.random()*12))+1;
            int puntata = ((int)(Math.random()*100))+1;
            if ( client.placeBet(cavallo,puntata))
                client.riceviElencoVincitori();
        }catch (IOException e){
            e.printStackTrace();
        }
    }//main
}
