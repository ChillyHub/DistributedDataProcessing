package car;

import car.command.*;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception{
        FieldMatrix fm = new FieldMatrix(10,12);
        CarPainter p = new CarPainter(fm);
        BasicCarServer carServer = new BasicCarServer(fm, p);
        Car car = carServer.createCar();

        Script script = new Script();
        script.add(new ColorCommand(car, Color.YELLOW));
        script.add(new NameCommand(car, "Car0"));
        script.add(new SpeedCommand(car, 500));
        script.add(new RightCommand(car, 2));
        script.add(new DownRightCommand(car, 2));
        script.add(new UpRightCommand(car, 3));
        script.add(new UpCommand(car, 1));
        script.add(new UpLeftCommand(car, 2));
        script.add(new SpeedCommand(car, 300));
        script.add(new DownLeftCommand(car, 13));
        script.add(new RightCommand(car, 13));
        script.add(new DownRightCommand(car, 13));
        script.add(new SpeedCommand(car, 500));
        script.add(new UpCommand(car, 13));

        script.execute();
    }
}
