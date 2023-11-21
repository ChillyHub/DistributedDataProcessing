package net.rmi;

import net.command.CommandCode;
import net.command.SerializableCommand;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class RemoteCarClient {

    public static final int port = 8080;
    public static final String name = "RMICarServer";
    public static final String host = "127.0.0.1";

    public static Random random;

    public static void main(String[] args) throws Exception {
        random = new Random(System.currentTimeMillis());

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            System.out.println(String.format("Client connected to registry on host %s and port %d",host, port));
            RemoteCarServer server = (RemoteCarServer) registry.lookup(name);
            System.out.println("server = " + server);

            int carIndex = createCar(server);
            randomMoveCommands(server, carIndex);

        } catch (RemoteException e) {
            System.out.println("Remote: " + e.getMessage());
        }
    }

    private static int createCar(RemoteCarServer server) throws RemoteException {
        int carIndex = server.executeCommand(new SerializableCommand(0, "CREATECAR", ""));
        server.executeCommand(new SerializableCommand(carIndex, "SETNAME", "Car" + carIndex));
        return carIndex;
    }

    private static void randomMoveCommands(RemoteCarServer server, int carIndex) throws RemoteException {
        CommandCode cmd = getRandomCommandCode(4);
        SerializableCommand command = new SerializableCommand(carIndex, cmd.name(), "1");

        while (true) {
            if (!(boolean)server.executeCommand(command)) {
                cmd = getRandomCommandCode(4);
                command = new SerializableCommand(carIndex, cmd.name(), "1");
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
