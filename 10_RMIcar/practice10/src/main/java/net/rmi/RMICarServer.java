package net.rmi;

import car.*;
import car.command.Command;
import net.command.SerializableCommand;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMICarServer extends BasicCarServer implements RemoteCarServer {

    public static final int port = 8080;
    public static final String name = "RMICarServer";

    public RMICarServer(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        super(fieldMatrix, carEventsListener);
    }

    @Override
    public <T> T executeCommand(SerializableCommand command) throws RemoteException {
        Car car = carServer.getCar(command.carIndex);
        String nextLine = command.commandName + " " + command.commandparameter;
        Command cmd = Command.createCommand(car, nextLine);

        return (T) cmd.execute();
    }
    public static void main(String[] args) throws Exception {
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        RMICarServer server = new RMICarServer(fm, p);

        Registry registry = LocateRegistry.createRegistry(port);
        RemoteCarServer obj = (RemoteCarServer) UnicastRemoteObject.exportObject(server, 0);
        registry.bind(name, obj);
        System.out.println("Server started on port: " + port);
    }
}
