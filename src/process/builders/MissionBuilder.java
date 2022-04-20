package process.builders;

import data.mission.Mission;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public Mission buildMission(String name, String description, String spaceCenterName, String destinationName, int orbit) {
        Mission mission;
        orbit *= 1000;
        if (name.equals("")) {
            name = "mission-" + new SimpleDateFormat("yyMMddmmss").format(new Date());
        }

        if (description.equals("")) {
            description = "No description provided.";
        }

        mission = new Mission(spaceCenterName, destinationName, Integer.valueOf(orbit), name, description, new Date());
        logger.trace("Mission successfully built");
        return mission;
    }
}
