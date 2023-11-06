package tcp;

import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String args[]) {
        // arguments supply hostname, port and message
        String serverHost = args.length>0? args[0]: "localhost";
        int serverPort = args.length>1? Integer.getInteger(args[1]): 8080;
        String message = args.length>2? args[2]: "Hello TCP for java!";
        System.out.print("Connecting to "+serverHost+":"+serverPort+"...");
        try (Socket s = new Socket(serverHost, serverPort)) {
            System.out.println("Connected!");
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(message); // UTF is a string encoding
            String data = in.readUTF(); // read a line of data from the stream
            System.out.println("Received: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        }
    }
}
