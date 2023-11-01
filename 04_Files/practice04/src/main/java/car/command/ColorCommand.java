package car.command;

import car.Car;
import java.awt.Color;

public class ColorCommand implements Command{
    private final Car car;
    private final Color color;

    public ColorCommand(Car car, Color color){
        this.car = car;
        this.color = color;
    }

    public ColorCommand(Car car, String colorName) {
        this.car = car;

        switch (colorName.toLowerCase()) {
            case "white":
                this.color = Color.white;
                break;
            case "lightgray":
                this.color = Color.lightGray;
                break;
            case "gray":
                this.color = Color.gray;
                break;
            case "darkgray":
                this.color = Color.darkGray;
                break;
            case "black":
                this.color = Color.black;
                break;
            case "red":
                this.color = Color.red;
                break;
            case "pink":
                this.color = Color.pink;
                break;
            case "orange":
                this.color = Color.orange;
                break;
            case "yellow":
                this.color = Color.yellow;
                break;
            case "green":
                this.color = Color.green;
                break;
            case "magenta":
                this.color = Color.magenta;
                break;
            case "cyan":
                this.color = Color.cyan;
                break;
            case "blue":
                this.color = Color.blue;
                break;
            default:
                this.color = Color.getColor(colorName);
                break;
        }
    }

    @Override
    public boolean execute() {
        car.setColor(color);
        return true;
    }
}
