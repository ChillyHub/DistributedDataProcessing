package tcp;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class SerializeTCPClient {
    public static void main(String args[]) {
        // arguments supply message and hostname
        int serverPort = 8088;
        //
        int clientPort = 8090;

        String serverHost = "localhost";//args[1];
        String message = "Hello world";//args[6];
        System.out.print("Connecting to "+serverHost+":"+serverPort+"...");
        try (Socket s = new Socket(serverHost, serverPort)) {
            System.out.println("Connected!");
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());

            // TODO: 2. Add sever port to message
            Message msg = new Message(9, "HELP!!!", serverHost, clientPort);

            out.writeObject(msg);
            System.out.println("Data sent: " + msg);
        } catch (
                UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (
                EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (
                IOException e) {
            System.out.println("Readline:" + e.getMessage()); // error in reading the stream
        }

        // TODO: 1. Use UDP to listen port
        try (DatagramSocket socket = new DatagramSocket(clientPort)) {
            // Receive
            byte[] receiveBytes = new byte[105];
            DatagramPacket response = new DatagramPacket(receiveBytes, receiveBytes.length);
            // TODO: 4. Receive and print
            socket.receive(response);
            System.out.println("UDP Response: " + new String(response.getData()));
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage()); // socket creation failed
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage()); // can be caused by send
        }
    }
}