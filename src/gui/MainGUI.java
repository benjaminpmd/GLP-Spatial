package gui;

import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.SimConfig;
import engine.process.configuration.LaunchConfigurator;

/**
 * This is the first window : the main menu of the simulation.
 * Leads to the PreLaunch window @see PreLaunchGUI
 * 
 * @author 
 *
 */
public class MainGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private LaunchConfigurator launchConfigurator = new LaunchConfigurator();

	//private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);

	public MainGUI(String title) {
		super(title);
		init();
	}
	//Trying to model the buttons, in the center of the window, using insets to move the buttons around
	private void init() {
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		JButton newSimulationButton = new JButton("New Simulation");
		newSimulationButton.addActionListener(new CreateListener());
		buttonConstraints.ipady = 20;
		buttonConstraints.gridwidth = 1;
		buttonConstraints.gridheight = 1;
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 0;
		buttonConstraints.insets = new Insets(100, 300, 0, 10);
		buttonsPanel.add(newSimulationButton, buttonConstraints);
		
		JButton loadSimulationButton = new JButton("Import Simulation");
		loadSimulationButton.addActionListener(new ImportListener());
		buttonConstraints.gridx = 2;
		buttonConstraints.insets.set(100, 10, 0, 300);
		buttonsPanel.add(loadSimulationButton, buttonConstraints);
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new QuitListener());
		buttonConstraints.ipady = 0;
		buttonConstraints.gridx = 1;
		buttonConstraints.gridy = 2;
		buttonConstraints.insets.set(100, 0, 0, 0);
		buttonsPanel.add(quitButton, buttonConstraints);
		
		contentPane.add(buttonsPanel);
		

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setSize(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);
		setResizable(false);
	}

	@Override
	public void run() {

	}

	private class ImportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Launch", "csv", "launch");
			fileChooser.setFileFilter(filter);
			fileChooser.setCurrentDirectory(new File("."));
			int returnVal = fileChooser.showOpenDialog(getContentPane());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String path = file.getPath();
				launchConfigurator.createLaunch(path);
				new LaunchGUI(getTitle());
				setVisible(false);
				dispose();
			}
		}
	}

	private class CreateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			launchConfigurator.createLaunch();
			new PreLaunchGUI(getTitle());
			setVisible(false);
			dispose();
		}

	}

	private class QuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
		}

	}
}
