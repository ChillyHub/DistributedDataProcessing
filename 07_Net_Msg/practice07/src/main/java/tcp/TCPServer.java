package tcp;

import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String args[]) {
        try {
            int serverPort = args.length>0? Integer.getInteger(args[0]): 8080; // the server port > 1024
            ServerSocket listenSocket = new ServerSocket(serverPort); // new server port generated
            System.out.println("Server listen port: " + serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept(); // listen for new connection
                ClientConnection c = new ClientConnection(clientSocket); // launch new thread
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}
