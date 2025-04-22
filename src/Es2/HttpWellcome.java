package Es2;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

/*
Questa è un'implementazione molto specializzata di un server HTTP non ci verrà riesta all'esame ma capirla non è male
 */

public class HttpWellcome {

    private static int port=8080;

    private static String HtmlWellcomeMessage(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"it\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Benvenuto!</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            min-height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            background-color: #fff;\n" +
                "            padding: 40px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #007bff;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        p {\n" +
                "            font-size: 1.1em;\n" +
                "            line-height: 1.6;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 12px 24px;\n" +
                "            background-color: #28a745;\n" +
                "            color: white;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            font-weight: bold;\n" +
                "            transition: background-color 0.3s ease;\n" +
                "        }\n" +
                "        .button:hover {\n" +
                "            background-color: #218838;\n" +
                "        }\n" +
                "        .info {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 0.9em;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Ciao! \uD83D\uDC4B</h1>\n" +
                "        <p>Questo è un server di prova semplice ma accogliente.</p>\n" +
                "        <p>Sembra che tu abbia raggiunto il server con successo!</p>\n" +
                "        <a href=\"#\" class=\"button\">Esplora di più (Link di esempio)</a>\n" +
                "        <div class=\"info\">\n" +
                "            Server in esecuzione...\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected: "+ client);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                String request = in.readLine();
                System.out.println("Request: "+request);
                StringTokenizer st = new StringTokenizer(request);
                if ((st.countTokens()>=2) && (st.nextToken().equals("GET"))) {
                    String message=HtmlWellcomeMessage();
                    //start of response header
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Lenght: "+message.length());
                    out.println("Content-Type: text/html");
                    out.println();
                    //End of response header
                    out.println(message);
                }else{
                    out.println("HTTP/1.1 404 Not Found");
                }
                out.flush();/*flush è molto importante perchè si occupa di svuotare il buffer ed è una cosa che va fatta
                            in modo ponderato è anche un parametro che si può impostare a true nel costruttore che svuota il
                            buffer dopo ogni println ma in questo caso se l'avessimo fatto la nostra app non funzionava.
                            */
                client.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
