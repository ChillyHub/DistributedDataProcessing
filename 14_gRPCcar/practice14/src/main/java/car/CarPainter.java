package car;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class CarPainter extends JPanel implements CarEventsListener {

    private final FieldMatrix fieldMatrix;
    private final static int preferedCellSize = 60;
    private final static int preferedGap = 24;

    private final java.util.List<Car> cars;

    private final JFrame f;

    public CarPainter(FieldMatrix fieldMatrix) {
        super();
        cars = new ArrayList<>();
        this.fieldMatrix = fieldMatrix;
        f = new JFrame("Cars");
        setBackground(Color.LIGHT_GRAY);
        f.setSize(fieldMatrix.cols * preferedCellSize + 2 * preferedGap, fieldMatrix.rows * preferedCellSize + 2 * preferedGap);
        f.add(this);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //
        f.setSize(fieldMatrix.cols * preferedCellSize + 2 * preferedGap, fieldMatrix.rows * preferedCellSize + (cars.size() + 2) * preferedGap);

        int screenWidth = getWidth();
        int screenHeight = getHeight();
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.BLACK);
        int width = Math.min(screenHeight - 2 * preferedGap, screenWidth - 2 * preferedGap);
        int step = width / Math.max(fieldMatrix.cols, fieldMatrix.rows);
        //
        int gapStep = Math.max(1, cars.size());
        for (int i = 0; i <= fieldMatrix.rows; i++)
            for (int j = 0; j <= fieldMatrix.cols; j++) {
                g.drawLine(i * step + preferedGap, j * step + preferedGap * gapStep, fieldMatrix.rows * step, j * step + preferedGap * gapStep);
                g.drawLine(i * step + preferedGap, j * step + preferedGap * gapStep, i * step + preferedGap, fieldMatrix.cols * step + preferedGap * (gapStep - 1));
                if ( i<fieldMatrix.rows && j<fieldMatrix.cols && fieldMatrix.getCellState(i,j) == FieldMatrix.CellState.WALL) {
                    g.setColor(Color.RED);
                    g.fill3DRect(j * step + preferedGap, i * step + preferedGap * gapStep, step, step, false);
                    g.setColor(Color.BLACK);
                }
            }

        //
        int yGap = preferedGap * 3 / 4;
        for (Car car : cars) {
            // TODO: Drawing repaint info
            g.setColor(Color.BLUE);
            g.drawString(String.format("Car [%s] || Move Direction: %s",
                    car.getName(), car.getDirection()), preferedGap, yGap);
            yGap += preferedGap;

            Position p = car.getPosition();
            g.setColor(car.getColor());
            g.fill3DRect(p.col * step + preferedGap, p.row * step + preferedGap * gapStep, step, step, false);
            if (car.getName() != null){
                int stringWith = fm.stringWidth(car.getName());
                g.setColor(Color.WHITE);
                g.drawString(car.getName(),p.col * step + preferedGap + (step - stringWith)/2, p.row * step + preferedGap * gapStep + step/2);
            }

        }
    }

    @Override
    public void carCreated(Car car) {
        cars.add(car);
        repaint();
    }

    @Override
    public void carDestroyed(Car car) {
        cars.remove(car);
        repaint();
    }

    @Override
    public void carMoved(Car car, Position from, Position to, boolean success) {
        repaint();
    }

    @Override
    public void carParameterChanged(Car car) {repaint();}


}
