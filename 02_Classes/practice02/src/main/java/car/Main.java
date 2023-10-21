package car;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Random;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws Exception {
        FieldMatrix fm = new FieldMatrix(10,15);
        CarPainter p = new CarPainter(fm);
        BasicCarServer carServer = new BasicCarServer(fm, p);
        Car car = carServer.createCar();

        // TODO: Init Car
        car.setName("Car");
        car.setColor(Color.YELLOW);
        CarServer.Direction direction = randomInitCarPosDir(car, carServer);

        // TODO: Moving Loop
        while (true) {
            try {
                Bound bound = checkOnBounds(car, carServer);
                if (bound != Bound.None) {
                    direction = changeDirection(direction, bound);
                }

                car.moveTo(direction);
            } catch (Exception e) {
                break;
            }
        }
    }

    public enum Bound {
        None,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT
    }

    public static CarServer.Direction randomInitCarPosDir(Car car, BasicCarServer carServer) {
        Random r = new Random(System.currentTimeMillis());
        int rows = r.nextInt(carServer.fieldMatrix.rows);
        int cols = r.nextInt(carServer.fieldMatrix.cols);

        Position pos = new Position(rows, cols);
        car.moveTo(pos);

        int dir = r.nextInt(4);
        return switch (dir) {
            case 0 -> CarServer.Direction.UP_LEFT;
            case 1 -> CarServer.Direction.UP_RIGHT;
            case 2 -> CarServer.Direction.DOWN_LEFT;
            case 3 -> CarServer.Direction.DOWN_RIGHT;
            default -> CarServer.Direction.UP;
        };
    }

    public static Bound checkOnBounds(Car car, BasicCarServer carServer) {
        Position pos = car.getPosition();
        int rows = carServer.fieldMatrix.rows;
        int cols = carServer.fieldMatrix.cols;

        if (pos.row == 0) {
            if (pos.col == 0) {
                return Bound.UP_LEFT;
            } else if (pos.col == cols - 1) {
                return Bound.UP_RIGHT;
            } else {
                return Bound.UP;
            }
        } else if (pos.row == rows - 1) {
            if (pos.col == 0) {
                return Bound.DOWN_LEFT;
            } else if (pos.col == cols - 1) {
                return Bound.DOWN_RIGHT;
            } else {
                return Bound.DOWN;
            }
        } else if (pos.col == 0) {
            return Bound.LEFT;
        } else if (pos.col == cols - 1) {
            return Bound.RIGHT;
        }

        return Bound.None;
    }

    public static CarServer.Direction changeDirection(CarServer.Direction src, Bound bound) {
        return switch (src) {
            case UP -> CarServer.Direction.DOWN;
            case DOWN -> CarServer.Direction.UP;
            case LEFT -> CarServer.Direction.RIGHT;
            case RIGHT -> CarServer.Direction.LEFT;
            case UP_LEFT -> switch (bound) {
                case UP -> CarServer.Direction.DOWN_LEFT;
                case LEFT -> CarServer.Direction.UP_RIGHT;
                case UP_LEFT -> CarServer.Direction.DOWN_RIGHT;
                default -> throw new RuntimeException("Direction change error");
            };
            case UP_RIGHT -> switch (bound) {
                case UP -> CarServer.Direction.DOWN_RIGHT;
                case RIGHT -> CarServer.Direction.UP_LEFT;
                case UP_RIGHT -> CarServer.Direction.DOWN_LEFT;
                default -> throw new RuntimeException("Direction change error");
            };
            case DOWN_LEFT -> switch (bound) {
                case DOWN -> CarServer.Direction.UP_LEFT;
                case LEFT -> CarServer.Direction.DOWN_RIGHT;
                case DOWN_LEFT -> CarServer.Direction.UP_RIGHT;
                default -> throw new RuntimeException("Direction change error");
            };
            case DOWN_RIGHT -> switch (bound) {
                case DOWN -> CarServer.Direction.UP_RIGHT;
                case RIGHT -> CarServer.Direction.DOWN_LEFT;
                case DOWN_RIGHT -> CarServer.Direction.UP_LEFT;
                default -> throw new RuntimeException("Direction change error");
            };
        };
    }
}
