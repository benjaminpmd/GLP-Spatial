package gui;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.SimConfig;
import data.mission.Mission;
import data.mission.Target;
import engine.process.factories.RocketFactory;
import engine.process.management.MissionManager;
import engine.process.repositories.CenterRepository;
import gui.elements.LaunchSitesPanel;
import gui.elements.PayloadPanel;
import gui.elements.StagePanel;

/**
 * This is the second window. It allows the user to configure their rocket.
 * Leads to the Launch window @see LaunchGUI
 * 
 * @author Alice Mabille
 *
 */
public class PreLaunchGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private final static Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);

	private RocketFactory rocketFactory = new RocketFactory();
	private MissionManager missionManager = MissionManager.getInstance();
	
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

	public PreLaunchGUI(String title) {
		super(title);
		init();
	}
	
	public void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//top banner
		JPanel topBanner = new JPanel();
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = TOP;
		c.gridy = LEFT;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel topLabel = new JLabel("Setup your mission");
		topBanner.add(topLabel);
		
		JCheckBox beginModeButton = new JCheckBox("Beginner mode");
		beginModeButton.addItemListener(new BeginnerModeAction());
		topBanner.add(beginModeButton);
		
		contentPane.add(topBanner, c);
		
		
		//left panel : choose your launch site
		LaunchSitesPanel launchSitesPanel = new LaunchSitesPanel();
		c.weightx = 0.2;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.fill = GridBagConstraints.VERTICAL;
		
		contentPane.add(launchSitesPanel, c);
		
		
		//middle panel : rocket schema
		JPanel rocketPanel = new JPanel();
		c.weightx = 0.3;
		c.weighty = rocketSchemaWeightY;
		c.gridx = MIDDLE;
		c.gridy = MIDDLE;
		c.gridwidth = 2;
		c.gridheight = 2;
		
		JLabel rocketLabel = new JLabel("Rocket schema");
		rocketPanel.add(rocketLabel);
		
		contentPane.add(rocketPanel, c);
		
		
		//below the middle panel : choose your destination
		JPanel orbitPanel = new JPanel();
		c.weightx = 0.5;
		c.weighty = orbitPanelWeightY;
		c.gridx = MIDDLE;
		c.gridy = MIDDLE_BOTTOM;
		c.gridwidth = 2;
		c.gridheight = 1;
				
		JLabel orbitLabel = new JLabel("Orbit :");
		orbitPanel.add(orbitLabel);
				
		contentPane.add(orbitPanel, c);
		
		
		//bottom panel : choose your payload
		PayloadPanel payloadPanel = new PayloadPanel(rocketFactory);
		c.weightx = 0.3;
		c.weighty = 0.15;
		c.gridx = MIDDLE;
		c.gridy = BOTTOM;
		c.gridwidth = 2;
		c.gridheight = 1;
		contentPane.add(payloadPanel, c);
		
		
		//rigth panel : stage panel 1
		StagePanel stagePanel1 = new StagePanel(1, 32, rocketFactory);
		c.weightx = 0.25;
		c.weighty = stagePanelWeightY;
		c.gridx = MIDDLE_RIGHT;
		c.gridy = MIDDLE;
		c.gridwidth = 1;
		c.gridheight = 2;
		contentPane.add(stagePanel1, c);
		
		
		//right panel : stage panel 2
		StagePanel stagePanel2 = new StagePanel(2, 6, rocketFactory);
		c.weightx = 0.25;
		c.weighty = stagePanelWeightY;
		c.gridx = RIGHT;
		c.gridy = MIDDLE;
		c.gridwidth = 1;
		c.gridheight = 2;
		contentPane.add(stagePanel2, c);
		
		//bottom right panel: save and run buttons
		JPanel launchPanel = new JPanel();
		c.weightx = 0.3;
		c.gridx = RIGHT;
		c.gridy = BOTTOM;
		c.gridwidth = 1;
		c.gridheight = 1;
		
		launchPanel.setLayout(new BoxLayout(launchPanel, BoxLayout.PAGE_AXIS));
		
		JButton saveButton = new JButton("Save");
		launchPanel.add(saveButton);
		JButton launchButton = new JButton("Launch");
		launchButton.addActionListener(new LaunchListener());
		launchPanel.add(launchButton);
		
		contentPane.add(launchPanel, c);

				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(preferredSize);
		pack();
		setVisible(true);
	}
	
	
	private class BeginnerModeAction implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				SimConfig.beginnerMode = false;
			}
			else SimConfig.beginnerMode = true;
		}
	}

	private class LaunchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			rocketFactory.getBuiltRocket().setCoordinates(CenterRepository.getInstance().getCenter("Centre Spatial Guyanais").getCoordinates());
			Mission mission = new Mission("Test Mission", "A mission for tests", CenterRepository.getInstance().getCenter("Centre Spatial Guyanais"), new Target(200), rocketFactory.getBuiltRocket());
			missionManager.setMission(mission);
			new LaunchGUI(getTitle());
			setVisible(false);
			dispose();
		}

	}
	public void run() {

	}
}
