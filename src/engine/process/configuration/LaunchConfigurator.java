package engine.process.configuration;

import config.SimConfig;
import data.coordinate.PolarCoordinate;
import data.mission.Mission;
import data.mission.CelestialObject;
import data.rocket.Rocket;
import engine.data.Constants;
import engine.process.builders.SpaceCenterBuilder;
import engine.process.management.MissionManager;
import log.LoggerUtility;
import org.apache.log4j.Logger;

public class LaunchConfigurator {
    /*
    private final Logger logger = LoggerUtility.getLogger(LaunchConfigurator.class, "html");
    private final CelestialObject earth = new CelestialObject("Earth", new PolarCoordinate(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
    private MissionManager missionManager = MissionManager.getInstance();

    public void createLaunch(String name, String description, String centerName, Rocket rocket) {
        SpaceCenterBuilder spaceCenterBuilder = new SpaceCenterBuilder(SimConfig.CENTERS_PATH);

    }

    public void createLaunch(String filePath) {
        FileManager fileManager = new FileManager();
        SpaceCenterBuilder spaceCenterBuilder = new SpaceCenterBuilder(SimConfig.CENTERS_PATH);
        spaceCenterBuilder.buildCenters();
        Mission mission = fileManager.importMission(filePath);
        mission.getRocket().setCoordinates(mission.getCenter().getCoordinates());
        missionManager.setMission(mission);
    }

     */
}
