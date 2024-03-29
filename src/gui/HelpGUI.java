package gui;

import config.SimConfig;
import data.mission.Mission;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class containing all the processes required for the displaying of the software's third window. The user can consult the help menu of the software here.
 * Is opened by using either {@link MainGUI} or {@link SimulationGUI} first.
 *
 * @author Lucas L
 * @version 22.04.28 (1.0.0)
 * @see gui.MainGUI
 * @see gui.SimulationGUI
 * @since 22.04.10
 */
public class HelpGUI extends JFrame {

    // Dimension and sizes of the elements
    private final static Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);
    // Colors used
    private final Color TEXT_COLOR = new Color(240, 240, 240);
    private final Color BACKGROUND_COLOR = new Color(60, 61, 64);
    private final Color TEXT_BACKGROUND_COLOR = new Color(71, 72, 75);
    private final Border border = BorderFactory.createEmptyBorder(20, 50, 10, 50);
    // The mission currently running
    private final Mission currentMission;

    /**
     * Constructor of the HelpGUI window.
     *
     * @param title          the title of the window.
     * @param currentMission the data of the mission currently running
     * @see data.mission.Mission
     */
    public HelpGUI(String title, Mission currentMission) {
        super(title);
        this.currentMission = currentMission;
        init();
    }

    /**
     * Initializes the JFrame.
     */
    private void init() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.PAGE_AXIS));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        JScrollPane leftScroll = new JScrollPane();
        leftScroll.setViewportView(leftPanel);

        JScrollPane midScroll = new JScrollPane();
        midScroll.setViewportView(midPanel);

        JScrollPane rightScroll = new JScrollPane();
        rightScroll.setViewportView(rightPanel);

        JLabel payloadLabel = new JLabel("Payload");
        JTextArea payload = new JTextArea("The equipment carried by a rocket, satellite, or spacecraft that will fulfill the goal of the mission."
                + " The payload on a rocket or a launcher is the artificial satellite or the space probe it has to put into orbit.");

        JLabel centreLabel = new JLabel("Space center");
        JTextArea centre = new JTextArea("Geographic zone from which launchers depart."
                + " They generally are situated on the equator in order to make the most of Earth's rotation speed.");

        JLabel launcherLabel = new JLabel("Rocket");
        JTextArea launcher = new JTextArea("You can choose the parameters of two stages of the rocket.");

        JLabel stageLabel = new JLabel("Stage");
        JTextArea stage = new JTextArea("There are two in a rocket. They both have engines and fuel tanks."
                + " The first, which is also the largest, is discarded at a certain time of the launch : when its tanks are empty."
                + " That is when the second comes in handy. All the following stats have to be chosen separately for each stage.");

        JLabel propergolLabel = new JLabel("Ergol (fuel)");
        JTextArea propergol = new JTextArea("Fuel used by the engine(s) to power the stages. "
                + "You can choose its volume in litres, its density in kg/L.");

        JLabel nbEnginesLabel = new JLabel("Number of engines");
        JTextArea nbEngines = new JTextArea("Between 1 and 32 for the first stage. "
                + "Between 1 and 5 for the second stage.");

        JLabel thrustLabel = new JLabel("Engine thrust");
        JTextArea thrust = new JTextArea("Force exerted by gas acceleration on the opposite direction of the progress.");

        JLabel ratioLabel = new JLabel("Thrust to weight ratio");
        JTextArea ratio = new JTextArea("Ratio of the engine thrust (Newton) to the engine weight (kg), gives the engine efficiency.");

        JLabel exhaustLabel = new JLabel("Exhaust Velocity");
        JTextArea exhaust = new JTextArea("Velocity of the gas at the exit of the nozzle.");

        JLabel ispLabel = new JLabel("ISP : Specific impulse");
        JTextArea isp = new JTextArea("Duration in seconds of a 1 ton-force thrust. "
                + " As an exemple, chemical engines that use the hydrogen-oxygen couple can produce a 1 ton-force thrust for 460 seconds.");

        JLabel missionNameLabel = new JLabel();
        JTextArea description = new JTextArea();

        if (currentMission != null) {
            missionNameLabel.setText(currentMission.getName());
            description.setText(currentMission.getDescription());
        } else {
            missionNameLabel.setText("No mission name");
            description.setText("No description has been provided yet.");
        }

        payloadLabel.setForeground(TEXT_COLOR);
        leftPanel.add(payloadLabel);
        payload.setForeground(TEXT_COLOR);
        payload.setBackground(TEXT_BACKGROUND_COLOR);
        payload.setBorder(border);
        payload.setLineWrap(true);
        payload.setEditable(false);
        leftPanel.add(payload);


        centreLabel.setForeground(TEXT_COLOR);
        leftPanel.add(centreLabel);
        centre.setForeground(TEXT_COLOR);
        centre.setBackground(TEXT_BACKGROUND_COLOR);
        centre.setBorder(border);
        centre.setLineWrap(true);
        centre.setEditable(false);
        leftPanel.add(centre);

        //Middle panel
        missionNameLabel.setForeground(TEXT_COLOR);
        midPanel.add(missionNameLabel);

        description.setForeground(TEXT_COLOR);
        description.setBackground(TEXT_BACKGROUND_COLOR);
        description.setBorder(border);
        description.setLineWrap(true);
        description.setEditable(false);
        midPanel.add(description);

        //Right panel
        launcherLabel.setForeground(TEXT_COLOR);
        rightPanel.add(launcherLabel);
        launcher.setForeground(TEXT_COLOR);
        launcher.setBackground(TEXT_BACKGROUND_COLOR);
        launcher.setBorder(border);
        launcher.setLineWrap(true);
        launcher.setEditable(false);
        rightPanel.add(launcher);


        stageLabel.setForeground(TEXT_COLOR);
        rightPanel.add(stageLabel);
        stage.setForeground(TEXT_COLOR);
        stage.setBackground(TEXT_BACKGROUND_COLOR);
        stage.setLineWrap(true);
        stage.setBorder(border);
        stage.setEditable(false);
        rightPanel.add(stage);


        propergolLabel.setForeground(TEXT_COLOR);
        rightPanel.add(propergolLabel);
        propergol.setForeground(TEXT_COLOR);
        propergol.setBackground(TEXT_BACKGROUND_COLOR);
        propergol.setBorder(border);
        propergol.setLineWrap(true);
        propergol.setEditable(false);
        rightPanel.add(propergol);


        nbEnginesLabel.setForeground(TEXT_COLOR);
        rightPanel.add(nbEnginesLabel);
        nbEngines.setForeground(TEXT_COLOR);
        nbEngines.setBackground(TEXT_BACKGROUND_COLOR);
        nbEngines.setBorder(border);
        nbEngines.setLineWrap(true);
        nbEngines.setEditable(false);
        rightPanel.add(nbEngines);


        thrustLabel.setForeground(TEXT_COLOR);
        rightPanel.add(thrustLabel);
        thrust.setForeground(TEXT_COLOR);
        thrust.setBackground(TEXT_BACKGROUND_COLOR);
        thrust.setBorder(border);
        thrust.setLineWrap(true);
        thrust.setEditable(false);
        rightPanel.add(thrust);


        ratioLabel.setForeground(TEXT_COLOR);
        rightPanel.add(ratioLabel);
        ratio.setForeground(TEXT_COLOR);
        ratio.setBackground(TEXT_BACKGROUND_COLOR);
        ratio.setBorder(border);
        ratio.setLineWrap(true);
        ratio.setEditable(false);
        rightPanel.add(ratio);

        exhaustLabel.setForeground(TEXT_COLOR);
        rightPanel.add(exhaustLabel);
        exhaust.setForeground(TEXT_COLOR);
        exhaust.setBackground(TEXT_BACKGROUND_COLOR);
        exhaust.setBorder(border);
        exhaust.setLineWrap(true);
        exhaust.setEditable(false);
        rightPanel.add(exhaust);

        ispLabel.setForeground(TEXT_COLOR);
        rightPanel.add(ispLabel);
        isp.setForeground(TEXT_COLOR);
        isp.setBackground(TEXT_BACKGROUND_COLOR);
        isp.setBorder(border);
        isp.setLineWrap(true);
        isp.setEditable(false);
        rightPanel.add(isp);

        leftPanel.setBackground(BACKGROUND_COLOR);
        leftScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(leftScroll);
        leftScroll.setPreferredSize(new Dimension(SimConfig.WINDOW_WIDTH / 3, SimConfig.WINDOW_HEIGHT));

        midPanel.setBackground(BACKGROUND_COLOR);
        midScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(midScroll);
        midScroll.setPreferredSize(new Dimension(SimConfig.WINDOW_WIDTH / 3, SimConfig.WINDOW_HEIGHT));

        rightPanel.setBackground(BACKGROUND_COLOR);
        rightScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(rightScroll);
        rightScroll.setPreferredSize(new Dimension(SimConfig.WINDOW_WIDTH / 3, SimConfig.WINDOW_HEIGHT));

        setMinimumSize(preferredSize);
        pack();
        setVisible(true);
    }
}