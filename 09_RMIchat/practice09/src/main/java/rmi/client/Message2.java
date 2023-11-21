package rmi.client;

import rmi.server.Message;

public class Message2 extends Message {

    private static final long serialVersionUID = 1L;

    public Message2(String name, String message) {
        super(name, message);
    }

    public String getName() {
        return name;
    }
}
