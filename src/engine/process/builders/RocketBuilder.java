package engine.process.builders;

import engine.data.Constants;
import exceptions.PayloadWeightTooHighException;
import data.rocket.Payload;
import data.rocket.Rocket;
import data.rocket.Stage;

/**
 * Builder for the rocket it features two methods for rocket creation, one with standard settings and another simpler.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see data.rocket.RocketEngine
 * @see engine.process.builders.RocketEngineBuilder
 * @since 11.02.22
 */
public class RocketBuilder {

    private Rocket rocket;

    private double calculateRocketWeight() {
        double firstStageWeight = rocket.getFirstStage().getEmptyWeight() + rocket.getFirstStage().getTank().getRemainingPropellant() + (rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getWeight());
        double secondStageWeight = rocket.getSecondStage().getEmptyWeight() + rocket.getSecondStage().getTank().getRemainingPropellant() + (rocket.getSecondStage().getEngineNb() * rocket.getSecondStage().getEngine().getWeight());
        return firstStageWeight + secondStageWeight;
    }

    /**
     * Check if the payload can be launched by the rocket
     *
     * @param payload The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    private boolean validPayloadWeight(Payload payload) {
        double thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        return (thrust * Constants.GRAVITY) > (calculateRocketWeight() + payload.getWeight());
    }

    /**
     * Builds and returns a rocket
     *
     * @param firstStage  the first stage of the rocket
     * @param secondStage the second stage of the rocket
     * @param payload     the payload to send, caution, the mass of the payload will be check to ensure that the rocket is able to lift it off.
     * @return a Rocket object with a first and second stage plus a payload that have been checked.
     * @throws PayloadWeightTooHighException in case the payload weight is too high an exception is thrown
     */
    public Rocket buildRocket(Stage firstStage, Stage secondStage, Payload payload) throws PayloadWeightTooHighException {
        rocket = new Rocket();
        rocket.setFirstStage(firstStage);
        rocket.setSecondStage(secondStage);
        if (validPayloadWeight(payload)) {
            rocket.setPayload(payload);
        } else {
            throw new PayloadWeightTooHighException();
        }
        return rocket;
    }
}
