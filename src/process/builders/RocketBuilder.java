package process.builders;

import data.coordinate.CartesianCoordinate;
import data.rocket.Payload;
import data.rocket.Rocket;
import data.rocket.Stage;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Builder for the rocket it features two methods for rocket creation.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see data.rocket.Rocket
 * @since 11.02.22
 */
public class RocketBuilder {

    private final Logger logger = LoggerUtility.getLogger(RocketBuilder.class, "html");
    private final StageBuilder stageBuilder;
    private Rocket rocket;

    /**
     * Builder of the Rocker.
     */
    public RocketBuilder() {
        stageBuilder = new StageBuilder();
    }

    /**
     * Build method for the rocket.
     *
     * @param firstStageParam     {@link HashMap} a HashMap object that contains the values for tankCapacity, propellantDensity, propellantTemperature, propellantTemperature, engineNb, engineThrust, engineThrustRatio and isp of the first stage.
     * @param secondStageParam    {@link HashMap} a HashMap object that contains the values for tankCapacity, propellantDensity, propellantTemperature, propellantTemperature, engineNb, engineThrust, engineThrustRatio and isp of the second stage.
     * @param payloadMass         {@link Integer} an integer that represent the mass of the payload in kg.
     * @param cartesianCoordinate {@link CartesianCoordinate} the cartesian coordinate of the rocket.
     * @return {@link data.rocket.Rocket}
     */
    public Rocket buildRocket(HashMap<String, String> firstStageParam, HashMap<String, String> secondStageParam, int payloadMass, CartesianCoordinate cartesianCoordinate) {

        // building the first stage
        Stage firstStage = stageBuilder.buildStage(
                Integer.valueOf(firstStageParam.get("capacity")),
                Double.valueOf(firstStageParam.get("density")),
                Integer.valueOf(firstStageParam.get("engineNb")),
                Double.valueOf(firstStageParam.get("thrust")),
                Double.valueOf(firstStageParam.get("thrustRatio")),
                Integer.valueOf(firstStageParam.get("isp")),
                Integer.valueOf(firstStageParam.get("exhaustVelocity"))
        );

        // building the second stage
        Stage secondStage = stageBuilder.buildStage(
                Integer.valueOf(secondStageParam.get("capacity")),
                Double.valueOf(firstStageParam.get("density")),
                Integer.valueOf(secondStageParam.get("engineNb")),
                Double.valueOf(secondStageParam.get("thrust")),
                Double.valueOf(secondStageParam.get("thrustRatio")),
                Integer.valueOf(secondStageParam.get("isp")),
                Integer.valueOf(firstStageParam.get("exhaustVelocity"))
        );

        // building the payload
        Payload payload = new Payload(payloadMass);
        // building and creating the rocket
        rocket = new Rocket(firstStage, secondStage, payload, cartesianCoordinate);
        logger.trace("Rocket successfully built.");
        return rocket;
    }
}
