package process.builders;

import config.Constants;
import data.coordinate.CartesianCoordinate;
import data.rocket.Payload;
import data.rocket.Rocket;
import data.rocket.Stage;
import exceptions.MissingPartException;
import exceptions.TooLowThrustException;
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

    private Rocket rocket;
    private final StageBuilder stageBuilder;

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
     * @throws MissingPartException  first stage, second stage or payload is or are missing.
     * @throws TooLowThrustException the thrust produced is not enough to lift the rocket off.
     */
    public Rocket buildRocket(HashMap<String, String> firstStageParam, HashMap<String, String> secondStageParam, int payloadMass, CartesianCoordinate cartesianCoordinate) throws MissingPartException, TooLowThrustException {

        // building the first stage
        Stage firstStage = stageBuilder.buildStage(
                Integer.valueOf(firstStageParam.get("tankCapacity")),
                Double.valueOf(firstStageParam.get("propellantDensity")),
                Integer.valueOf(firstStageParam.get("propellantTemperature")),
                Integer.valueOf(firstStageParam.get("engineNb")),
                Double.valueOf(firstStageParam.get("engineThrust")),
                Double.valueOf(firstStageParam.get("engineThrustRatio")),
                Integer.valueOf(firstStageParam.get("isp"))
        );

        // building the second stage
        Stage secondStage = stageBuilder.buildStage(
                Integer.valueOf(secondStageParam.get("tankCapacity")),
                Double.valueOf(firstStageParam.get("propellantDensity")),
                Integer.valueOf(firstStageParam.get("propellantTemperature")),
                Integer.valueOf(secondStageParam.get("engineNb")),
                Double.valueOf(secondStageParam.get("engineThrust")),
                Double.valueOf(secondStageParam.get("engineThrustRatio")),
                Integer.valueOf(secondStageParam.get("isp"))
        );

        // building the payload
        Payload payload = new Payload(payloadMass);

        // building and creating the rocket
        rocket = new Rocket(firstStage, secondStage, payload, cartesianCoordinate);

        if (!completeRocket()) {
            throw new MissingPartException();
        } else if (!validPayloadMass()) {
            throw new TooLowThrustException();
        }
        logger.trace("Rocket successfully built.");
        return rocket;
    }

    /**
     * Check if the payload can be launched by the rocket
     *
     * @return boolean, true if the weight is correct, false in the other case.
     */
    private boolean validPayloadMass() {
        double thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        return ((thrust * Constants.GRAVITY) > rocket.getMass());
    }

    /**
     * Method to check if the rocket is complete or not and is able to lift off the pad.
     *
     * @return a boolean, true if the rocket is correct, false in others cases.
     */
    private boolean completeRocket() {
        return ((rocket.getFirstStage() != null) && (rocket.getSecondStage() != null) && (rocket.getPayload() != null));
    }
}