package net.server;

import car.BasicCarServer;
import car.CarEventsListener;
import car.CarPainter;
import car.FieldMatrix;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends BasicCarServer implements Runnable{
    private final int port;

    public Server(FieldMatrix fieldMatrix, CarEventsListener carEventsListener, int port){
        super(fieldMatrix,carEventsListener);
        this.port = port;
        new Thread(this).start();
    }

    public static void main(String[] args) {
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        new Server(fm, p, 8080);
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientWorker(socket, this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
