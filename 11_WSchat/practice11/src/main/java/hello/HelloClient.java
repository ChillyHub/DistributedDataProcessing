package hello;

import hello.webservice.HelloServer;
import hello.webservice.HelloServerService;
import hello.webservice.Message;


public class HelloClient {
    public static final String url = "http://localhost:8080/Hello?wsdl";
    public static void main(String[] args) throws Exception{
        HelloServerService helloService = new HelloServerService();
        HelloServer hello = helloService.getHelloServerPort();
        System.out.println(hello);
        System.out.println(hello.sayHello());

        Message message = new Message();
        message.setName("Client");
        message.setMessage("Hello server");
        System.out.println(hello.sendMessage(message));
    }


}
