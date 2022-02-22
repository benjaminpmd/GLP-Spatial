package gui.elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import engine.data.CartesianCoordinates;
import engine.process.management.MissionManager;

/**
 * This class draws the trajectory of the rocket.
 * @author Alice Mabille
 *
 */
public class TrajectoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final int LENGTH = 400;
	private final int WIDTH = 400;
	private final int FRAME_CENTER_X = WIDTH / 2;
	private final int FRAME_CENTER_Y = WIDTH /2;
	
	private MissionManager missionManager = MissionManager.getInstance();
	private int ratio;
	int x = 0;

	public TrajectoryPanel() {
		ratio = (int) (((100000) + missionManager.getRocketAltitude()) / WIDTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		printPlanet(g2);
		printTrajectory(g2);
		System.out.println((int) missionManager.getRocketAltitude() / ratio);
	}
	
	public void printPlanet(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.drawOval(FRAME_CENTER_X+50, FRAME_CENTER_Y+50, (WIDTH-50) / ratio, (WIDTH-50) /ratio);
	}

	public void printTrajectory(Graphics2D g2) {
		System.out.println(x);
		g2.setColor(Color.BLACK);
		List altitudes = missionManager.getSimulation().getAltitudes();
		for (int i = 0; i < altitudes.size()-2; i++) {
			g2.drawLine(20, (int) altitudes.get(i), 5, (int) altitudes.get(i+1));
		}
		x++;
	}

}
