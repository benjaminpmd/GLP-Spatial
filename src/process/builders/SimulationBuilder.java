package process.builders;

import data.coordinate.CartesianCoordinate;
import data.mission.Mission;
import data.mission.SpaceCenter;
import data.rocket.Rocket;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.management.SimulationManager;

import java.util.HashMap;

/**
 * Class that build the simulation manager to use. Featuring an overload of the build method, it allows to build a manager
 * from imported file or user input.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @see process.management.SimulationManager
 * @since 22.03.18
 */
public class SimulationBuilder {

    private final Logger logger = LoggerUtility.getLogger(SimulationBuilder.class, "html");

    private final MissionBuilder missionBuilder;
    private final CelestialObjectBuilder celestialObjectBuilder;
    private final SpaceCenterBuilder spaceCenterBuilder;
    private final RocketBuilder rocketBuilder;

    public SimulationBuilder(CelestialObjectBuilder celestialObjectBuilder, SpaceCenterBuilder spaceCenterBuilder) {
        this.celestialObjectBuilder = celestialObjectBuilder;
        this.spaceCenterBuilder = spaceCenterBuilder;

        rocketBuilder = new RocketBuilder();
        missionBuilder = new MissionBuilder();
    }

    public SimulationManager buildSimulation(HashMap<String, String> firstStageParam, HashMap<String, String> secondStageParam, String payloadMass, HashMap<String, String> missionParam) {
        Mission mission = missionBuilder.buildMission(missionParam.get("name"),
                missionParam.get("description"),
                missionParam.get("spaceCenterName"),
                missionParam.get("destinationName"),
                Integer.valueOf(missionParam.get("orbitAltitude"))
        );

        SpaceCenter spaceCenter = spaceCenterBuilder.buildSpaceCenter(mission.getSpaceCenterName());
        CartesianCoordinate coordinate = spaceCenter.getCartesianCoordinate();
        Rocket rocket = rocketBuilder.buildRocket(firstStageParam, secondStageParam, Integer.valueOf(payloadMass), coordinate);


        SimulationManager manager = new SimulationManager(rocket, mission, celestialObjectBuilder);
        logger.trace("Simulation manager successfully built");
        return manager;
    }
}
