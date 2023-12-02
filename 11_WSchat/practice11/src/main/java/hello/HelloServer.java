package hello;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class HelloServer {
    public static final String url = "http://0.0.0.0:8080/Hello"; //bind all IPv4 interfaces

    @WebMethod
    public String sayHello(){
        return "Hello!";
    }

    @WebMethod
    public String sendMessage(Message message) {
        System.out.println(message.name + ": " + message.message);
        return "Hello, " + message.name + "!";
    }

    public static void main(String[] args) {
        HelloServer helloServer = new HelloServer();
        Endpoint.publish(url, helloServer);
        System.out.println("WebService started");
    }
}
