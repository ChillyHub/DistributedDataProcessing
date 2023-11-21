package rmi.client;

import rmi.common.HelloChat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient {
    public static final int port = 8080;
    public static final String name = "HelloChat";
    public static final String host = "127.0.0.1";

    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry(host, port);
        System.out.println(String.format("Client connected to registry on host %s and port %d",host, port));
        HelloChat server = (HelloChat) registry.lookup(name);
        System.out.println("server = " + server);

        // 1:
        String s = server.message("Chilly", "Hello");
        System.out.println(s);

        // 3:
        String m = server.objMessage(new Message2("Chilly", "Hello"));
        System.out.println(m);
    }
}
