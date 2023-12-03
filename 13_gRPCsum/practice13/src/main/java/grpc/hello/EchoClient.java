package grpc.hello;
import io.grpc.*;

import java.util.Arrays;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        EchoServiceGrpc.EchoServiceBlockingStub client = createClient("localhost",8080);
        System.out.println("Connected to server");
        Scanner console = new Scanner(System.in);
        //String message;
        //while((message = console.nextLine())!=null){
        //    EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        //    EchoResponse response = client.echo(request);
        //    System.out.println("response="+response.getMessage());
        //}

        try {
            System.out.println("Input two integer to calculate sum (in different lines): ");
            SummRequest summRequest = SummRequest.newBuilder()
                    .setClientId(client.toString())
                    .setNum1(Integer.parseInt(console.nextLine()))
                    .setNum2(Integer.parseInt(console.nextLine())).build();
            SummResponse summResponse = client.summ(summRequest);
            System.out.println("summResponse=" + summResponse.getResult());

            System.out.println("Input integers array to calculate sum (in one line with space to split): ");
            SummArrayRequest summArrayRequest = SummArrayRequest.newBuilder()
                    .setClientId(client.toString())
                    .addAllNums(Arrays.stream(console.nextLine().split("\\s+")).mapToInt(Integer::parseInt).boxed().toList()).build();
            SummResponse summArrayResponse = client.summArray(summArrayRequest);
            System.out.println("summArrayResponse=" + summArrayResponse.getResult());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private static EchoServiceGrpc.EchoServiceBlockingStub createClient(String host, int port){
        Channel channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        return EchoServiceGrpc.newBlockingStub(channel);
    }
}
