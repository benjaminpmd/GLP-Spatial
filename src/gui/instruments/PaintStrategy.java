package gui.instruments;

import config.Constants;
import config.SimConfig;
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

	public void paint(CartesianCoordinate origin, CartesianCoordinate end, int opacity, int scale, Graphics g) {

		g.setColor(new Color(255, 255, 0, opacity));

		int originX = (int) SimConfig.GRAPHIC_CENTER_X + (origin.getX() / scale);
		int originY = (int) SimConfig.GRAPHIC_CENTER_Y + (origin.getY() / scale);
		int endX = (int) SimConfig.GRAPHIC_CENTER_X + (end.getX() / scale);
		int endY = (int) SimConfig.GRAPHIC_CENTER_Y + (end.getY() / scale);

		g.drawLine(originX, originY, endX, endY);
	}

	public void paint(CelestialObject celestialObject, int scale, Graphics g) {
		g.setColor(Color.BLUE);

		CartesianCoordinate coordinate = celestialObject.getCartesianCoordinate();

		int x = (int) SimConfig.GRAPHIC_CENTER_X + (coordinate.getX() / scale) - (celestialObject.getRadius() / scale);
		int y = (int) SimConfig.GRAPHIC_CENTER_Y + (coordinate.getY() / scale) - (celestialObject.getRadius() / scale);

		int diameter = (int) ((celestialObject.getRadius() * 2) / scale);

		g.fillOval(x, y, diameter, diameter);
	}

	public void paint(Rocket rocket, int scale, Graphics g) {
		g.setColor(Color.RED);
		CartesianCoordinate coordinate = rocket.getCartesianCoordinate();
		int x = (int) SimConfig.GRAPHIC_CENTER_X + (coordinate.getX() / scale) - 3;
		int y = (int) SimConfig.GRAPHIC_CENTER_Y + (coordinate.getY() / scale) - 3;
		g.fillOval(x, y, 6, 6);
	}
}