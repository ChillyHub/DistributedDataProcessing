package rmi.common;

import rmi.server.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloChat extends Remote {
    String message(String name, String message) throws RemoteException;
    String objMessage(Message message) throws RemoteException;
}
