package car;

import java.awt.*;
import java.util.Random;

public class Car {
    private final CarServer carServer;

    private Position position;
    private static int count = 1;
    private int index;
    private int speed = 500;
    private Color color;
    private String name;

    private CarServer.Direction direction = CarServer.Direction.UP;

    public Car(CarServer carServer, Position position){
        this.carServer = carServer;
        this.position = position;
        speed = new Random().nextInt(300) + 300;
        index = count++;
        color = new Color((int)(Math.random() * 0x1000000));
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){return color;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public void destroy(){
        carServer.destroyCar(this);
    }

    public boolean moveTo(CarServer.Direction direction){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setDirection(direction);
        if (carServer.moveCarTo(this,direction)){
            position = position.move(direction);
            return true;
        }else
            return false;

    }
    public Position getPosition(){return position;}

    public int getIndex(){return index;}

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(CarServer.Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public CarServer.Direction getDirection() {
        return direction;
    }

    public void repaint() {
        carServer.repaint();
    }
}
