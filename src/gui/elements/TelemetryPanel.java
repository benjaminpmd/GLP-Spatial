package gui.elements;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This class is only responsible for telemetry result printing.
 * 
 * There is no algorithmic processing in this class.
 * 
 * Used in @see SimulationGUI
 * 
 * @author Lucas L
 *
 */
public class TelemetryPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public TelemetryPanel() {
		
		JPanel counterPanel = new JPanel();
		counterPanel.setLayout(new GridLayout(1,2));
		
		JLabel velocityLabel = new JLabel("Velocity (km/h)");
		counterPanel.add(velocityLabel);
		
		JLabel altitudeLabel = new JLabel("Altitude (km)");
		counterPanel.add(altitudeLabel);
	}
}
