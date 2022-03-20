package gui;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.*;

import config.SimConfig;
import data.mission.Mission;
import exceptions.MissingPartException;
import exceptions.TooLowThrustException;
import gui.elements.SpaceCentersPanel;
import gui.elements.PayloadPanel;
import gui.elements.StagePanel;
import process.builders.*;
import process.management.FileManager;
import process.management.SimulationManager;
import process.repositories.CelestialObjectRepository;

/**
 * This is the first window. It allows the user to configure their rocket.
 * Leads to the Launch window {@link SimulationGUI}
 * 
 * @author Alice Mabille
 *
 */
public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final static Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);

	// since values from files are registered in the repositories, we init the builder before anything else
	private CelestialObjectBuilder celestialObjectBuilder = new CelestialObjectBuilder(SimConfig.CELESTIAL_OBJECTS_PATH);
	private SpaceCenterBuilder spaceCenterBuilder = new SpaceCenterBuilder(SimConfig.CENTERS_PATH);
	private MissionBuilder missionBuilder = new MissionBuilder(celestialObjectBuilder, spaceCenterBuilder);
	private SimulationBuilder simulationBuilder = new SimulationBuilder(celestialObjectBuilder, spaceCenterBuilder);

	private FileManager fileManager = new FileManager(celestialObjectBuilder, spaceCenterBuilder);

	private double rocketSchemaWeightY = 0.3;
	private double orbitPanelWeightY = 0.15;
	private double stagePanelWeightY = rocketSchemaWeightY + orbitPanelWeightY;
	private final int LEFT = 0;
	private final int MIDDLE = 1;
	private final int MIDDLE_RIGHT = 4;
	private final int RIGHT = 6;
	private final int TOP = 0;
	private final int MIDDLE_BOTTOM = 3;
	private final int BOTTOM = 4;
	private final Color MISSION_COLOR = new Color(60,61,64);
	public final static Color ROCKET_COLOR = new Color(71,72,75);
	private final Color TEXT_COLOR = new Color(240, 240, 240);
	private final Color TOP_BANNER_COLOR = new Color(89,90,93);

	private Container contentPane;

	private JPanel topBanner = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");

	private JMenuItem importSimulationItem = new JMenuItem("Import");
	private JMenuItem exportSimulationItem = new JMenuItem("Export");
	private JMenuItem exitItem = new JMenuItem("Exit");
	private JMenuItem fileHelpItem = new JMenuItem("Import/Export");
	private JMenuItem paramHelpItem = new JMenuItem("Simulation parameters");

	// stage panels
	private StagePanel stagePanel1 = new StagePanel(1, 32);
	private StagePanel stagePanel2 = new StagePanel(1,6);

	// payload panel
	private PayloadPanel payloadPanel;

	// space centers panel
	private SpaceCentersPanel spaceCentersPanel = new SpaceCentersPanel();

	private JTextField missionNameField = new JTextField("Name your mission here");
	private JTextPane errorTextPane = new JTextPane();
	private JTextField orbitField;
	private JComboBox<String> destinationMenu;

	private JFileChooser fileChooser = new JFileChooser("./");

	//the lowest and highest orbits possible
	private final int ORBIT_MIN = 0;
	private final int ORBIT_MAX = 60000;

	public MainGUI(String title) {
		super(title);
		init();
	}
	
	public void init() {
		contentPane = getContentPane();
		contentPane.setBackground(new Color(61, 62, 64));
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//top banner
		c.gridx = TOP;
		c.gridy = LEFT;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		topBanner.setBackground(TOP_BANNER_COLOR);
		JLabel topLabel = new JLabel("Setup your simulation");
		topLabel.setForeground(TEXT_COLOR);
		topBanner.add(topLabel);
		
		JButton launchButton = new JButton("Launch");
		launchButton.setBackground(new Color(0, 204, 0));
		launchButton.addActionListener(new LaunchAction());
		topBanner.add(launchButton);
		
		errorTextPane.setBackground(TOP_BANNER_COLOR);
		errorTextPane.setForeground(Color.YELLOW);
		
		contentPane.add(topBanner, c);

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


		//left panel : choose your launch site
		c.weightx = 0.2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.fill = GridBagConstraints.BOTH;
		spaceCentersPanel.setBackground(MISSION_COLOR);
		spaceCentersPanel.setElementsBackground(MISSION_COLOR);
		spaceCentersPanel.setElementsForeground(TEXT_COLOR);
		contentPane.add(spaceCentersPanel, c);
		
		
		//middle panel : rocket schema
		JPanel rocketPanel = new JPanel();
		c.gridx = MIDDLE;
		c.gridy = MIDDLE;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		rocketPanel.setBackground(ROCKET_COLOR);
		JLabel rocketLabel = new JLabel("Rocket schema");
		rocketPanel.add(rocketLabel);
		
		contentPane.add(rocketPanel, c);
		
		
		//below the middle panel : choose your destination
		JPanel orbitPanel = new JPanel();
		orbitPanel.setLayout(new BoxLayout(orbitPanel, BoxLayout.PAGE_AXIS));
		c.gridx = MIDDLE;
		c.gridy = MIDDLE_BOTTOM;
		c.gridwidth = 3;
		c.gridheight = 1;
		orbitPanel.setBackground(MISSION_COLOR);
		orbitPanel.setForeground(TEXT_COLOR);
		
		orbitPanel.add(Box.createRigidArea(new Dimension(300, 20)));
		
		JLabel orbitLabel = new JLabel("Orbit :");
		orbitLabel.setBackground(MISSION_COLOR);
		orbitLabel.setForeground(TEXT_COLOR);
		orbitPanel.add(orbitLabel);
		
		orbitField = new JTextField();
		orbitField.setMinimumSize(new Dimension(100,50));
		orbitField.setMaximumSize(new Dimension(400,50));
		orbitField.setBackground(ROCKET_COLOR);
		orbitField.setForeground(TEXT_COLOR);
		orbitPanel.add(orbitField);
		
		orbitPanel.add(Box.createRigidArea(new Dimension(300, 20)));

		//destination
		JLabel destinationLabel = new JLabel("Destination :");
		destinationLabel.setBackground(MISSION_COLOR);
		destinationLabel.setForeground(TEXT_COLOR);
		orbitPanel.add(destinationLabel);
		Collection<String> celestialObjectCollection = CelestialObjectRepository.getInstance().getKeys();
		String[] planetArray = new String[celestialObjectCollection.size()];
		celestialObjectCollection.toArray(planetArray);
		destinationMenu = new JComboBox<String>(planetArray);
		destinationMenu.setMinimumSize(new Dimension(100,50));
		destinationMenu.setMaximumSize(new Dimension(400,50));
		orbitPanel.add(destinationMenu);
		
		orbitPanel.add(Box.createRigidArea(new Dimension(300, 20)));
		
		orbitPanel.setBackground(MISSION_COLOR);
		orbitPanel.setForeground(TEXT_COLOR);
		contentPane.add(orbitPanel, c);
		
		
		//bottom panel : choose your payload
		payloadPanel = new PayloadPanel();
		c.gridx = MIDDLE_RIGHT;
		c.gridy = MIDDLE_BOTTOM;
		c.gridwidth = 3;
		c.gridheight = 1;
		payloadPanel.setBackground(ROCKET_COLOR);
		payloadPanel.setElementsBackground(ROCKET_COLOR);
		payloadPanel.setElementsForeground(TEXT_COLOR);
		contentPane.add(payloadPanel, c);
		
		
		//right panel : stage panel 1
		c.gridx = MIDDLE_RIGHT;
		c.gridy = MIDDLE;
		c.gridwidth = 1;
		c.gridheight = 2;
		stagePanel1.setBackground(ROCKET_COLOR);
		stagePanel1.setElementsBackground(new Color(89,90,93));
		stagePanel1.setElementsForeground(TEXT_COLOR);
		contentPane.add(stagePanel1, c);
		
		
		//right panel : stage panel 2
		c.gridx = RIGHT;
		c.gridy = MIDDLE;
		c.gridwidth = 1;
		c.gridheight = 2;
		stagePanel2.setBackground(ROCKET_COLOR);
		stagePanel2.setElementsBackground(new Color(89,90,93));
		stagePanel2.setElementsForeground(TEXT_COLOR);
		contentPane.add(stagePanel2, c);

				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(preferredSize);
		pack();
		setVisible(true);
	}

	private class LaunchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String destination = (String) destinationMenu.getSelectedItem();
			try {
				HashMap<String,String> stage1Values = stagePanel1.getValues();
				HashMap<String,String> stage2Values = stagePanel2.getValues();
				String payloadMass = payloadPanel.getMassInput();
				String missionName = missionNameField.getText();

				int orbit = Integer.valueOf(orbitField.getText());
				if (orbit < ORBIT_MIN) {
					errorTextPane.setText("Cannot create launch : the chosen orbit must be positive or null.");
				}
				else if (orbit > ORBIT_MAX) {
					errorTextPane.setText("Cannot create launch : the chosen orbit must be inferior to 60 000 km.");
				}
				else {
					String spaceCenterName = spaceCentersPanel.getSelectedCenter();
					SimulationManager manager = simulationBuilder.buildSimulation(stage1Values, stage2Values, payloadMass, missionName, spaceCenterName, destination, orbit);

					new SimulationGUI(getTitle(), manager);
					setVisible(false);
					dispose();
				}
			}
			catch (NumberFormatException ex) {
				System.err.println(ex);
				errorTextPane.setText("Please write an integer between 0 and 60 000 as the orbit height.");
			} catch (MissingPartException ex) {
				errorTextPane.setText(ex.getMessage());
			} catch (TooLowThrustException ex) {
				errorTextPane.setText(ex.getMessage());
			} catch (IllegalArgumentException ex) {
				errorTextPane.setText(ex.getMessage());
			}
			topBanner.add(errorTextPane);
		}
	}

	private class exportAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String destination = (String) destinationMenu.getSelectedItem();
			try {
				HashMap<String,String> stage1Values = stagePanel1.getValues();
				HashMap<String,String> stage2Values = stagePanel2.getValues();
				String payloadMass = payloadPanel.getMassInput();
				String missionName = missionNameField.getText();

				HashMap<String, String> payloadValues = new HashMap<>();
				payloadValues.put("mass", payloadMass);

				int orbit = Integer.valueOf(orbitField.getText());
				if (orbit < ORBIT_MIN) {
					errorTextPane.setText("Cannot create launch : the chosen orbit must be positive or null.");
				}
				else if (orbit > ORBIT_MAX) {
					errorTextPane.setText("Cannot create launch : the chosen orbit must be inferior to 60 000 km.");
				}
				else {
					String spaceCenterName = spaceCentersPanel.getSelectedCenter();
					Mission mission = missionBuilder.buildMission(missionName, spaceCenterName, destination, orbit);
					HashMap<String, String> missionValues = new HashMap<>();
					missionValues.put("missionName", missionName);
					missionValues.put("spaceCenterName", spaceCenterName);
					missionValues.put("destinationName", destination);
					missionValues.put("orbit", String.valueOf(orbit));

					fileChooser.setDialogTitle("Export simulation");

					int userSelection = fileChooser.showSaveDialog(contentPane);

					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File fileToSave = fileChooser.getSelectedFile();
						fileManager.exportSimulation(stage1Values, stage2Values, payloadValues, missionValues, fileToSave.getAbsolutePath());
					}
				}
			}
			catch (NumberFormatException ex) {
				System.err.println(ex);
				errorTextPane.setText("Please write an integer between 0 and 60 000 as the orbit height.");
			} catch (IllegalArgumentException ex) {
				errorTextPane.setText(ex.getMessage());
			}
			topBanner.add(errorTextPane);
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
					new SimulationGUI(getTitle(), manager);
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
}
