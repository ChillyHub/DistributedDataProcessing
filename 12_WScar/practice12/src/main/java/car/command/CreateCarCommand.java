package car.command;

import car.BasicCarServer;
import car.Car;

public class CreateCarCommand extends Command<String, Integer> {
    static{
        factory.put(CreateCarCommand.class, (param, car)->new CreateCarCommand(param,car));
    }

    public CreateCarCommand(String parameter, Car car){
        super(parameter,car);
    }

    @Override
    public Integer execute() {
        Car car = BasicCarServer.getServer().createCar();
        return car.getIndex();
    }
}
