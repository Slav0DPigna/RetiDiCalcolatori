package Es1;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        printLocalAddress();
        System.out.println("----------------");
        printRemoteAddress("www.dimes.unical.it");
        System.out.println("----------------");
        printRemoteAddress("8.8.4.4");
        System.out.println("----------------");
        printAllRemoteAddress("dns.google");
    }//main

    private static void printAllRemoteAddress(String name) {
        try {
            InetAddress[] machines = InetAddress.getAllByName(name);
            for (int i = 0; i < machines.length; i++) {
                System.out.println("Host name ("+i+"):"+machines[i].getHostName());
                System.out.println("Host IP ("+i+"):"+machines[i].getHostAddress());
            }
        }catch (UnknownHostException e) {
            System.out.println("Unknown host: " + name);
        }
    }//printAllRemoteAddress

    static void printLocalAddress(){
        try{
            InetAddress myself = InetAddress.getLocalHost();
            System.out.println("My name: "+myself.getHostName());
            System.out.println("My IP: "+myself.getHostAddress());
        }catch (UnknownHostException e){
            System.out.println("Failed to find my self");
        }
    }//printLocalAddress

    static void printRemoteAddress(String name){
        try{
            InetAddress machine = InetAddress.getByName(name);
            System.out.println("My name: "+machine.getHostName());
            System.out.println("My IP: "+machine.getHostAddress());
        }catch (UnknownHostException e){
            System.out.println("Unknown host: " + name);
        }
    }//printRemoteAddress
}//main
