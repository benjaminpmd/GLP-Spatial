package process.builders;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.mission.Mission;
import data.mission.SpaceCenter;
import data.rocket.Rocket;
import exceptions.MissingPartException;
import exceptions.TooLowThrustException;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.management.SimulationManager;

import java.util.HashMap;

/**
 * Class that build the simulation manager to use. Featuring an overload of the build method, it allows to build a manager
 * from imported file or user input.
 *
 * @version 22.03.18 (1.0.0)
 * @see process.management.SimulationManager
 * @since 18.03.22
 */
public class SimulationBuilder {

    private final Logger logger = LoggerUtility.getLogger(SimulationBuilder.class, "html");

    private final MissionBuilder missionBuilder;
    private final CelestialObjectBuilder celestialObjectBuilder;
    private final SpaceCenterBuilder spaceCenterBuilder;
    private final RocketBuilder rocketBuilder;

    public SimulationBuilder() {
        celestialObjectBuilder = new CelestialObjectBuilder(SimConfig.CELESTIAL_OBJECTS_PATH);
        spaceCenterBuilder = new SpaceCenterBuilder(SimConfig.CENTERS_PATH);

        rocketBuilder = new RocketBuilder();
        missionBuilder = new MissionBuilder();
    }

    public SimulationBuilder(CelestialObjectBuilder celestialObjectBuilder, SpaceCenterBuilder spaceCenterBuilder) {
        this.celestialObjectBuilder = celestialObjectBuilder;
        this.spaceCenterBuilder = spaceCenterBuilder;

        rocketBuilder = new RocketBuilder();
        missionBuilder = new MissionBuilder();
    }

    /**
     * Method to build the manager from the user input.
     *
     * @param firstStageParam  {@link HashMap}<String, String> the first stage settings.
     * @param secondStageParam {@link HashMap}<String, String> the second stage settings.
     * @param payloadMass      {@link String} the mass of the payload.
     * @param name             {@link String} the name of the mission.
     * @param spaceCenterName  {@link String} the name of the space center.
     * @param destinationName  {@link String} the name of the destination object.
     * @param orbit            {@link Integer} the orbit targeted around the object.
     * @return {@link SimulationManager} the manager of the simulation.
     * @throws TooLowThrustException    in case the rocket is not powerful enough to lift off.
     * @throws MissingPartException     in case a part of the rocket is missing.
     * @throws IllegalArgumentException in case the data passed are not correct.
     */
    public SimulationManager buildSimulation(HashMap<String, String> firstStageParam, HashMap<String, String> secondStageParam, String payloadMass, String name, String description, String spaceCenterName, String destinationName, String orbit) throws TooLowThrustException, MissingPartException, IllegalArgumentException {

        // checking mass of the payload
        if (payloadMass == null) {
            throw new IllegalArgumentException("Mass of the payload cannot be empty.");
        }

        int pMass = Integer.valueOf(payloadMass);
        if (pMass < 0) {
            throw new IllegalArgumentException("The payload mass must be more than 0.");
        }

        // checking space center name
        if (spaceCenterName == null) {
            throw new IllegalArgumentException("You must choose a space center.");
        }

        Mission mission = missionBuilder.buildMission(name, description, spaceCenterName, destinationName, Integer.valueOf(orbit));
        Rocket rocket;

        try {
            SpaceCenter spaceCenter = spaceCenterBuilder.buildSpaceCenter(mission.getSpaceCenterName());
            CartesianCoordinate coordinate = spaceCenter.getCartesianCoordinate();
            rocket = rocketBuilder.buildRocket(firstStageParam, secondStageParam, pMass, coordinate);

        } catch (MissingPartException | TooLowThrustException e) {
            logger.error(e.getMessage());
            throw e;
        }
        SimulationManager manager = new SimulationManager(rocket, mission, celestialObjectBuilder);
        logger.trace("Simulation manager successfully built");
        return manager;
    }
}
