package engine.process.configuration;

import config.SimConfig;
import data.mission.Mission;
import data.mission.Planet;
import data.rocket.Rocket;
import engine.data.Constants;
import engine.data.PolarCoordinates;
import engine.process.builders.CenterBuilder;
import engine.process.management.MissionManager;
import log.LoggerUtility;
import org.apache.log4j.Logger;

public class LaunchConfigurator {

    private final Logger logger = LoggerUtility.getLogger(LaunchConfigurator.class, "html");
    private final Planet earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
    private MissionManager missionManager = MissionManager.getInstance();

    public void createLaunch(String name, String description, String centerName, Rocket rocket) {
        CenterBuilder centerBuilder = new CenterBuilder(SimConfig.CENTERS_PATH);

    }

    public void createLaunch(String filePath) {
        logger.info(filePath);
        FileManager fileManager = new FileManager();
        CenterBuilder centerBuilder = new CenterBuilder(SimConfig.CENTERS_PATH);
        centerBuilder.buildCenters();
        Mission mission = fileManager.importMission(filePath);
        mission.getRocket().setCoordinates(mission.getCenter().getCoordinates());
        missionManager.setMission(mission);
    }

    public void createLaunch() {
        CenterBuilder centerBuilder = new CenterBuilder(SimConfig.CENTERS_PATH);
        centerBuilder.buildCenters();
    }
}
