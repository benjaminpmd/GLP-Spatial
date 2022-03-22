package gui.instruments;
import data.coordinate.CartesianCoordinate;
import data.mission.CelestialObject;
import data.rocket.Rocket;
import data.rocket.Stage;

import java.awt.*;
import java.awt.Color;

/**
 * This class draws the trajectory of the rocket. Used in {@link gui.SimulationGUI}.
 * 
 * @see gui.SimulationGUI
 * @author Alice Mabille
 */
public class PaintStrategy {

	public void paint(CartesianCoordinate coordinate, int scale, int centerX, int centerY, Graphics g) {

		g.setColor(Color.YELLOW);

		int x = centerX + (coordinate.getX() / scale);
		int y = centerY + (coordinate.getY() / scale);

		g.fillOval(x, y, 2, 2);
	}

	public void paint(CartesianCoordinate origin, CartesianCoordinate end, int opacity, int scale, int centerX, int centerY, Graphics g) {

		g.setColor(new Color(255, 255, 0, opacity));

		int originX = centerX + (origin.getX() / scale);
		int originY = centerY + (origin.getY() / scale);
		int endX = centerX + (end.getX() / scale);
		int endY = centerY + (end.getY() / scale);

		g.drawLine(originX, originY, endX, endY);
	}

	public void paint(CelestialObject celestialObject, int scale, int centerX, int centerY, Graphics g) {
		g.setColor(Color.BLUE);

		CartesianCoordinate coordinate = celestialObject.getCartesianCoordinate();

		int x = centerX + (coordinate.getX() / scale) - (celestialObject.getRadius() / scale);
		int y = centerY + (coordinate.getY() / scale) - (celestialObject.getRadius() / scale);

		int diameter = ((celestialObject.getRadius() * 2) / scale);

		g.fillOval(x, y, diameter, diameter);
	}

	public void paint(Rocket rocket, int scale, int centerX, int centerY, Graphics g) {
		g.setColor(Color.RED);
		CartesianCoordinate coordinate = rocket.getCartesianCoordinate();
		int x = centerX + (coordinate.getX() / scale) - 3;
		int y = centerY + (coordinate.getY() / scale) - 3;
		g.fillOval(x, y, 6, 6);
	}

	public void paint(Stage stage, int scale, int centerX, int centerY, Graphics g) {
		g.setColor(Color.GREEN );
		CartesianCoordinate coordinate = stage.getCartesianCoordinate();
		int x = centerX + (coordinate.getX() / scale) - 3;
		int y = centerY + (coordinate.getY() / scale) - 3;
		g.fillOval(x, y, 6, 6);
	}
}