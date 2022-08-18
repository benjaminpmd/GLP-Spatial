package process.builders;

import data.rocket.Propellant;
import data.rocket.RocketEngine;
import data.rocket.Stage;
import data.rocket.Tank;
import process.factories.StageComponentFactory;

/**
 * Class to build space centers.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @see process.factories.StageComponentFactory
 * @see data.rocket.Stage
 * @since 22.02.14
 */
public class StageBuilder {

    /**
     * Method to build a stage.
     *
     * @param tankCapacity      The capacity in L.
     * @param propellantDensity The density of the propellant to use in kg.L^-1.
     * @param engineNb          The number of engine.
     * @param engineThrust      The thrust produced per engine in N.
     * @param engineThrustRatio The "Weight to Thrust Ratio" of the engine.
     * @param engineIsp         The Specific Impulse of the engine.
     * @param exhaustVelocity   The speed of the gas at the exit of the engine nozzle.
     * @return {@link Stage}
     */
    public Stage buildStage(int tankCapacity, double propellantDensity, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp, int exhaustVelocity) {

        // create the propellant for the stage
        Propellant propellant = StageComponentFactory.createPropellant(propellantDensity);

        // create the tank
        Tank tank = StageComponentFactory.createTank(propellant, tankCapacity);

        // create the engine
        RocketEngine rocketEngine = StageComponentFactory.createRocketEngine(engineThrust, engineThrustRatio, engineIsp, exhaustVelocity);

        // To get the area to use to calculate material weight on the stage, we represent the tank as a sphere
        double radius = Math.pow(((tankCapacity / Math.PI) * (3 / 4)), (1.0 / 3.0));
        // using 40.5kg as the mass (in kg.m^-2 +3mm large) of the used material to build the rocket, it corresponds to a light aluminium
        double mass = (40.5 * (4 * Math.PI * Math.pow(radius, 2))) + rocketEngine.getMass() * engineNb;

        // build and return the stage
        return new Stage(tank, rocketEngine, engineNb, mass);
    }
}
