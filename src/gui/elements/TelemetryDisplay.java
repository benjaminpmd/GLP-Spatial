package gui.elements;

import javax.swing.JPanel;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import gui.instruments.PaintStrategy;
import process.management.SimulationManager;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class TelemetryDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private CartesianCoordinate speedArcCircleCenterCoordinate = new CartesianCoordinate();
	private CartesianCoordinate altitudeArcCircleCenterCoordinate = new CartesianCoordinate();
	
	private CartesianCoordinate speedUnitNameCoordinate = new CartesianCoordinate();
	private CartesianCoordinate altitudeUnitNameCoordinate = new CartesianCoordinate();
	
	private final int maxArcCircleAngle = 180;
	private final int startArcCircleAngle = 0;
	
	private int arcCircleRadius = 50;
	
	private double speedCurrentAngle = 25.0;
	private double altitudeCurrentAngle = 45.0;
	
	private String speedUnitName = "Speed (km/h)";
	private String altitudeUnitName = "Altitude (km)";
	
	private String speedMinUnit = "0";
	private String speedMaxUnit = "41400";
	
	private String altitudeMinUnit = speedMinUnit;
	private int altitudeMax;
	private String altitudeMaxUnit;
	
	private PaintStrategy paintStrategy;
	private final SimulationManager manager;
	
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
		}
		else {
			altitudeMax = 40000000;
		}
		altitudeMaxUnit = String.valueOf(altitudeMax/1000);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int rocketVelocity = (int) (manager.getRocket().getVelocity() * 3600) / 1000;
		double rocketAltitude =  manager.getAltitude();
		altitudeCurrentAngle = (rocketAltitude * 180) / altitudeMax;
		if(altitudeCurrentAngle > 180) {
			altitudeCurrentAngle = 180;
		}
		else if (altitudeCurrentAngle < 0) {
			altitudeCurrentAngle = 0;
		}

		speedCurrentAngle = (rocketVelocity * 180) / 41400;
		if(speedCurrentAngle > 180) {
			speedCurrentAngle = 180;
		}
		else if (speedCurrentAngle < 0) {
			speedCurrentAngle = 0;
		}

		String rocketVelocityInfo = rocketVelocity + " km/h";
		String rocketAltitudeInfo = (int) (rocketAltitude/1000) + " km";

		paintStrategy.paint(speedArcCircleCenterCoordinate, arcCircleRadius, startArcCircleAngle, maxArcCircleAngle, speedCurrentAngle, speedUnitName, speedUnitNameCoordinate, speedMinUnit, speedMaxUnit, rocketVelocityInfo, g2);
		paintStrategy.paint(altitudeArcCircleCenterCoordinate, arcCircleRadius, startArcCircleAngle, maxArcCircleAngle, altitudeCurrentAngle, altitudeUnitName, altitudeUnitNameCoordinate, altitudeMinUnit, altitudeMaxUnit,  rocketAltitudeInfo, g2);
		paintStrategy.paint(manager.getRocketConfig(), g2);
	}
	
}
