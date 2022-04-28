package gui;

import config.SimConfig;
import data.mission.Mission;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class HelpGUI extends JFrame {

    private final static Dimension preferredSize = new Dimension(SimConfig.WINDOW_WIDTH, SimConfig.WINDOW_HEIGHT);
    private Border border = BorderFactory.createEmptyBorder(20, 50, 10, 50);
    private final Color TEXT_COLOR = new Color(240, 240, 240);
    private final Color BACKGROUND_COLOR = new Color(60,61,64);
    private final  Color TEXT_BACKGROUND_COLOR = new Color(71,72,75);
    private Mission currentMission;
    
    public HelpGUI(String title, Mission currentMission){
        super(title);
        this.currentMission = currentMission;
        init();
    }

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

       JLabel missionNameLabel = new JLabel();
       JTextArea description = new JTextArea();
        
        if(currentMission != null) {
        	missionNameLabel.setText(currentMission.getName());
        	description.setText(currentMission.getDescription());
        }
        else {
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
        
        
        scheduleLabel.setForeground(TEXT_COLOR);
        leftPanel.add(scheduleLabel);
        schedule.setForeground(TEXT_COLOR);
        schedule.setBackground(TEXT_BACKGROUND_COLOR);
        schedule.setBorder(border);
        schedule.setLineWrap(true);
        schedule.setEditable(false);
        leftPanel.add(schedule);

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
        leftScroll.setPreferredSize(new Dimension(SimConfig.WINDOW_WIDTH/3, SimConfig.WINDOW_HEIGHT));
        
        midPanel.setBackground(BACKGROUND_COLOR);
        midScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(midScroll);
        midScroll.setPreferredSize(new Dimension(SimConfig.WINDOW_WIDTH/3, SimConfig.WINDOW_HEIGHT));
        
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(rightScroll);
        rightScroll.setPreferredSize(new Dimension(SimConfig.WINDOW_WIDTH/3, SimConfig.WINDOW_HEIGHT));
        
        setMinimumSize(preferredSize);
        pack();
        setVisible(true);
    }
}