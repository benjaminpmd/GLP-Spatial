package engine.process.management;

import data.rocket.Payload;
import data.rocket.Rocket;
import data.rocket.Stage;
import data.rocket.Tank;
import engine.data.Constants;
import engine.process.builders.StageBuilder;

/**
 * Class that contains all methods to manage a rocket.
 *
 * @author Benjamin P
 * @version 22.02.17 - I like math (1.0.0)
 * @see Rocket
 * @see StageBuilder
 * @since 14.02.22
 */
public class RocketManager {
    Rocket rocket;
    StageBuilder stageBuilder;

    /**
     * Constructor of the class
     */
    public RocketManager() {
        stageBuilder = new StageBuilder();
        Rocket rocket = new Rocket();
    }

    /**
     * Method that calculates the rocket weight.
     *
     * @return the weight of the rocket in kg.
     */
    public double calculateRocketWeight() {
        Stage firstStage = rocket.getFirstStage();
        Stage secondStage = rocket.getSecondStage();
        double weight = 0;
        if (firstStage != null) {
            weight += (firstStage.getEmptyWeight() + (firstStage.getTank().getRemainingPropellant() * firstStage.getTank().getPropellant().getDensity()) + (firstStage.getEngine().getWeight() * firstStage.getEngineNb()));
        }
        if (secondStage != null) {
            weight += (secondStage.getEmptyWeight() + (secondStage.getTank().getRemainingPropellant() * secondStage.getTank().getPropellant().getDensity()) + (secondStage.getEngine().getWeight() * secondStage.getEngineNb()));
        }
        return weight;
    }

    /**
     * Check if the payload can be launched by the rocket.
     *
     * @param payload The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    public boolean validPayloadWeight(Payload payload) {
        double thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        return ((thrust * Constants.GRAVITY) > (calculateRocketWeight() + payload.getWeight()));
    }

    /**
     * Method to check if the rocket is complete or not and is able to lift off the pad.
     *
     * @return a boolean, true if the rocket is correct, false in others cases.
     */
    public boolean validRocket() {
        // TODO: Add checking for lift-off ability
        return (rocket.getFirstStage() != null) && (rocket.getSecondStage() != null) && (rocket.getPayload() != null);
    }

    /**
     * Method that creates a stage and add it to the rocket with specific values.
     *
     * @param tankCapacity          the tank capacity in L.
     * @param propellantDensity     the density of the propellant at its optimal temperature in kg.L^-1.
     * @param propellantTemperature the optimal propellant temperature in C.
     * @param engineNb              the number of engines.
     * @param engineThrust          the thrust output per engine in N.
     * @param engineThrustRatio     the thrust to weight ratio of the engine.
     * @param engineIsp             the specific impulsion of the engine in s.
     * @param stageNb               the stage to create 1 or 2.
     * @throws IllegalArgumentException in case the stageNb is not 1 or 2.
     */
    public void createStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp, int stageNb) throws IllegalArgumentException {
        Stage stage = stageBuilder.buildStage(tankCapacity, propellantDensity, propellantTemperature, engineNb, engineThrust, engineThrustRatio, engineIsp);
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        } else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        } else throw new IllegalArgumentException("The stage number must be 1 or 2");
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

    /**
     * Method that updates the rocket. It removes the used propellant mass and manage stage once the tank is empty.
     *
     * @param deltaTime time passed in s.
     */
    public void updateRocket(double deltaTime) {
        Stage stage;
        Tank tank;
        if ((rocket.getFirstStage() != null) || (rocket.getSecondStage() != null)) {
            if ((rocket.getFirstStage() != null)) {
                stage = rocket.getFirstStage();
            } else {
                stage = rocket.getSecondStage();
            }
            tank = stage.getTank();
            double remainingPropellant = tank.getRemainingPropellant();
            double usedPropellant = (stage.getEngineNb() * stage.getEngine().getPropellantFlow()) * deltaTime;
            if ((remainingPropellant - usedPropellant) > 0) {
                tank.setRemainingPropellant(remainingPropellant - usedPropellant);
            } else {
                if (stage.equals(rocket.getFirstStage())) {
                    rocket.setFirstStage(null);
                } else {
                    rocket.setSecondStage(null);
                }
            }
        }
    }
}
