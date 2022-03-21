package gui.instruments;
import data.coordinate.CartesianCoordinate;
import data.mission.CelestialObject;
import data.rocket.Rocket;

import java.awt.*;
import java.awt.Color;

/**
 * This class draws the trajectory of the rocket. Used in {@link gui.SimulationGUI}.
 * 
 * @see gui.SimulationGUI
 * @author Alice Mabille
 */
public class PaintStrategy {

	public void paint(CartesianCoordinate origin, CartesianCoordinate end, int opacity, int scale, int centerX, int centerY, Graphics g) {

		g.setColor(new Color(255, 255, 0, opacity));

		int originX = (int) centerX + (origin.getX() / scale);
		int originY = (int) centerY + (origin.getY() / scale);
		int endX = (int) centerX + (end.getX() / scale);
		int endY = (int) centerY + (end.getY() / scale);

		g.drawLine(originX, originY, endX, endY);
	}

	public void paint(CelestialObject celestialObject, int scale, int centerX, int centerY, Graphics g) {
		g.setColor(Color.BLUE);

		CartesianCoordinate coordinate = celestialObject.getCartesianCoordinate();

		int x = (int) centerX + (coordinate.getX() / scale) - (celestialObject.getRadius() / scale);
		int y = (int) centerY + (coordinate.getY() / scale) - (celestialObject.getRadius() / scale);

		int diameter = (int) ((celestialObject.getRadius() * 2) / scale);

		g.fillOval(x, y, diameter, diameter);
	}

	public void paint(Rocket rocket, int scale, int centerX, int centerY, Graphics g) {
		g.setColor(Color.RED);
		CartesianCoordinate coordinate = rocket.getCartesianCoordinate();
		int x = (int) centerX + (coordinate.getX() / scale) - 3;
		int y = (int) centerY + (coordinate.getY() / scale) - 3;
		g.fillOval(x, y, 6, 6);
	}
}