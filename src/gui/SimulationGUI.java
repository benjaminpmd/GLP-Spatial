package gui;

import config.SimConfig;

import gui.elements.ChartPanel;
import gui.elements.TelemetryDisplay;
import gui.elements.TrajectoryDisplay;

import data.coordinate.CartesianCoordinate;

import process.management.FileManager;
import process.management.SimulationManager;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.*;

import java.io.File;

/**
 * Class containing all the processes required for the displaying of the software's second window. This is where the user can witness the simulation, by consulting all the different panels placed at their disposal.
 * Is opened by using {@link MainGUI} first.
 *
 * @see gui.MainGUI
 * @author Alice Mabille
 * @version 22.04.28 (1.0.0)
 * @since 22.02.22
 */
public class SimulationGUI extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    
	// Colors used
    private final Color BACKGROUND_COLOR = new Color(60, 61, 64);
    private final Color TEXT_COLOR = new Color(240, 240, 240);
    private final Color BUTTON_COLOR = new Color(95, 0, 0);
    private final Color BUTTON_ENGAGE_COLOR = new Color(58, 162, 0);
    
    // Window Dimension
    private final Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);
    
    // Managers
    private final SimulationManager manager;
    private final FileManager fileManager;
    
    // Utility elements
    private float speed = 1;
    private boolean firstRun = true;
    
    // Charts
    private final ChartPanel velocityChart;
    private final ChartPanel accelerationChart;
    
    // Graphical elements
    private Container contentPane;
    private final GridBagConstraints c = new GridBagConstraints();
    private final JPanel chartPanel = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem newSimulationItem = new JMenuItem("New");
    private final JMenuItem importSimulationItem = new JMenuItem("Import");
    private final JMenuItem exportSimulationItem = new JMenuItem("Export");
    private final JMenuItem exitItem = new JMenuItem("Exit");
    private final JMenuItem simulationHelpItem = new JMenuItem("Open Help...");
    private final JFileChooser fileChooser = new JFileChooser("./");
    private final JLabel speedLabel = new JLabel("Speed: x" + speed);
    private final JButton startButton = new JButton("Play");
    private final JButton speedupButton = new JButton("Speed up");
    private final JButton slowdownButton = new JButton("Slow down");
    private final JButton zoomInButton = new JButton("Zoom in");
    private final JButton zoomOutButton = new JButton("Zoom out");
    private final JButton resetViewButton = new JButton("Reset View");
    private final JButton trackButton = new JButton("Start tracking");
    
    // Displayer
    private final TrajectoryDisplay trajectoryDisplay;
    private final TelemetryDisplay telemetryDisplay;

    // Initial status of the start button.
    private boolean stop = true;
    private final SimulationGUI instance = this;
    private int simulationSpeed = SimConfig.SIMULATION_SPEED;


	/**
	 * Constructor of the SimulationGUI window.
	 * @param title the title of the window.
	 * @param manager the {@link SimulationManager} to use.
	 * @param fileManager the {@link FileManager} to use for imports/exports.
	 */
    public SimulationGUI(String title, SimulationManager manager, FileManager fileManager) {
        super(title);
        this.manager = manager;
        this.fileManager = fileManager;
        velocityChart = new ChartPanel("Speed", manager.getTelemetry());
        accelerationChart = new ChartPanel("Acceleration", manager.getTelemetry());
        trajectoryDisplay = new TrajectoryDisplay(manager);
        telemetryDisplay = new TelemetryDisplay(manager);
        MouseInput mouseInput = new MouseInput();
        trajectoryDisplay.addMouseListener(mouseInput);
        trajectoryDisplay.addMouseMotionListener(mouseInput);
        trajectoryDisplay.addMouseWheelListener(new MouseWheelControls());
        init();
    }

	/**
	 * Initializes the JFrame.
	 */
    private void init() {

		int MIDDLE = 2;
		int TOP = 0;

        contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(BACKGROUND_COLOR);
        c.fill = GridBagConstraints.BOTH;

        newSimulationItem.addActionListener(new NewSimulationAction());
        exportSimulationItem.addActionListener(new exportAction());
        importSimulationItem.addActionListener(new ImportAction());
        exitItem.addActionListener(new ExitAction());
        simulationHelpItem.addActionListener(new HelpAction());
        fileMenu.add(newSimulationItem);
        fileMenu.add(importSimulationItem);
        fileMenu.add(exportSimulationItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        helpMenu.add(simulationHelpItem);

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
        topLabel.setForeground(TEXT_COLOR);
        topBanner.setBackground(BACKGROUND_COLOR);
        topBanner.add(topLabel);
        contentPane.add(topBanner, c);

        //left panel : graphs
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.PAGE_AXIS));
        c.weightx = 0.2;
        c.weighty = 1;
		c.gridx = 0;
        c.gridy = TOP;
        c.gridwidth = 2;
        c.gridheight = 6;

        chartPanel.add(velocityChart);

        chartPanel.add(accelerationChart);

        contentPane.add(chartPanel, c);

        c.weightx = 0.6;
        c.weighty = 0.5;
        c.gridx = MIDDLE;
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridheight = 3;

        contentPane.add(trajectoryDisplay, c);

        c.weighty = 1;
        c.weightx = 0.5;
        c.gridx = MIDDLE;
		c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;

        telemetryDisplay.setBackground(BACKGROUND_COLOR);
        contentPane.add(telemetryDisplay, c);


        //right panel : play, pause buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(8, 1));
        ((GridLayout) rightPanel.getLayout()).setVgap(10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
		c.gridx = 6;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 4;

        startButton.addActionListener(new StartStopAction());
        speedupButton.addActionListener(new IncreaseSpeedAction());
        slowdownButton.addActionListener(new DecreaseSpeedAction());
        zoomInButton.addActionListener(new ZoomInAction());
        zoomOutButton.addActionListener(new ZoomOutAction());
        resetViewButton.addActionListener(new ResetViewAction());
        trackButton.addActionListener(new TrackAction());

        speedLabel.setForeground(TEXT_COLOR);
        startButton.setForeground(TEXT_COLOR);
        speedupButton.setForeground(TEXT_COLOR);
        slowdownButton.setForeground(TEXT_COLOR);
        zoomInButton.setForeground(TEXT_COLOR);
        zoomOutButton.setForeground(TEXT_COLOR);
        resetViewButton.setForeground(TEXT_COLOR);
        trackButton.setForeground(TEXT_COLOR);

        speedLabel.setBackground(BACKGROUND_COLOR);
        startButton.setBackground(BUTTON_COLOR);
        speedupButton.setBackground(BUTTON_COLOR);
        slowdownButton.setBackground(BUTTON_COLOR);
        zoomInButton.setBackground(BUTTON_COLOR);
        zoomOutButton.setBackground(BUTTON_COLOR);
        resetViewButton.setBackground(BUTTON_COLOR);
        trackButton.setBackground(BUTTON_COLOR);

        rightPanel.add(speedLabel);
        rightPanel.add(startButton);
        rightPanel.add(speedupButton);
        rightPanel.add(slowdownButton);
        rightPanel.add(zoomInButton);
        rightPanel.add(zoomOutButton);
        rightPanel.add(resetViewButton);
        rightPanel.add(trackButton);

        rightPanel.setBackground(BACKGROUND_COLOR);
        contentPane.add(rightPanel, c);


        //bottom panel
        JPanel launchPanel = new JPanel();
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = MIDDLE;
		c.gridy = 5;
        c.gridwidth = 6;
        c.gridheight = 1;

        launchPanel.setBackground(BACKGROUND_COLOR);
        contentPane.add(launchPanel, c);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(preferredSize);
        pack();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Updates the values of all the elements, at each time unit.
     */
    private void updateValues() {
        if (!manager.hasCrashed()) {
            manager.next();
            trajectoryDisplay.repaint();
            telemetryDisplay.repaint();
            velocityChart.update();
            accelerationChart.update();
        }
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                Thread.sleep(simulationSpeed);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            velocityChart.repaint();
            accelerationChart.repaint();
            trajectoryDisplay.repaint();
            telemetryDisplay.repaint();

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
                if (firstRun) {
                    manager.launch();
                    firstRun = false;
                }
                Thread simThread = new Thread(instance);
                simThread.start();
            }
        }
    }

    private class exportAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle("Export simulation");

            int userSelection = fileChooser.showSaveDialog(contentPane);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                fileManager.exportSimulation(manager, fileToSave.getAbsolutePath());
            }
        }
    }

    private class ImportAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            fileChooser.setDialogTitle("Export simulation");

            int userSelection = fileChooser.showOpenDialog(contentPane);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();
                try {
                    stop = true;
                    SimulationManager newManager = fileManager.importSimulation(fileToOpen.getAbsolutePath());
                    new SimulationGUI(getTitle(), newManager, fileManager);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }

    private class ExitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int close;
            if (!stop) {
                close = JOptionPane.showConfirmDialog(null, "A simulation is launched, confirm you want to exit?");
            }
            else close = 1;

            if (close == 1) {
                setVisible(false);
                dispose();
            }
        }
    }

    private class NewSimulationAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stop = true;
            new MainGUI(getTitle());
            setVisible(false);
            dispose();
        }
    }

    private class IncreaseSpeedAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((simulationSpeed / 2) > 0) {
                speed *= 2;
                speedLabel.setText("Speed: x" + speed);
                simulationSpeed /= 2;
            }
        }
    }

    private class DecreaseSpeedAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (((simulationSpeed * 2) > 0) && ((speed / 2) >= 0.0625)) {
                speed /= 2;
                speedLabel.setText("Speed: x" + speed);
                simulationSpeed *= 2;
            }
        }
    }

    private class ZoomInAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((trajectoryDisplay.getScale() / 2) > 0) {
                trajectoryDisplay.setScale(trajectoryDisplay.getScale() / 2);
            }
            trajectoryDisplay.repaint();

        }
    }

    private class ZoomOutAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((trajectoryDisplay.getScale() * 2) > 0) {
                trajectoryDisplay.setScale(trajectoryDisplay.getScale() * 2);
            }
            trajectoryDisplay.repaint();
        }
    }

    private class ResetViewAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CartesianCoordinate coordinate = manager.getRocket().getCartesianCoordinate();

            trajectoryDisplay.setScale(SimConfig.DEFAULT_SCALE);

            int rocketX = SimConfig.GRAPHIC_CENTER_X - (coordinate.getX() / trajectoryDisplay.getScale());
            int rocketY = SimConfig.GRAPHIC_CENTER_Y - (coordinate.getY() / trajectoryDisplay.getScale());

            trajectoryDisplay.setCenterX(rocketX);
            trajectoryDisplay.setCenterY(rocketY);

            trajectoryDisplay.repaint();
        }
    }

    private class TrackAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!trajectoryDisplay.isLock()) {
                trajectoryDisplay.setLock(true);
                trackButton.setText("Stop tracking");
                trackButton.setBackground(BUTTON_ENGAGE_COLOR);
            } else {
                trajectoryDisplay.setLock(false);
                trackButton.setText("Start tracking");
                trackButton.setBackground(BUTTON_COLOR);
            }
            trajectoryDisplay.repaint();
        }
    }

    private class MouseInput extends MouseInputAdapter {

        private int initialX = 0;
        private int initialY = 0;

        @Override
        public void mousePressed(MouseEvent e) {
            initialX = e.getX();
            initialY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int deltaX = (e.getX() - initialX);
            int deltaY = (e.getY() - initialY);

            initialX = e.getX();
            initialY = e.getY();

			trajectoryDisplay.setCenterX(trajectoryDisplay.getCenterX() + deltaX);
            trajectoryDisplay.setCenterY(trajectoryDisplay.getCenterY() + deltaY);

            trajectoryDisplay.repaint();
        }
    }

    private class MouseWheelControls implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() == 1) {
                if ((trajectoryDisplay.getScale() * 2) > 0) {
                    trajectoryDisplay.setScale(trajectoryDisplay.getScale() * 2);
                }
            } else {
                if ((trajectoryDisplay.getScale() / 2) > 0) {
                    trajectoryDisplay.setScale(trajectoryDisplay.getScale() / 2);
                }
            }
            trajectoryDisplay.repaint();
        }
    }

    private class HelpAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new HelpGUI("Help", manager.getMission());
        }
    }
}
