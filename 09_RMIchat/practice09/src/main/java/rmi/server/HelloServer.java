package rmi.server;

import rmi.common.HelloChat;
import rmi.client.Message2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelloServer implements HelloChat {
    public static final int port = 8080;
    public static final String name = "HelloChat";

    @Override
    public String message(String name, String message) {
        System.out.println(name + ": " + message);
        return "Hello, " + name + "!";
    }

    // 2:
    @Override
    public String objMessage(Message message) {
        return "Hello, " + ((Message2)message).getName() + "!";
    }

    public static void main(String[] args) throws Exception{
        HelloServer server = new HelloServer();
        Registry registry = LocateRegistry.createRegistry(port);
        HelloChat obj = (HelloChat) UnicastRemoteObject.exportObject(server, 0);
        registry.bind(name, obj);
        System.out.println("Server started on port: " + port);
    }
}
