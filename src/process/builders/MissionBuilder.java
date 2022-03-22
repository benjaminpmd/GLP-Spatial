package process.builders;

import data.mission.Mission;
import log.LoggerUtility;
import org.apache.log4j.Logger;

/**
 * Class to transfer input data from the user input to a new Mission Object.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see data.mission.Mission
 * @since 07.03.22
 */
public class MissionBuilder {

    private final Logger logger = LoggerUtility.getLogger(MissionBuilder.class, "html");

    private final CelestialObjectBuilder celestialObjectBuilder;
    private final SpaceCenterBuilder spaceCenterBuilder;

    public MissionBuilder(CelestialObjectBuilder celestialObjectBuilder, SpaceCenterBuilder spaceCenterBuilder) {
        this.celestialObjectBuilder = celestialObjectBuilder;
        this.spaceCenterBuilder = spaceCenterBuilder;
    }

    /**
     * Method that builds and returns a Mission.
     *
     * @param name            {@link String} the name, in case the user has not inserted anything, the default text
     *                        "Name your mission here" will be replaced by a default mission name based on the current time.
     * @param spaceCenterName {@link String} the name of the space center.
     * @param destinationName {@link String} name of the object destination.
     * @param orbit           {@link String} the orbit targeted.
     * @return {@link Mission}
     */
    public Mission buildMission(String name, String spaceCenterName, String destinationName, int orbit) {
        Mission mission;
        if (name.equals("")) {
            mission = new Mission(spaceCenterName, destinationName, Integer.valueOf(orbit));
        } else {
            mission = new Mission(spaceCenterName, destinationName, Integer.valueOf(orbit), name);
        }
        logger.trace("Mission successfully built");
        return mission;
    }
}
