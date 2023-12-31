package car.command;

import car.Car;
import car.CarServer;
import car.util.ColorFactory;

public class ChangeColorCommand extends Command<String, Boolean> {
    public ChangeColorCommand(String parameter, Car car){
        super(parameter, car);
    }

    @Override
    public Boolean execute() {
        car.setColor(ColorFactory.getColor(parameter));
        return true;
    }

    static{
        factory.put(ChangeColorCommand.class, (param, car)->new ChangeColorCommand(param,car));
    }
}
