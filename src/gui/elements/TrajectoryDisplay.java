package gui.elements;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import data.rocket.Stage;
import gui.instruments.PaintStrategy;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.management.Calculation;
import process.management.SimulationManager;

import javax.swing.*;
import java.awt.*;

/**
 * This class draws the trajectory of the rocket. Used in {@link gui.SimulationGUI}.
 *
 * @author Alice Mabille
 * @see gui.SimulationGUI
 */
public class TrajectoryDisplay extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerUtility.getLogger(TrajectoryDisplay.class, "html");

    private int scale = SimConfig.DEFAULT_SCALE;
    private boolean lock = false;
    private int centerX = SimConfig.GRAPHIC_CENTER_X;
    private int centerY = SimConfig.GRAPHIC_CENTER_Y;
    private final SimulationManager manager;
    private Calculation calculation = new Calculation();
    private final PaintStrategy paintStrategy;

    public TrajectoryDisplay(SimulationManager manager) {
        super();
        this.manager = manager;
        paintStrategy = new PaintStrategy();
        setBackground(Color.BLACK);
    }

    /**
     * Draws the trajectory using an array of data from {@link SimulationManager} at t=0
     *
     * @see SimulationManager
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(lock) {
            CartesianCoordinate coordinate = manager.getRocket().getCartesianCoordinate();

            centerX = SimConfig.GRAPHIC_CENTER_X - (coordinate.getX() / scale);
            centerY = SimConfig.GRAPHIC_CENTER_Y - (coordinate.getY() / scale);
        }


        // painting celestial objects
        for (CelestialObject celestialObject : manager.getCelestialObjects().values()) {
            paintStrategy.paint(celestialObject, scale, centerX, centerY, g);
        }

        for (Stage stage : manager.getReleasedStages()) {
            paintStrategy.paint(stage, scale, centerX, centerY, g);
        }

        // paining trajectory
        for (int i = manager.getTrajectoryHistory().size() - 1; i > 0; i--) {

            CartesianCoordinate coordinate = manager.getTrajectoryHistory().get(i);

            paintStrategy.paint(coordinate, scale, centerX, centerY, g);
        }

        // paining trajectory
        int colorOpacity = 255;
        for (int i = manager.getCoordinateHistory().size() - 1; i - 1 > 0; i--) {

            CartesianCoordinate coordinate = manager.getCoordinateHistory().get(i);
            CartesianCoordinate nextCoordinate = manager.getCoordinateHistory().get(i);

            paintStrategy.paint(coordinate, nextCoordinate, colorOpacity, scale, centerX, centerY, g);
            if ((i % 2) == 0) {
                colorOpacity--;
            }
        }

        CelestialObject destination = manager.getCelestialObjects().get(manager.getMission().getDestinationName());
        if ((!destination.getName().equals("Earth"))) {
            int distance = (int) calculation.calculateDistance(manager.getRocket().getCartesianCoordinate(), destination.getCartesianCoordinate());
            distance -= destination.getRadius();
            if (distance > 36000000) {
                PolarCoordinate polarCoordinate = calculation.cartesianToPolar(destination.getCartesianCoordinate());
                polarCoordinate.setR(polarCoordinate.getR() - destination.getRadius() - manager.getMission().getOrbitAltitude());
                paintStrategy.paint(manager.getRocket(), calculation.polarToCartesian(polarCoordinate), scale, centerX, centerY, g);
            }
        }
        paintStrategy.paint(manager.getMission().getOrbitAltitude(), destination, scale, centerX, centerY, g);

        paintStrategy.paint(manager.getRocket(), scale, centerX, centerY, g);
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        if (SimConfig.MIN_SCALE <= scale) {
            this.scale = scale;
        }
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}