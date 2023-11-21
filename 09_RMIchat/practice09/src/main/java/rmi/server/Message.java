package rmi.server;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected String message;

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
