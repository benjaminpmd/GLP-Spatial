package gui.elements;

import data.coordinate.CartesianCoordinate;
import gui.instruments.PaintStrategy;
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
	
	private final int LENGTH = 300;
	private final int WIDTH = 300;
	private final int FRAME_CENTER_X = LENGTH/2;
	private final int FRAME_CENTER_Y = WIDTH/2;
	private double ratio = 1;
	
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

	private void calculateRatio() {
		CartesianCoordinate rocketCoordinate = manager.getRocket().getCartesianCoordinate();
		if (rocketCoordinate.getX() > rocketCoordinate.getY()) {
			ratio = (int) (rocketCoordinate.getX() / LENGTH);
		}
		else {
			ratio = (int) (rocketCoordinate.getY() / WIDTH);
		}
	}

	/**
	 * Draws the trajectory using an array of data from {@link SimulationManager} at t=0
	 *
	 * @see SimulationManager
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		calculateRatio();

		// paining trajectory
		int colorOpacity = 255;
		for (int i = manager.getCoordinateHistory().size(); i - 1 > 0; i--) {
			CartesianCoordinate coordinate = manager.getCoordinateHistory().get(i);
			CartesianCoordinate nextCoordinate = manager.getCoordinateHistory().get(i - 1);

			CartesianCoordinate origin = new CartesianCoordinate((int) (coordinate.getX() * ratio), (int) (coordinate.getY() * ratio));
			CartesianCoordinate end = new CartesianCoordinate((int) (nextCoordinate.getX() * ratio), (int) (nextCoordinate.getY() * ratio));

			paintStrategy.paint(origin, end, colorOpacity, g);
			colorOpacity -= 2;
		}

		// painting celestial objects

	}
}