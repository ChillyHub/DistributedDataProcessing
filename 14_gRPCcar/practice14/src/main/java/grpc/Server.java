package grpc;

import car.*;
import car.util.ColorFactory;
import com.google.protobuf.Empty;
import grpc.carservice.*;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Server extends CarServiceGrpc.CarServiceImplBase {

    BasicCarServer carServer;

    protected Server(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        carServer = BasicCarServer.createCarServer(fieldMatrix, carEventsListener);
    }

    @Override
    public void createCar(AddNewCarRequest request, StreamObserver<AddNewCarResponse> responseObserver) {
        Car car = carServer.createCar();
        car.setName(request.getName());
        car.setColor(ColorFactory.getColor(request.getColor()));
        AddNewCarResponse response = AddNewCarResponse.newBuilder().setCarIndex(car.getIndex()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void moveCar(MoveCarRequest request, StreamObserver<MoveCarResponse> responseObserver) {
        Car car = carServer.getCar(request.getCarIndex());
        boolean ret = false;
        try {
            for (int i = 0; i < request.getCount(); i++) {
                ret = car.moveTo(CarServer.Direction.valueOf(request.getDirection().name()));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        MoveCarResponse response = MoveCarResponse.newBuilder().setResult(ret).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void setCarColor(SetCarColorRequest request, StreamObserver<Empty> responseObserver) {
        carServer.getCar(request.getCarIndex()).setColor(ColorFactory.getColor(request.getColor()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void setCarName(SetCarNameRequest request, StreamObserver<Empty> responseObserver) {
        carServer.getCar(request.getCarIndex()).setName(request.getName());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws Exception {
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);

        io.grpc.Server server = ServerBuilder
                .forPort(8080)
                .addService(new Server(fm, p)).build();
        server.start();
        System.out.println("Server started");
        server.awaitTermination();
    }
}
