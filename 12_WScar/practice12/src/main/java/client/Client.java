package client;

import car.webservice.Color;
import car.webservice.Direction;
import car.webservice.Server;
import car.webservice.ServerService;

import javax.xml.ws.WebServiceException;
import java.awt.*;
import java.net.URL;
import java.util.Random;

public class Client {
    public static void main(String[] args) throws Exception {
        try {
            String url = "http://127.0.0.1:8090/CarServer?wsdl";
            ServerService service = new ServerService(new URL(url));
            Server serverPort = service.getServerPort();

            runCarsThreads(4, serverPort);
        } catch (WebServiceException e) {
            System.out.println("WebServiceException: " + e.getMessage());
        }
    }

    public static final Random random = new Random(System.currentTimeMillis());

    private static void runCarsThreads(int carCount, Server serverPort) {
        for (int i = 0; i < carCount; i++) {
            new Thread(() -> {
                try {
                    int carIndex = serverPort.createCar();
                    System.out.println("carIndex = " + carIndex);

                    serverPort.setCarName(carIndex, "Car" + carIndex);
                    serverPort.setCarColor(carIndex, "blue");
                    randomMoveCommands(serverPort, carIndex);
                } catch (WebServiceException e) {
                    System.out.println("WebServiceException: " + e.getMessage());
                }
            }).start();
        }
    }

    private static void randomMoveCommands(Server serverPort, int carIndex) throws WebServiceException {
        Direction direction = getRandomDirection();
        while (true) {
            if (!serverPort.moveCarTo(carIndex, direction)) {
                direction = getRandomDirection();
            }
        }
    }

    private static Direction getRandomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }
}