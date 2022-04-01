package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.SimConfig;
import engine.process.configuration.LaunchConfigurator;
import engine.process.management.MissionManager;
import gui.elements.GraphPanel;
import gui.elements.TrajPanel;

/**
 * This is the third window. It shows the trajectory and telemetry of the rocket.
 *
 * @author Alice Mabille
 *
 */
public class LaunchGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private MissionManager missionManager = MissionManager.getInstance();

	private TrajPanel trajPanel = new TrajPanel();
	private GraphPanel speedGraph = new GraphPanel("Speed");
	private GraphPanel accelerationGraph = new GraphPanel("Acceleration");
	//private GraphPanel altitudeGraph = new GraphPanel("Altitude");
	private JPanel graphPanel = new JPanel();
	private Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);

	/*total width = 7
	 *total height = 6
	 */
	private final int LEFT = 0;
	private final int MIDDLE = 2;
	private final int MIDDLE_RIGHT = 4;
	private final int RIGHT = 6;
	private final int TOP = 0;
	private final int MIDDLE_BOTTOM = 4;
	private final int BOTTOM = 5;

	private int simulationSpeed = 1000;

	private JButton startButton = new JButton("Play");
	private JButton delayButton = new JButton("Delay start");
	private JButton speedupButton = new JButton("Speed up");
	private JButton slowdownButton = new JButton("Slow down");

	private GridBagConstraints c = new GridBagConstraints();

	private LaunchGUI instance = this;

	/**
	 * Initial status of the start button.
	 */
	private boolean stop = true;


	public LaunchGUI(String title) {
		super(title);
		init();
	}

	private void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBackground(Color.DARK_GRAY);
		c.fill = GridBagConstraints.BOTH;


		//top banner
		JPanel topBanner = new JPanel();
		c.gridx = MIDDLE;
		c.gridy = TOP;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;

		JLabel topLabel = new JLabel(missionManager.getMission().getName());//TODO
		topBanner.add(topLabel);

		contentPane.add(topBanner, c);

		//left panel : graphs
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.PAGE_AXIS));
		c.weightx = 0.2;
		c.weighty = 1;
		c.gridx = LEFT;
		c.gridy = TOP;
		c.gridwidth = 2;
		c.gridheight = 6;
		graphPanel.add(speedGraph);
		graphPanel.add(accelerationGraph);
		//graphPanel.add(altitudeGraph);

		contentPane.add(graphPanel, c);

		//middle panel : trajectory
		c.weightx = 0.6;
		c.weighty = 0.5;
		c.gridx = MIDDLE;
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridheight = 3;

		contentPane.add(trajPanel, c);


		//below the middle panel : flight parameters dashboard
		JPanel boardPanel = new JPanel();
		c.weighty = 0.1;
		c.weightx = 0.5;
		c.gridx = MIDDLE;
		c.gridy = MIDDLE_BOTTOM;
		c.gridwidth = 2;
		c.gridheight = 1;

		JLabel velocLabel = new JLabel("Velocity");
		JLabel altiLabel = new JLabel("Altitude");
		boardPanel.add(velocLabel);
		boardPanel.add(altiLabel);

		contentPane.add(boardPanel, c);


		//below the middle panel : rocket schema
		JPanel rocketPanel = new JPanel();
		c.gridx = MIDDLE_RIGHT;
		c.gridy = MIDDLE_BOTTOM;
		c.gridwidth = 2;
		c.gridheight = 1;

		JLabel rocketLabel = new JLabel("Rocket Schema");
		rocketPanel.add(rocketLabel);

		contentPane.add(rocketPanel, c);



		//right panel : play, pause buttons
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		c.weightx = 0.2;
		c.gridx = RIGHT;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 5;
		//rightPanel.add(delayButton);
		rightPanel.add(startButton);
		startButton.addActionListener(new StartStopAction());
		rightPanel.add(speedupButton);
		speedupButton.addActionListener(new IncreaseSpeedAction());
		rightPanel.add(slowdownButton);
		slowdownButton.addActionListener(new DecreaseSpeedAction());

		contentPane.add(rightPanel, c);


		//bottom panel
		JPanel launchPanel = new JPanel();
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridx = MIDDLE;
		c.gridy = BOTTOM;
		c.gridwidth = 6;
		c.gridheight = 1;

		contentPane.add(launchPanel, c);



		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(preferredSize);
		pack();
		setVisible(true);
		setResizable(false);
	}

	private void updateValues() {
		missionManager.next();
		trajPanel.update();
		speedGraph.update();
		accelerationGraph.update();
		//altitudeGraph.update();

	}

	@Override
	public void run() {
		while (!stop) {
			try {
				Thread.sleep(simulationSpeed);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			speedGraph.repaint();
			accelerationGraph.repaint();
			//altitudeGraph.repaint();
			trajPanel.repaint();

			// Ensure that the simulation is not stopped during the iteration.
			if (!stop) {
				updateValues();
			}
		}
	}

	private class StartStopAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!stop) {
				stop = true;
				startButton.setText(" Play ");
			} else {
				stop = false;
				startButton.setText(" Pause ");
				Thread simThread = new Thread(instance);
				simThread.start();
			}
		}
	}

	private class IncreaseSpeedAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			simulationSpeed = (int) simulationSpeed / 2;
		}
	}

	private class DecreaseSpeedAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			simulationSpeed *= 2;
		}
	}
}