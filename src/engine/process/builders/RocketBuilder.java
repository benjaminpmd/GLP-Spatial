package engine.process.builders;

import engine.data.Constants;
import data.rocket.Payload;
import data.rocket.Rocket;
import data.rocket.Stage;

/**
 * Builder for the rocket it features two methods for rocket creation, one with standard settings and another simpler.
 * TODO: add logging
 *
 * @author Benjamin P
 * @version 22.03.06
 * @see data.rocket.RocketEngine
 * @see engine.process.builders.RocketEngineBuilder
 * @since 11.02.22
 */
public class RocketBuilder {

    private Rocket rocket = new Rocket();

    private StageBuilder stageBuilder = new StageBuilder();

    /**
     * Check if the payload can be launched by the rocket
     *
     * @param payload The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    /*
    private boolean validPayload(Payload payload) throws IllegalArgumentException {
        double thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        return (thrust * Constants.GRAVITY) > (rocket.getWeight() + payload.getWeight());
    }

     */

    /**
     * Method that creates a stage with limited values and add it to the rocket.
     *
     * @param tankCapacity the tank capacity in L.
     * @param engineNb     the number of engines.
     * @param engineThrust the thrust output per engine in N.
     * @param stageNb      the stage to create 1 or 2.
     * @throws IllegalArgumentException in case the stageNb is not 1 or 2.
     */
    public void addStage(int tankCapacity, int engineNb, int engineThrust, int stageNb) throws IllegalArgumentException {
        Stage stage = stageBuilder.buildStage(tankCapacity, engineNb, engineThrust);
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        } else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        } else throw new IllegalArgumentException("The stage number must be 1 or 2");
    }

    public void addPayload(String name, int weight) {
        Payload payload = new Payload(name, weight);
        rocket.setPayload(payload);
    }

    /**
     * Method to check if the rocket is complete or not and is able to lift off the pad.
     *
     * @return a boolean, true if the rocket is correct, false in others cases.
     */
    /*
    public boolean validRocket() {
        return (rocket.getFirstStage() != null) && (rocket.getSecondStage() != null) && (rocket.getPayload() != null) && (rocket.getThrust() > rocket.getWeight() * Constants.GRAVITY);
    }


     */
    public Rocket getBuiltRocket() {
        return rocket;
    }
}
