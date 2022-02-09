package engine.process;

import engine.rocket.Rocket;
import engine.rocket.RocketEngine;
import engine.rocket.Payload;
import engine.rocket.Stage;
import engine.rocket.Tank;


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
    private Stage createStage(long fuelCapacity, float fuelDensity, int engineNb, float engineThrust) {
        Tank tank = new Tank(fuelCapacity, fuelDensity);
        RocketEngine rocketEngine = new RocketEngine(engineThrust, 0, 0);
        Stage stage = new Stage(engineNb, rocketEngine, tank);
        return stage;
    }

    /**
     * Check if the payload can be launched by the rocket
     * 
     * @param payloadWeight The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    private boolean validatePayload(float payloadWeight) {
        // TODO: do some physics to calculate thrust of engine and validate payload weight
        return true;
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
    public void addStage(long fuelCapacity, float fuelDensity, int engineNb, float engineThrust, int stage) throws  IllegalArgumentException {
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

    public void addPayload(String name, float payloadWeight) {
        if (validatePayload(payloadWeight)) {
            rocket.setPayload(new Payload(name, payloadWeight));
        }
    }

    public Rocket getBuiltRocket() {
        return rocket;
    }
}
