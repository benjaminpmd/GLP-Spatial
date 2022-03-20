package gui.elements;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.mission.CelestialObject;
import gui.instruments.PaintStrategy;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.management.SimulationManager;

import java.awt.*;

import javax.swing.JPanel;

/**
 * This class draws the trajectory of the rocket. Used in {@link gui.SimulationGUI}.
 * 
 * @see gui.SimulationGUI
 * @author Alice Mabille
 */
public class TrajectoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerUtility.getLogger(TrajectoryPanel.class, "html");

	private int scale = 21236;
	private SimulationManager manager;
	private PaintStrategy paintStrategy;
	private double max=0.00000001;
	private double min=0.00000001;

	public TrajectoryPanel(SimulationManager manager) {
		super();
		this.manager = manager;
		paintStrategy = new PaintStrategy();
		setBackground(Color.DARK_GRAY);
	}
	/**
	 * Draws the trajectory using an array of data from {@link SimulationManager} at t=0
	 *
	 * @see SimulationManager
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		logger.trace("painting elements");

		// painting celestial objects
		for (CelestialObject celestialObject : manager.getCelestialObjects().values()) {
			paintStrategy.paint(celestialObject, scale, g);
		}

		// paining trajectory
		int colorOpacity = 255;
		for (int i = manager.getCoordinateHistory().size()-1; i - 1 > 0; i--) {

			CartesianCoordinate coordinate = manager.getCoordinateHistory().get(i);
			CartesianCoordinate nextCoordinate = manager.getCoordinateHistory().get(i - 1);

			paintStrategy.paint(coordinate, nextCoordinate, colorOpacity, scale, g);
			logger.trace("painting : " + coordinate + nextCoordinate);
			if ((i % 2) == 0) {
				colorOpacity --;
			}
		}

		paintStrategy.paint(manager.getRocket(), scale, g);
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
}