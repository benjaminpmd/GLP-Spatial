package engine.process.configuration;

import config.SimConfig;
import data.mission.Center;
import data.mission.Mission;
import data.mission.Planet;
import data.mission.Target;
import data.rocket.Rocket;
import engine.data.Constants;
import engine.data.PolarCoordinates;
import engine.process.builders.CenterBuilder;
import engine.process.builders.RocketBuilder;
import engine.process.management.MissionManager;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LaunchConfigurator {

    private final Logger logger = LoggerUtility.getLogger(LaunchConfigurator.class, "html");
    private final Planet earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
    private MissionManager missionManager = MissionManager.getInstance();

    public void createLaunch(String name, String description, String centerName, Rocket rocket) {
        CenterBuilder centerBuilder = new CenterBuilder(SimConfig.CENTERS_PATH);

    }

    public void createLaunch(String filePath) {
        FileManager fileManager = new FileManager();
        CenterBuilder centerBuilder = new CenterBuilder(SimConfig.CENTERS_PATH);
        centerBuilder.buildCenters();
        Mission mission = fileManager.importMission(filePath);
        mission.getRocket().setCoordinates(mission.getCenter().getCoordinates());
        missionManager.setMission(mission);
    }
}
