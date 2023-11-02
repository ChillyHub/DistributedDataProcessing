package car;

import car.command.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class Main {
    public static void main(String[] args) throws Exception{
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("field.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        //FieldMatrix fm = new FieldMatrix(10,10);
        CarPainter p = new CarPainter(fm);
        BasicCarServer carServer = new BasicCarServer(fm, p);

        class CarMover implements Runnable {
            private final String name;

            public CarMover(String name){
                this.name = name;
            }
            @Override
            public void run() {
                Random random = new Random();
                Car car = carServer.createCar();
                car.setName(name);
                CarServer.Direction direction = CarServer.Direction.DOWN;
                while(true){
                    boolean result;
                    try {
                        result = car.moveTo(direction);
                    }catch(ArrayIndexOutOfBoundsException e){
                        result = false;
                    }
                    if (!result){
                        direction = CarServer.Direction.values()[random.nextInt(CarServer.Direction.values().length)];
                    }
                }
            }
        }

        class WallModifier implements Runnable {
            private final FieldMatrix fm;
            private final int speed;

            public WallModifier(FieldMatrix fm, int speed) {
                this.fm = fm;
                this.speed = speed;
            }

            @Override
            public void run() {
                Random random = new Random();
                while (true) {
                    boolean result;
                    try {
                        result = fm.modifyWall(random.nextInt(fm.rows), random.nextInt(fm.cols));
                        if (result) {
                            carServer.repaint();
                        }
                        try {
                            Thread.sleep(speed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch(ArrayIndexOutOfBoundsException e){
                        result = false;
                    }
                }
            }
        }

        new Thread(new CarMover("Car1")).start(); //car1
        Thread.sleep(1000);
        new Thread(new CarMover("Car2")).start(); //car2
        Thread.sleep(1000);
        new Thread(new CarMover("Car3")).start(); //car3
        Thread.sleep(1000);
        new Thread(new CarMover("Car4")).start(); //car4

        Thread.sleep(1000);
        new Thread(new WallModifier(fm, 500)).start();
    }
}
