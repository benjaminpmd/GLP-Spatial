package gui.elements;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import gui.instruments.PaintStrategy;
import process.management.SimulationManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class containing methods allowing the telemetries of the rocket to be drawn in {@link gui.SimulationGUI}.
 *
 * @author Lucas L
 * @version 22.04.28 (1.0.0)
 * @see gui.SimulationGUI
 * @since 22.02.22
 */
public class TelemetryDisplay extends JPanel {

    private static final long serialVersionUID = 1L;
    // Angles of both arcs
    private final int maxArcCircleAngle = 180;
    private final int startArcCircleAngle = 0;
    // Displaying and management
    private final PaintStrategy paintStrategy;
    private final SimulationManager manager;
    // Relative position of the centers of both arcs
    private final CartesianCoordinate speedArcCircleCenterCoordinate = new CartesianCoordinate();
    private final CartesianCoordinate altitudeArcCircleCenterCoordinate = new CartesianCoordinate();
    // Relative position of the unit of both arcs
    private final CartesianCoordinate speedUnitNameCoordinate = new CartesianCoordinate();
    private final CartesianCoordinate altitudeUnitNameCoordinate = new CartesianCoordinate();
    // Radius of both arcs
    private final int arcCircleRadius = 50;
    // Default angles
    private double speedCurrentAngle = 25.0;
    private double altitudeCurrentAngle = 45.0;
    // Unit names
    private final String speedUnitName = "Speed (km/h)";
    private final String altitudeUnitName = "Altitude (km)";
    // Default speed values
    private final String speedMinUnit = "0";
    private final String speedMaxUnit = "41400";
    // Default altitude values
    private final String altitudeMinUnit = speedMinUnit;
    private final int altitudeMax;
    private final String altitudeMaxUnit;

    /**
     * Defines all the datas needed to draw the telemetrics.
     *
     * @param manager contains a part of the required datas, regarding the data interval used in both arcs
     */
    public TelemetryDisplay(SimulationManager manager) {
        super();
        this.manager = manager;
        paintStrategy = new PaintStrategy();

        speedArcCircleCenterCoordinate.setX(SimConfig.TELEMETRY_CENTER_XSPEED);
        speedArcCircleCenterCoordinate.setY(SimConfig.TELEMETRY_CENTER_YSPEED);

        altitudeArcCircleCenterCoordinate.setX(SimConfig.TELEMETRY_CENTER_XALTITUDE);
        altitudeArcCircleCenterCoordinate.setY(SimConfig.TELEMETRY_CENTER_YALTITUDE);

        speedUnitNameCoordinate.setX(SimConfig.TELEMETRY_CENTER_XSPEED - 40);
        speedUnitNameCoordinate.setY(SimConfig.TELEMETRY_CENTER_YSPEED - (arcCircleRadius + 20));

        altitudeUnitNameCoordinate.setX(SimConfig.TELEMETRY_CENTER_XALTITUDE - 30);
        altitudeUnitNameCoordinate.setY(SimConfig.TELEMETRY_CENTER_YALTITUDE - (arcCircleRadius + 20));

        if (manager.getMission().getDestinationName().equals("Earth")) {
            altitudeMax = manager.getMission().getOrbitAltitude() + (manager.getMission().getOrbitAltitude() / 6);
        } else {
            altitudeMax = 40000000;
        }
        altitudeMaxUnit = String.valueOf(altitudeMax / 1000);
    }

    /**
     * Draws the telemetrics using an array of data from {@link SimulationManager} at t=0
     *
     * @see SimulationManager
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int rocketVelocity = (int) (manager.getRocket().getVelocity() * 3600) / 1000;
        double rocketAltitude = manager.getAltitude();
        altitudeCurrentAngle = (rocketAltitude * 180) / altitudeMax;
        if (altitudeCurrentAngle > 180) {
            altitudeCurrentAngle = 180;
        } else if (altitudeCurrentAngle < 0) {
            altitudeCurrentAngle = 0;
        }

        speedCurrentAngle = (rocketVelocity * 180) / 41400;
        if (speedCurrentAngle > 180) {
            speedCurrentAngle = 180;
        } else if (speedCurrentAngle < 0) {
            speedCurrentAngle = 0;
        }

        String rocketVelocityInfo = rocketVelocity + " km/h";
        String rocketAltitudeInfo = (int) (rocketAltitude / 1000) + " km";

        paintStrategy.paint(speedArcCircleCenterCoordinate, arcCircleRadius, startArcCircleAngle, maxArcCircleAngle, speedCurrentAngle, speedUnitName, speedUnitNameCoordinate, speedMinUnit, speedMaxUnit, rocketVelocityInfo, g2);
        paintStrategy.paint(altitudeArcCircleCenterCoordinate, arcCircleRadius, startArcCircleAngle, maxArcCircleAngle, altitudeCurrentAngle, altitudeUnitName, altitudeUnitNameCoordinate, altitudeMinUnit, altitudeMaxUnit, rocketAltitudeInfo, g2);
        paintStrategy.paint(manager.getRocketConfig(), g2);
    }

}
