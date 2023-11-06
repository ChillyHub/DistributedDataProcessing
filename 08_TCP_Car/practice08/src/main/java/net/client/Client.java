package net.client;

import car.command.Command;
import net.CommandCode;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static Random random;

    public static void main(String[] args) {
        random = new Random(System.currentTimeMillis());

        try (Socket clientSocket = new Socket("localhost", 8080)) {
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());

            // loadScriptAndSend("script.txt", out);

            randomMoveScriptAndSend(is, os);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        }
    }

    private static void loadScriptAndSend(String fileName, DataOutputStream out) throws IOException {
        InputStreamReader stream = new InputStreamReader(
                Client.class.getClassLoader().getResourceAsStream(fileName));
        Scanner scanner = new Scanner(stream);

        while (scanner.hasNextLine()) {
            String script = scanner.nextLine();
            String[] tokens = script.split("\\s+");

            out.writeUTF(tokens[0]);
            out.writeUTF(tokens[1]);
        }
    }

    private static void randomMoveScriptAndSend(DataInputStream in, DataOutputStream out)
            throws IOException {
        String command = "SET_NAME";
        String loop = "Car" + random.nextInt(100);

        out.writeUTF(command);
        out.writeUTF(loop);
        in.readBoolean();

        CommandCode cmd = getRandomCommandCode(4);
        command = cmd.name();
        loop = "1";

        while (true) {
            out.writeUTF(command);
            out.writeUTF(loop);

            if (!in.readBoolean()) {
                cmd = getRandomCommandCode(4);
                command = cmd.name();
            }
        }
    }

    private static CommandCode getRandomCommandCode() {
        return CommandCode.values()[random.nextInt(CommandCode.values().length)];
    }

    private static CommandCode getRandomCommandCode(int range) {
        return CommandCode.values()[random.nextInt(Math.min(range, CommandCode.values().length))];
    }
}
