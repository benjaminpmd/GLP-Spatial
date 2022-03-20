package gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

import config.SimConfig;
import exceptions.MissingPartException;
import exceptions.TooLowThrustException;
import gui.elements.GraphPanel;
import gui.elements.TrajectoryPanel;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.management.FileManager;
import process.management.SimulationManager;

/**
 * This is the third window. It shows the trajectory and telemetry of the rocket.
 * 
 * @author Alice Mabille
 *
 */
public class SimulationGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerUtility.getLogger(SimulationGUI.class, "html");
	
	private SimulationManager manager;
	private FileManager fileManager;

	private Container contentPane;

	private GraphPanel speedGraph;
	private GraphPanel accelerationGraph;
	//private GraphPanel altitudeGraph = new GraphPanel("Altitude");

	private JPanel graphPanel = new JPanel();
	Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);


	private JPanel topBanner = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");

	private JMenuItem importSimulationItem = new JMenuItem("Import");
	private JMenuItem exportSimulationItem = new JMenuItem("Export");
	private JMenuItem exitItem = new JMenuItem("Exit");
	private JMenuItem fileHelpItem = new JMenuItem("Import/Export");
	private JMenuItem paramHelpItem = new JMenuItem("Simulation parameters");

	private JFileChooser fileChooser = new JFileChooser("./");
	
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
	
	private JButton startButton = new JButton("Play");
	private JButton delayButton = new JButton("Delay start");
	private JButton speedupButton = new JButton("Speed up");
	private JButton slowdownButton = new JButton("Slow down");
	private JButton zoomInButton = new JButton("Zoom in");
	private JButton zoomOutButton = new JButton("Zoom out");

	private TrajectoryPanel trajectoryPanel;
	private GridBagConstraints c = new GridBagConstraints();
	
	/**
	 * Initial status of the start button.
	 */
	private boolean stop = true;
	private SimulationGUI instance = this;
	private int simulationSpeed = SimConfig.SIMULATION_SPEED;


	public SimulationGUI(String title, SimulationManager manager, FileManager fileManager) {
		super(title);
		this.manager = manager;
		this.fileManager = fileManager;
		speedGraph = new GraphPanel("Speed", manager.getTelemetry());
		accelerationGraph = new GraphPanel("Acceleration", manager.getTelemetry());
		trajectoryPanel = new TrajectoryPanel(manager);
		init();
	}
	
	private void init() {
		contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBackground(Color.DARK_GRAY);
		c.fill = GridBagConstraints.BOTH;

		// top menu
		exportSimulationItem.addActionListener(new exportAction());
		importSimulationItem.addActionListener(new importAction());
		exitItem.addActionListener(new exitAction());
		fileMenu.add(importSimulationItem);
		fileMenu.add(exportSimulationItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		
		
		//top banner
		JPanel topBanner = new JPanel();
		c.gridx = MIDDLE;
		c.gridy = TOP;
		c.gridwidth = 5;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		
		JLabel topLabel = new JLabel(manager.getMission().getName());
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

		contentPane.add(trajectoryPanel, c);
		
		
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
		c.gridheight = 4;
		startButton.addActionListener(new StartStopAction());
		speedupButton.addActionListener(new IncreaseSpeedAction());
		slowdownButton.addActionListener(new DecreaseSpeedAction());
		zoomInButton.addActionListener(new ZoomInAction());
		zoomOutButton.addActionListener(new ZoomOutAction());
		rightPanel.add(delayButton);
		rightPanel.add(startButton);
		rightPanel.add(speedupButton);
		rightPanel.add(slowdownButton);
		rightPanel.add(zoomInButton);
		rightPanel.add(zoomOutButton);
		
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
		manager.next();
		trajectoryPanel.repaint();
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
			trajectoryPanel.repaint();

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

	private class exportAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}

	private class importAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			fileChooser.setDialogTitle("Export simulation");

			int userSelection = fileChooser.showOpenDialog(contentPane);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToOpen = fileChooser.getSelectedFile();
				try {

					SimulationManager manager = fileManager.importSimulation(fileToOpen.getAbsolutePath());
					new SimulationGUI(getTitle(), manager, fileManager);
					setVisible(false);
					dispose();
				} catch (MissingPartException ex) {
					ex.printStackTrace();
				} catch (TooLowThrustException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private class exitAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
		}
	}

	private class IncreaseSpeedAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			simulationSpeed /= 2;
		}
	}

	private class DecreaseSpeedAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			simulationSpeed *= 2;
		}
	}

	private class ZoomInAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			trajectoryPanel.setScale(trajectoryPanel.getScale() / 2);
		}
	}

	private class ZoomOutAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			trajectoryPanel.setScale(trajectoryPanel.getScale() * 2);
		}
	}
}
