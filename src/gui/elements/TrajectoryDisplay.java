package gui.elements;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.mission.CelestialObject;
import gui.instruments.PaintStrategy;
import log.LoggerUtility;
import org.apache.log4j.Logger;
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

    private int scale = 21236;
    private boolean lock = false;
    private int centerX = SimConfig.GRAPHIC_CENTER_X;
    private int centerY = SimConfig.GRAPHIC_CENTER_Y;
    private final SimulationManager manager;
    private final PaintStrategy paintStrategy;
    private final double max = 0.00000001;
    private final double min = 0.00000001;

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

        // paining trajectory
        int colorOpacity = 255;
        for (int i = manager.getCoordinateHistory().size() - 1; i - 1 > 0; i--) {

            CartesianCoordinate coordinate = manager.getCoordinateHistory().get(i);
            CartesianCoordinate nextCoordinate = manager.getCoordinateHistory().get(i - 1);

            paintStrategy.paint(coordinate, nextCoordinate, colorOpacity, scale, centerX, centerY, g);
            if ((i % 2) == 0) {
                colorOpacity--;
            }
        }

        paintStrategy.paint(manager.getRocket(), scale, centerX, centerY, g);
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
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