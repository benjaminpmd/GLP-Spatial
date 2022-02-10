package engine.process;

import engine.rocket.Rocket;
import engine.rocket.RocketEngine;
import engine.rocket.Payload;
import engine.rocket.Stage;
import engine.rocket.Tank;
import exceptions.MissingStageException;


public class RocketBuilder {
    private Rocket rocket;

    public RocketBuilder() {
        rocket = new Rocket();
    }

    /**
     * Creates and return a stage
     * 
     * @param fuelCapacity The capacity of the stage fuel tank, note that the rocket will be initilised with the maximum capactity.
     * @param fuelDensity The density of the fuel is vital to calculate how much fuel engines use to power the rocket.
     * @param engineNb How many engines power the stage.
     * @param engineThrust The thrust that the engine produce, keep in mind that more powerful is the engine, heavier it will be.
     */
    private Stage createStage(int fuelCapacity, float fuelDensity, int engineNb, int engineThrust) {
        Tank tank = new Tank(fuelCapacity, fuelDensity);
        RocketEngine rocketEngine = new RocketEngine(engineThrust, 0, 0);
        Stage stage = new Stage(engineNb, rocketEngine, tank);
        return stage;
    }

    private float calculateRocketWeight() {
        float firstStageWeight = rocket.getFirstStage().getEmptyWeight() + rocket.getFirstStage().getFuelTank().getFuel() + (rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getWeight());
        float secondStageWeight = rocket.getSecondStage().getEmptyWeight() + rocket.getSecondStage().getFuelTank().getFuel() + (rocket.getSecondStage().getEngineNb() * rocket.getSecondStage().getEngine().getWeight());
        return firstStageWeight + secondStageWeight;
    }

    /**
     * Check if the payload can be launched by the rocket
     * 
     * @param payloadWeight The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    private boolean validatePayloadWeight(int payloadWeight) {
        int thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        if (thrust > calculateRocketWeight()) {
            return true;
        }
        else return false;
    }

    /**
     * Method to call to add a stage to the rocket
     * 
     * @param fuelCapacity The capacity of the stage fuel tank, note that the rocket will be initilised with the maximum capactity.
     * @param fuelDensity The density of the fuel is vital to calculate how much fuel engines use to power the rocket.
     * @param engineNb How many engines power the stage.
     * @param engineThrust The thrust that the engine produce, keep in mind that more powerful is the engine, heavier it will be.
     * @param stage The stage you want to create, can be 1 or 2.
     * @throws IllegalArgumentException If the stage number does not exist.
     */
    public void addStage(int fuelCapacity, float fuelDensity, int engineNb, int engineThrust, int stage) throws  IllegalArgumentException {
        if (stage == 1) {
            rocket.setFirstStage(createStage(fuelCapacity, fuelDensity, engineNb, engineThrust));
        }
        else if (stage == 2) {
            rocket.setSecondStage(createStage(fuelCapacity, fuelDensity, engineNb, engineThrust));
        }
        else {
            throw new IllegalArgumentException("stage value must be 1 or 2");
        }
    }

    public void addPayload(String name, int payloadWeight) throws MissingStageException, IllegalArgumentException {
        if ((rocket.getFirstStage() == null) || (rocket.getSecondStage() == null)) {
            throw new MissingStageException();
        }
        else if (!validatePayloadWeight(payloadWeight)) {
            throw new IllegalArgumentException("Payload weight is too high for the booster you created");
        }
        else {
            rocket.setPayload(new Payload(name, payloadWeight));
        }
    }

    public Rocket getBuiltRocket() {
        return rocket;
    }
}
