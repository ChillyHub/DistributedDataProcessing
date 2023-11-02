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
