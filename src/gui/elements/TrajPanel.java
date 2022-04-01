package gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import engine.data.CartesianCoordinates;
import engine.data.Constants;
import engine.process.management.MissionManager;

/**
 * This class draws the trajectory of the rocket.
 * @author Alice Mabille
 *
 */
public class TrajPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final int LENGTH = 300;
	private final int WIDTH = 300;
	private final int FRAME_CENTER_X = LENGTH / 2;
	private final int FRAME_CENTER_Y = WIDTH / 2;

	private MissionManager missionManager = MissionManager.getInstance();
	private ArrayList<CartesianCoordinates> positionArray = new ArrayList<CartesianCoordinates>();
	private int x;
	private int pastx;
	private int y;
	private int pasty;
	private int startPointX = 0;
	private int startPointY = 0;
	private int realStartPointX;
	private int realStartPointY;
	private double zoom;
	private double max=0.00000001;
	private double min=0.00000001;





	public TrajPanel() {
		super();
		setBackground(Color.DARK_GRAY);
		update();
	}

	/**
	 * Draws the trajectory using an array of data from {@link MissionManager} at t=0
	 *
	 * @see MissionManager
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i<positionArray.size(); i++ ){
			CartesianCoordinates position = positionArray.get(i);
			int positionx = position.getX();
			int positiony = position.getY();
			int maxi = Math.max(Math.abs(positiony), Math.abs(positionx));
			int mini = Math.min(Math.abs(positiony), Math.abs(positionx));

			if(positionArray.size()>1) {
				if(maxi>max) {
					max = maxi;
				}
				if(mini<min) {
					min = mini;
				}
				zoom = LENGTH/max;

				//earthDiameter is zoomed in/out
				int earthDiameter = (int) (Constants.EARTH_RADIUS*zoom*2);

				x = zoomPositionX(positionx);
				y = zoomPositionY(positiony);

				g.setColor(Color.BLUE);
				//on peut voir qu'il y a un probleme avec les startPoint
				g.drawOval(startPointX, startPointY, earthDiameter, earthDiameter);
				g.fillOval(startPointX-earthDiameter, startPointY+(earthDiameter/2), earthDiameter, earthDiameter);
				g.setColor(Color.YELLOW);
				g.drawLine(pastx, pasty, x, y);
				
				pastx = x;
				pasty = y;
			}
			else {
				realStartPointX = positionArray.get(0).getX();
				realStartPointY = positionArray.get(0).getY();
				startPointX = zoomPositionX(realStartPointX);
				startPointY = zoomPositionY(realStartPointY);
				pastx = x = startPointX;
				pasty = y = startPointY;
			}
		}
	}

	/**
	 * Transforms the position as it was calculated in {@link MissionManager} into a position that fits into the TrajPanel.
	 * @param positionX the real position
	 * @return the zoomed in/out position
	 */
	private int zoomPositionX(int positionX) {
		return (int) (positionX*zoom);
	}

	/**
	 * Transforms the position as it was calculated in {@link MissionManager} into a position that fits into the TrajPanel.
	 * @param positionY the real position
	 * @return the zoomed in/out position
	 */
	private int zoomPositionY(int positionY) {
		return (int) (-positionY*zoom);
	}


	/**
	 * Gets the latest trajectory data from the class {@link MissionManager}
	 */
	public void update() {
		positionArray = missionManager.getSimulation().getTrajectories();

	}
}
