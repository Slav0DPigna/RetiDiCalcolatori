package Es2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeClient {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(host);
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address,3575);
        socket.send(packet);
        packet=new DatagramPacket(buf,buf.length);
        socket.receive(packet);
        String response=(new String(packet.getData()));
        System.out.println(response);
        socket.close();
    }
}
