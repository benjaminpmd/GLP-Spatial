package engine.process.factories;

import engine.data.Constants;
import data.rocket.Payload;
import data.rocket.Rocket;
import data.rocket.Stage;
import engine.process.builders.StageBuilder;

/**
 * Builder for the rocket it features two methods for rocket creation, one with standard settings and another simpler.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see data.rocket.RocketEngine
 * @see engine.process.builders.RocketEngineBuilder
 * @since 11.02.22
 */
public class RocketFactory {

    private Rocket rocket = new Rocket();
    private StageBuilder stageBuilder = new StageBuilder();

    /**
     * Check if the payload can be launched by the rocket
     *
     * @param payload The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    private boolean validPayload(Payload payload) throws IllegalArgumentException {
        double thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        return (thrust * Constants.GRAVITY) > (rocket.getWeight() + payload.getWeight());
    }

    /**
     * Method that creates a stage with limited values and add it to the rocket.
     *
     * @param tankCapacity the tank capacity in L.
     * @param engineNb     the number of engines.
     * @param engineThrust the thrust output per engine in N.
     * @param stageNb      the stage to create 1 or 2.
     * @throws IllegalArgumentException in case the stageNb is not 1 or 2.
     */
    public void createStage(int tankCapacity, int engineNb, int engineThrust, int stageNb) throws IllegalArgumentException {
        Stage stage = stageBuilder.buildStage(tankCapacity, engineNb, engineThrust);
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        } else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        } else throw new IllegalArgumentException("The stage number must be 1 or 2");
    }

    public void createStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp, int stageNb) throws IllegalArgumentException {
        Stage stage = stageBuilder.buildStage(tankCapacity, propellantDensity, propellantTemperature, engineNb, engineThrust, engineThrustRatio, engineIsp);
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        } else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        } else throw new IllegalArgumentException("The stage number must be 1 or 2");
    }

    public void createStage(Stage stage, int stageNb) throws IllegalArgumentException {
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        } else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        } else throw new IllegalArgumentException("The stage number must be 1 or 2");
    }

    public void createPayload(String name, int weight) {
        Payload payload = new Payload(name, weight);
        rocket.setPayload(payload);
    }

    public void createPayload(int weight) {
        Payload payload = new Payload(weight);
        rocket.setPayload(payload);
    }

    /**
     * Method to check if the rocket is complete or not and is able to lift off the pad.
     *
     * @return a boolean, true if the rocket is correct, false in others cases.
     */
    public boolean validRocket() {
        return (rocket.getFirstStage() != null) && (rocket.getSecondStage() != null) && (rocket.getPayload() != null) && (rocket.getThrust() > rocket.getWeight() * Constants.GRAVITY);
    }

    public Rocket getBuiltRocket() {
        return rocket;
    }
}
