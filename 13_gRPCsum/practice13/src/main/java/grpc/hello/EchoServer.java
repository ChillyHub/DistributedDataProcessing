package grpc.hello;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

public class EchoServer extends EchoServiceGrpc.EchoServiceImplBase {
    @Override
    public void echo(EchoRequest request,
                     io.grpc.stub.StreamObserver<EchoResponse> responseObserver) {
        System.out.println("receive:"+request.getMessage());
        EchoResponse response = EchoResponse.newBuilder().setMessage("from server:"+request.getMessage()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void summ(SummRequest request, StreamObserver<SummResponse> responseObserver) {
        System.out.println("receive:" + request.getClientId());
        System.out.println("    sum:" + request.getNum1() + " " + request.getNum2());
        SummResponse response = SummResponse.newBuilder().setResult(request.getNum1() + request.getNum2()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void summArray(SummArrayRequest request, StreamObserver<SummResponse> responseObserver) {
        System.out.println("receive:" + request.getClientId());
        System.out.println("    sum:" + request.getNumsList());
        SummResponse response = SummResponse.newBuilder().
                setResult(request.getNumsList().stream().reduce(0, Integer::sum)).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws Exception{
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new EchoServer()).build();
        server.start();
        System.out.println("Server started");
        server.awaitTermination();
    }
}
