package tcp;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;

public class Message implements Serializable {
    public int priority;
    public String message;
    public Integer[] data = new Integer[10];

    public String addressHost;
    public int severPort;

    public Message(int p, String msg) {
        this.priority = p;
        this.message = msg;
        Arrays.fill(data, 20);

        this.addressHost = "localhost";
        this.severPort = 8088;
    }

    public Message(int p, String msg, String addressHost, int severPort) {
        this.priority = p;
        this.message = msg;
        Arrays.fill(data, 20);

        this.addressHost = addressHost;
        this.severPort = severPort;
    }

    public String getAddressHostName() {
        return addressHost;
    }

    public int getSeverPort() {
        return severPort;
    }

    @Override
    public String toString() {
        return "class Message: priority=" + priority + " message=" + message +
                " arrays=" + Arrays.asList(data) + " sever port=" + severPort;
    }
}