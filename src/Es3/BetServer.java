package Es3;

import java.io.*;
import java.net.*;
import java.util.*;

public class BetServer {

    public class Scommessa{
        private int ID_scommessa;
        private int N_cavallo;
        private long puntata;
        private InetAddress scommettitore;
        private static int nextID = 0;

        public Scommessa(int N_cavallo, long puntata, InetAddress scommettitore ){
            this.N_cavallo = N_cavallo;
            this.puntata = puntata;
            this.scommettitore = scommettitore;
            this.ID_scommessa = nextID++;
        }

        public int getID(){
            return ID_scommessa;
        }

        public int getCavallo() {
            return N_cavallo;
        }

        public InetAddress getScommettitore(){
            return scommettitore;
        }

        public long getPuntata(){
            return puntata;
        }

        public boolean equals(Object o){
            if(!(o instanceof Scommessa)){
                return false;
            }
            Scommessa s = (Scommessa) o;
            return N_cavallo == s.getCavallo();
        }//equals
    }//scommessa

    public class BetAccepter extends Thread {
        private int port;
        private ServerSocket server;
        private boolean accept;

        public BetAccepter(int port) {
            try{
                this.port = port;
                server = new ServerSocket(port);
                accept = true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }//costruttore

        public void run(){
            while(accept){
                try{
                    Calendar now = Calendar.getInstance();
                    server.setSoTimeout((int)(limite.getTimeInMillis()-now.getTimeInMillis()));
                    Socket k = server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(k.getInputStream()));
                    PrintWriter out = new PrintWriter(k.getOutputStream(), true);
                    String line = in.readLine();
                    int pos = line.indexOf(' ');
                    int numCavallo = Integer.parseInt(line.substring(0,pos));
                    long puntata = Long.parseLong(line.substring(pos+1));
                    InetAddress ip = k.getInetAddress();
                    Scommessa s = new Scommessa(numCavallo,puntata,ip);
                    int key = s.getID();
                    scommesse.put(key,s);
                    out.println("Scommessa accettata");
                    out.close();
                    k.close();
                    System.out.println("Ricevuta scommessa: "+ip+" "+numCavallo+" "+puntata);
                }catch (SocketTimeoutException ste){
                    accept = false;
                    System.out.println("Tempo scaduto per puntare");
                }catch (IOException ioe){
                    ioe.printStackTrace();

                }
            }//while
            try{
                server.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }//run
    }//BetAccepter

    public class BetDenyer extends Thread {
        private int port;
        private ServerSocket server;
        private boolean closed;

        public BetDenyer(int port) {
            try {
                this.port = port;
                this.closed = true;
                server = new ServerSocket(port);
            }catch (IOException e){
                e.printStackTrace();
            }
        }//costruttore

        public void reset(){
            closed = false;
        }//reset

        public void run(){
            try{
                while(closed){
                    Socket k = server.accept();
                    PrintWriter out = new PrintWriter(k.getOutputStream(), true);
                    out.println("Scommesse chiuse");
                    out.close();
                    k.close();
                    System.out.println("Scommessa rifiutata");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }//run
    }//BetDenyer

    private HashMap<Integer,Scommessa> scommesse;
    private Calendar limite;
    private BetAccepter accepter;//questi sono due thread
    private BetDenyer denyer;
    private int port;

    public BetServer(int port,Calendar limite) {
        this.scommesse = new HashMap<Integer,Scommessa>();
        this.port = port;
        this.limite = limite;
        this.accepter = new BetAccepter(this.port);
        accepter.start();
    }//costruttore

    public void accettaScommesse(){
        try{
            accepter.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void rifiutaScommesse(){
        denyer=new BetDenyer(port);
        denyer.start();
    }

    public void resetServer(){
        denyer.reset();
    }

    public LinkedList<Scommessa> controllaScommesse(int cavalloVincente){
        LinkedList<Scommessa> elenco= new LinkedList<>();
        for(int i=0;i<scommesse.size();i++){
            Scommessa s = scommesse.get(i);
            if (s.equals(new Scommessa(cavalloVincente,0,null))){
                elenco.add(s);
            }
        }
        return elenco;
    }//controllaScommesse

    public void comunicaVincitori(LinkedList<Scommessa> vincitori,InetAddress ind,int port){
        ListIterator<Scommessa> it = vincitori.listIterator();
        try{
            MulticastSocket socket = new MulticastSocket();
            byte[] buf = new byte[256];
            String m="";
            int quota=12;
            while(it.hasNext()){
                Scommessa s=it.next();
                m += s.getScommettitore() +" " +(s.getPuntata()*quota)+"\n";
                System.out.println("________________\n"+m+"_________________\n");
            }
            if (vincitori.size()==0){
                m+="Questa volta non ha vinto nessuno riprovate :-)";
            }
            buf = m.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, ind, port);
            socket.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }//comunicavincitori

    public static void main(String[] args) {
        int serverPort = 8001;
        int clientPort = 8002;
        try{
            Calendar deadline = Calendar.getInstance();
            deadline.add(Calendar.MINUTE, 1);
            BetServer server = new BetServer(serverPort,deadline);
            System.out.println("Scommesse aperte");
            server.accettaScommesse();
            server.rifiutaScommesse();
            int vincente = ((int) (Math.random()*12))+1;
            System.out.println("Il numero del cavallo vincente: "+vincente);
            LinkedList<Scommessa> elencoVincitori = server.controllaScommesse(vincente);
            InetAddress multiAddr = InetAddress.getByName("230.0.0.1");
            server.comunicaVincitori(elencoVincitori,multiAddr,clientPort);
            Thread.sleep(100);
            server.resetServer();
        }catch (IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }//main

}
