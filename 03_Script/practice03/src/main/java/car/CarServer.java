package car;

public interface CarServer {
    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }

    Car createCar();
    void destroyCar(Car car);
    boolean moveCarTo(Car car, Direction direction);

}
