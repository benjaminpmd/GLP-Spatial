package engine.process.builders;

import exceptions.PayloadWeightTooHighException;

import model.rocket.Payload;
import model.rocket.Rocket;
import model.rocket.Stage;

import engine.data.Constants;

/**
 * Builder for the rocket it features two methods for rocket creation, one with standard stettings and another simplier.
 * 
 * @author Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 * @see {@link model.rocket.RocketEngine} {@link engine.process.builders.RocketEngineBuilder}
 */
public class RocketBuilder {

    private Rocket rocket;

    private float calculateRocketWeight() {
        float firstStageWeight = rocket.getFirstStage().getEmptyWeight() + rocket.getFirstStage().getTank().getRemainingPropellant() + (rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getWeight());
        float secondStageWeight = rocket.getSecondStage().getEmptyWeight() + rocket.getSecondStage().getTank().getRemainingPropellant() + (rocket.getSecondStage().getEngineNb() * rocket.getSecondStage().getEngine().getWeight());
        return firstStageWeight + secondStageWeight;
    }

    /**
     * Check if the payload can be launched by the rocket
     * 
     * @param payloadWeight The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    private boolean validPayloadWeight(Payload payload) {
        int thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        if ((thrust * Constants.GRAVITY) > calculateRocketWeight()) {
            return true;
        }
        else return false;
    }

    /**
     * Builds and returns a rocket
     * @param firstStage the first stage of the rocket
     * @param secondStage the second stage of the rocket
     * @param payload the payload to send, caution, the mass of the payload will be check to ensure that the rocket is able to lift it off.
     * @return a Rocket object with a first and second stage plus a payload that have been checked.
     * @throws PayloadWeightTooHighException in case the payload weight is too high an exception is thrown
     */
    public Rocket buildRocket(Stage firstStage, Stage secondStage, Payload payload) throws PayloadWeightTooHighException {
        rocket = new Rocket();
        rocket.setFirstStage(firstStage);
        rocket.setSecondStage(secondStage);
        if (validPayloadWeight(payload)) {
            rocket.setPayload(payload);
        }
        else {
            throw new PayloadWeightTooHighException();
        }
        return rocket;
    }
}
