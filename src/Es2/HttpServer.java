package Es2;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class HttpServer {
    private static int port = 8080;

    public static void main(String[] args) {
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("HTTP server listening on port " + port);
            while(true){
                Socket client = server.accept();
                System.out.println("HTTP client connected to " + client);
                TheadedServer cc = new TheadedServer(client);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static class TheadedServer extends Thread{
        private Socket client;
        private BufferedReader is;
        private DataOutputStream os;

        public TheadedServer(Socket client){
            this.client = client;
            try{
                this.is = new BufferedReader(new InputStreamReader(client.getInputStream()));
                this.os = new DataOutputStream(new DataOutputStream(client.getOutputStream()));
            }catch (IOException e){
                try{
                    this.client.close();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                    return;
                }
            }
            this.start();
        }

        private void reply(DataOutputStream out,  File f) throws IOException{
            DataInputStream in = new DataInputStream(client.getInputStream());
            int len=(int) f.length();
            byte buf[]=new byte[len];
            in.readFully(buf);
            out.writeBytes("HTTP/1.1 200 OK\r\n");
            out.writeBytes("Content-Lenght: "+buf.length+"\r\n");
            out.writeBytes("Content-Type: text/html\r\n\r\n");
            out.write(buf);
            out.flush();
            in.close();
        }

        public void run() {
            try {
                String request = is.readLine();
                System.out.println("Request: " + request);
                StringTokenizer st = new StringTokenizer(request);
                if ((st.countTokens() >= 2) && (st.nextToken().equals("GET"))) {
                    if ((request = st.nextToken()).startsWith("/")) {
                        request = request.substring(1);
                    }if (request.endsWith("/") || request.equals("")) {
                        request = request + "index.html";//per impedire di andare in giro nelle routes
                    }if((request.indexOf("..")!=-1)||(request.startsWith("/"))){
                        os.writeBytes("403 Forbidden You do not have permission to access this resource" +request+"\r\n");
                    }else{
                        File f= new File(HtmlWellcomeMessage());
                        reply(os,f);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        private String HtmlWellcomeMessage(){
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
    }
}
