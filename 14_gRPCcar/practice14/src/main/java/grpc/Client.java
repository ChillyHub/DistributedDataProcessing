package grpc;

import grpc.carservice.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

import java.util.Random;

public class Client {
    public static void main(String[] args) throws Exception {
        CarServiceGrpc.CarServiceBlockingStub client = createClient("localhost",8080);
        System.out.println("Connected to server");

        runCarsThreads(10, client);
    }

    public static final Random random = new Random(System.currentTimeMillis());

    private static CarServiceGrpc.CarServiceBlockingStub createClient(String host, int port){
        Channel channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        return CarServiceGrpc.newBlockingStub(channel);
    }

    private static void runCarsThreads(int carCount, CarServiceGrpc.CarServiceBlockingStub client) {
        for (int i = 0; i < carCount; i++) {
            new Thread(() -> {
                try {
                    AddNewCarRequest request = AddNewCarRequest.newBuilder().setName("Car").setColor("black").build();
                    AddNewCarResponse response = client.createCar(request);
                    int carIndex = response.getCarIndex();
                    System.out.println("carIndex = " + carIndex);

                    SetCarNameRequest nameRequest = SetCarNameRequest.newBuilder().setCarIndex(carIndex).setName("Car" + carIndex).build();
                    client.setCarName(nameRequest);

                    SetCarColorRequest colorRequest = SetCarColorRequest.newBuilder().setCarIndex(carIndex).setColor("blue").build();
                    client.setCarColor(colorRequest);

                    randomMoveCommands(client, carIndex);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }).start();
        }
    }

    private static void randomMoveCommands(CarServiceGrpc.CarServiceBlockingStub client, int carIndex) throws Exception {
        Direction direction = getRandomDirection();
        while (true) {
            MoveCarRequest request = MoveCarRequest.newBuilder()
                    .setCarIndex(carIndex).setDirection(direction).setCount(1).build();
            MoveCarResponse response = client.moveCar(request);
            if (!response.getResult()) {
                direction = getRandomDirection();
            }
        }
    }

    private static Direction getRandomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length - 1)];
    }
}
