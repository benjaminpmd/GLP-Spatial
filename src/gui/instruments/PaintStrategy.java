package gui.instruments;

import data.coordinate.CartesianCoordinate;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class draws the trajectory of the rocket. Used in {@link gui.SimulationGUI}.
 * 
 * @see gui.SimulationGUI
 * @author Alice Mabille
 */
public class PaintStrategy {

	public void paint(CartesianCoordinate origin, CartesianCoordinate end, int opacity, Graphics g) {

		g.setColor(new Color(255, 255, 0, opacity));
		g.drawLine(origin.getX(), origin.getY(), end.getX(), end.getY());
	}
}