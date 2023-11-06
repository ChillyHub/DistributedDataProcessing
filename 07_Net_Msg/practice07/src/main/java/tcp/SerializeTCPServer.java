package tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class SerializeTCPServer {
    public static void main(String args[]) {
        try {
            int serverPort = 8088; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort); // new server port generated
            System.out.println("SerializeServer listen port: " + serverPort);

            // TODO: UDP Server socket
            DatagramSocket udpSocket = new DatagramSocket(serverPort);

            while (true) {
                Socket clientSocket = listenSocket.accept(); // listen for new connection
                ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                Object data = ois.readObject();
                System.out.println("Received data: " + data);

                // TODO: 3. Use UDP to response
                byte[] responseBytes = data.toString().getBytes();
                DatagramPacket response = new DatagramPacket(responseBytes, responseBytes.length,
                        InetAddress.getByName(((Message)data).getAddressHostName()), ((Message)data).getSeverPort());
                System.out.println("UDP Send response to client: " +
                        InetAddress.getByName(((Message)data).getAddressHostName()) + "/:" + ((Message)data).getSeverPort());
                udpSocket.send(response);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage()); // socket creation failed
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}