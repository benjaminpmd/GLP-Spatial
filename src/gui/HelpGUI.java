package gui;

import config.SimConfig;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class HelpGUI extends JFrame {

    private final static Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);
    private Border border = BorderFactory.createEmptyBorder(20, 50, 10, 50);

    public HelpGUI(String title){
        super(title);
        init();
    }

    private void init() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Notions et terminologies de base");


        JLabel payloadLabel = new JLabel("Payload");
        JTextArea payload = new JTextArea("The equipment carried by a rocket, satellite, or spacecraft that will fulfill the goal of the mission."
                + " The payload on a rocket or a launcher is the artificial satellite or the space probe it has to put into orbit.");

        JLabel centreLabel = new JLabel("Spatial centre");
        JTextArea centre = new JTextArea("Geographic zone from which launchers depart."
                + " They generally are situated on the equator in order to make the most of Earth's rotation speed.");
        JLabel scheduleLabel = new JLabel("Schedule");
        JTextArea schedule = new JTextArea("When creating a launch, you can schedule it with UTC time.");


        JLabel launcherLabel = new JLabel("Launcher");
        JTextArea launcher = new JTextArea("You can choose the parameters of two stages of the launcher.");
        JLabel stageLabel = new JLabel("Stage");
        JTextArea stage = new JTextArea("There are two in a rocket. They both have engines and fuel tanks."
                + " The first, which is also the greatest, is discarded at a certain time of the launch : when its tanks are empty."
                + " That is when the second comes in handy. All the following stats have to be chosen separatly for each stage.");
        JLabel propergolLabel = new JLabel("Propergol (fuel)");
        JTextArea propergol = new JTextArea("Mix of a combustive (oxidant) and a combustible (reducer). "
                + "The chemical reaction that occurs is an oxidation-reduction. "
                + "You can choose its volume in litres, its density in kg/L, and its temperature in °C.");
        JLabel nbEnginesLabel = new JLabel("Number of engines");
        JTextArea nbEngines = new JTextArea("Between 1 and 32 for the first stage. "
                + "Between 1 and 5 for the second stage. "
                + "Bring the combustive and the combustible together and propels the rocket.");
        JLabel thrustLabel = new JLabel("Engine thrust");
        JTextArea thrust = new JTextArea("force exerted by gas acceleration on the opposite direction of the progress.");
        JLabel ratioLabel = new JLabel("Thrust to weight ratio");
        JTextArea ratio =  new JTextArea("ratio of the engine thrust (Newton) to the engine weight (kg), gives the engine efficiency.");
        JLabel ispLabel = new JLabel("ISP : Specific impulse");
        JTextArea isp = new JTextArea("duration in seconds of a 1 ton-force thrust. "
                + " As an exemple, chemical engines that use the hydrogen-oxygen couple can produce a 1 ton-force thrust for 460 seconds.");



        leftPanel.add(title);

        leftPanel.add(payloadLabel);
        payload.setBorder(border);
        payload.setLineWrap(true);
        payload.setEditable(false);
        leftPanel.add(payload);
        leftPanel.add(centreLabel);
        centre.setBorder(border);
        centre.setLineWrap(true);
        centre.setEditable(false);
        leftPanel.add(centre);
        leftPanel.add(scheduleLabel);
        schedule.setBorder(border);
        schedule.setLineWrap(true);
        schedule.setEditable(false);
        leftPanel.add(schedule);

        rightPanel.add(launcherLabel);
        launcher.setBorder(border);
        launcher.setLineWrap(true);
        launcher.setEditable(false);
        rightPanel.add(launcher);
        rightPanel.add(stageLabel);
        stage.setLineWrap(true);
        stage.setBorder(border);
        stage.setEditable(false);
        rightPanel.add(stage);
        rightPanel.add(propergolLabel);
        propergol.setBorder(border);
        propergol.setLineWrap(true);
        propergol.setEditable(false);
        rightPanel.add(propergol);
        rightPanel.add(nbEnginesLabel);
        nbEngines.setBorder(border);
        nbEngines.setLineWrap(true);
        nbEngines.setEditable(false);
        rightPanel.add(nbEngines);
        rightPanel.add(thrustLabel);
        thrust.setBorder(border);
        thrust.setLineWrap(true);
        thrust.setEditable(false);
        rightPanel.add(thrust);
        rightPanel.add(ratioLabel);
        ratio.setBorder(border);
        ratio.setLineWrap(true);
        ratio.setEditable(false);
        rightPanel.add(ratio);
        rightPanel.add(ispLabel);
        isp.setBorder(border);
        isp.setLineWrap(true);
        isp.setEditable(false);
        rightPanel.add(isp);

        add(leftPanel);
        add(rightPanel);

        setMinimumSize(preferredSize);
        pack();
        setVisible(true);
    }
}