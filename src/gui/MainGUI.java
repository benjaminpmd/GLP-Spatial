package gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JOptionPane;

import config.SimConfig;
import exceptions.BadValueException;
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
	private JPanel infoPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");

	private JMenuItem newSimulationItem = new JMenuItem("New");
	private JMenuItem importSimulationItem = new JMenuItem("Import");
	private JMenuItem exportSimulationItem = new JMenuItem("Export");
	private JMenuItem exitItem = new JMenuItem("Exit");

	private JMenuItem simulationHelpItem = new JMenuItem("Open Help...");

	// stage panels
	private StagePanel stagePanel1 = new StagePanel(1, 32);
	private StagePanel stagePanel2 = new StagePanel(2,6);

	// payload panel
	private PayloadPanel payloadPanel;

	// space centers panel
	private SpaceCentersPanel spaceCentersPanel = new SpaceCentersPanel();

	private JTextField missionNameField = new JTextField("");
	private JTextField missionDescriptionField = new JTextField("");
	private JTextPane errorTextPane = new JTextPane();
	private JTextField orbitField;
	private JComboBox<String> destinationMenu;

	private JFileChooser fileChooser = new JFileChooser("./");

	//the lowest and highest orbits possible
	private final int ORBIT_MIN = 200;
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
		newSimulationItem.addActionListener(new NewSimulationAction());
		exportSimulationItem.addActionListener(new exportAction());
		importSimulationItem.addActionListener(new importAction());
		exitItem.addActionListener(new exitAction());
		fileMenu.add(newSimulationItem);
		fileMenu.add(importSimulationItem);
		fileMenu.add(exportSimulationItem);
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);

		helpMenu.add(simulationHelpItem);
		simulationHelpItem.addActionListener(new HelpAction());

		menuBar.add(helpMenu);
		setJMenuBar(menuBar);


		//left panel : choose your launch site
		c.weightx = 0.2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		spaceCentersPanel.setBackground(MISSION_COLOR);
		spaceCentersPanel.setElementsBackground(MISSION_COLOR);
		spaceCentersPanel.setElementsForeground(TEXT_COLOR);
		contentPane.add(spaceCentersPanel, c);


		//bottom-left panel : mission infos
		c.weightx = 0.2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = MIDDLE_BOTTOM;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		JLabel missionNameLabel = new JLabel("Mission name :");
		JLabel missionDescriptionLabel = new JLabel("Description :");

		missionNameLabel.setForeground(TEXT_COLOR);
		missionDescriptionLabel.setForeground(TEXT_COLOR);
		missionNameField.setForeground(TEXT_COLOR);
		missionDescriptionField.setForeground(TEXT_COLOR);

		missionNameField.setBackground(MISSION_COLOR);
		missionDescriptionField.setBackground(MISSION_COLOR);
		infoPanel.setBackground(MISSION_COLOR);

		infoPanel.add(missionNameLabel);
		infoPanel.add(missionNameField);
		infoPanel.add(missionDescriptionLabel);
		infoPanel.add(missionDescriptionField);

		contentPane.add(infoPanel, c);

		//middle panel : rocket schema
		JPanel rocketPanel = new JPanel();
		c.gridx = MIDDLE;
		c.gridy = MIDDLE;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		rocketPanel.setLayout(new BoxLayout(rocketPanel, BoxLayout.PAGE_AXIS));
		rocketPanel.setBackground(ROCKET_COLOR);
		JLabel rocketLabel = new JLabel("Rocket schema");
		rocketLabel.setForeground(TEXT_COLOR);
		rocketPanel.add(rocketLabel);
		try {
			BufferedImage rocketSchema = ImageIO.read(new File(SimConfig.IMAGE_PATH + "RocketSchema.png"));
			Image scaledRocketSchema = rocketSchema.getScaledInstance(318, 383, Image.SCALE_SMOOTH);
			JLabel rocketSchemaLabel = new JLabel(new ImageIcon(scaledRocketSchema));
			rocketPanel.add(rocketSchemaLabel);
		} catch (IOException e) {
			JLabel errorLabel = new JLabel("\nWe are not able to show you the schema of the rocket :(");
			errorLabel.setForeground(TEXT_COLOR);
			rocketPanel.add(errorLabel);

			rocketPanel.add(errorLabel);
		}

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
		stagePanel1.setElementsBackground(ROCKET_COLOR);
		stagePanel1.setElementsForeground(TEXT_COLOR);
		contentPane.add(stagePanel1, c);


		//right panel : stage panel 2
		c.gridx = RIGHT;
		c.gridy = MIDDLE;
		c.gridwidth = 1;
		c.gridheight = 2;
		stagePanel2.setBackground(ROCKET_COLOR);
		stagePanel2.setElementsBackground(ROCKET_COLOR);
		stagePanel2.setElementsForeground(TEXT_COLOR);
		contentPane.add(stagePanel2, c);


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(preferredSize);
		pack();
		setVisible(true);
	}

	private SimulationManager createManager() throws BadValueException, IllegalArgumentException {

		// payload data
		String payloadMass = payloadPanel.getMassInput();
		if (payloadMass.equals("")) {
			throw new BadValueException("payload mass", "empty");
		}

		// orbit data
		if (orbitField.getText().equals("")) {
			throw new IllegalArgumentException("The chosen orbit must be between " + ORBIT_MIN + " and " + ORBIT_MAX + " km.");
		}
		int orbit = Integer.valueOf(orbitField.getText());
		if (orbit < ORBIT_MIN) {
			throw new IllegalArgumentException("The chosen orbit must be higher than " + ORBIT_MIN + "km.");
		}
		else if (orbit > ORBIT_MAX) {
			throw new IllegalArgumentException("The chosen orbit must be inferior to " + ORBIT_MAX + " km.");
		}

		// stages data
		HashMap<String,String> firstStageParam = stagePanel1.getValues();
		HashMap<String,String> secondStageParam = stagePanel2.getValues();
		for (Entry param : firstStageParam.entrySet()) {
			if (param.getValue().equals("")) {
				throw new BadValueException(param.getKey() + " on first stage", "empty");
			}
		}
		for (Entry param : secondStageParam.entrySet()) {
			if (param.getValue().equals("")) {
				throw new BadValueException(param.getKey() + " on second stage", "empty");
			}
		}


		if (spaceCentersPanel.getSelectedCenter() == null) {
			throw new IllegalArgumentException("You must choose a space center");
		}

		// mission data
		HashMap<String, String> missionParam = new HashMap<>();
		missionParam.put("name", missionNameField.getText());
		missionParam.put("description", missionDescriptionField.getText());
		missionParam.put("destinationName", (String) destinationMenu.getSelectedItem());
		missionParam.put("spaceCenterName", spaceCentersPanel.getSelectedCenter());
		missionParam.put("orbitAltitude", String.valueOf(orbit));

		return simulationBuilder.buildSimulation(firstStageParam, secondStageParam, payloadMass, missionParam);
	}

	private class LaunchAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// try to build mission and rocket
			try {
				SimulationManager manager = createManager();
				new SimulationGUI(getTitle(), manager, fileManager);
				dispose();
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
	}

	private class exportAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// try to build mission and rocket
			try {
				SimulationManager manager = createManager();
				fileChooser.setDialogTitle("Export simulation");
				int userSelection = fileChooser.showSaveDialog(contentPane);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					fileManager.exportSimulation(manager, fileToSave.getAbsolutePath());
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
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
				} catch (Exception ex) {
					// TODO: add logger here
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

	private class NewSimulationAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new MainGUI("Space Simulation");
			setVisible(false);
			dispose();
		}
	}

	private class HelpAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new HelpGUI("Help");
		}
	}

}
