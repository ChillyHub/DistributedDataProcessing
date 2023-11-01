package car.command;

import car.Car;

import java.awt.*;

public class BlinkCommand implements Command{
    private final Car car;
    private final Color blinkColor;

    public BlinkCommand(Car car, Color blinkColor){
        this.car = car;
        this.blinkColor = blinkColor;
    }

    public BlinkCommand(Car car, String blinkColorName){
        this.car = car;

        switch (blinkColorName.toLowerCase()) {
            case "white":
                this.blinkColor = Color.white;
                break;
            case "lightgray":
                this.blinkColor = Color.lightGray;
                break;
            case "gray":
                this.blinkColor = Color.gray;
                break;
            case "darkgray":
                this.blinkColor = Color.darkGray;
                break;
            case "black":
                this.blinkColor = Color.black;
                break;
            case "red":
                this.blinkColor = Color.red;
                break;
            case "pink":
                this.blinkColor = Color.pink;
                break;
            case "orange":
                this.blinkColor = Color.orange;
                break;
            case "yellow":
                this.blinkColor = Color.yellow;
                break;
            case "green":
                this.blinkColor = Color.green;
                break;
            case "magenta":
                this.blinkColor = Color.magenta;
                break;
            case "cyan":
                this.blinkColor = Color.cyan;
                break;
            case "blue":
                this.blinkColor = Color.blue;
                break;
            default:
                this.blinkColor = Color.getColor(blinkColorName);
                break;
        }
    }

    @Override
    public boolean execute() {
        Color oldColor = car.getColor();
        Color newColor = blinkColor;
        for(int i = 0; i < 10; i++) {
            car.setColor(newColor);
            try {
                Thread.sleep(car.getSpeed());
                System.out.println("blink!!!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            car.repaint();

            Color tmp = oldColor;
            oldColor = newColor;
            newColor = tmp;
        }
        car.setColor(oldColor);
        return true;
    }
}
