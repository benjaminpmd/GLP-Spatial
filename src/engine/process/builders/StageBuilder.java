package engine.process.builders;

import data.rocket.Propellant;
import data.rocket.RocketEngine;
import data.rocket.Stage;
import data.rocket.Tank;
import engine.data.Constants;

/**
 * Builder class for stage object.
 *
 * @author Benjamin P
 * @version 22.03.06
 * @see data.rocket.Tank
 * @see data.rocket.Propellant
 * @since 11.02.22
 */
public class StageBuilder {

    public Stage buildStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp) {

        // create the propellant for the stage
        Propellant propellant = new Propellant(propellantDensity, propellantTemperature);

        // create the tank
        Tank tank = new Tank(propellant, tankCapacity);

        // create the engine
        RocketEngine rocketEngine = createRocketEngine(engineThrust, engineThrustRatio, engineIsp);

        // To get the area to use to calculate material weight on the stage, we represent the tank as a sphere
        double radius = Math.pow(((3 * tankCapacity) / (4 * Math.PI)), (1.0 / 3.0)) / 10;
        // using 28.3kg as the mass (in kg.m^-2 +3mm large) of the used material to build the rocket, it corresponds to a light aluminium
        double mass = (28.3 * ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3))) + rocketEngine.getWeight() * engineNb;

        // build and return the stage
        return new Stage(tank, rocketEngine, engineNb, mass);
    }

    /**
     * Create an engine with and calculates its performances
     *
     * @param thrust      the mass output of the engine (kg.s^-1).
     * @param thrustRatio the ratio thrust/weight of the engine.
     * @param isp         the Specific impulse of the engine.
     * @return the engine what have been built.
     */
    private RocketEngine createRocketEngine(double thrust, double thrustRatio, int isp) {

        double engineWeight = ((thrust * Constants.GRAVITY) / (thrustRatio * Constants.GRAVITY));
        double propellantFlow = (thrust / (Constants.GRAVITY * isp));
        propellantFlow += ((propellantFlow * 10) / 100);

        RocketEngine rocketEngine = new RocketEngine(isp, thrust, propellantFlow, engineWeight);
        return rocketEngine;
    }

    /**
     * Simplified version of the engine build, it will be initiated with default values.
     *
     * @param thrust the mass output of the engine (kg.s^-1).
     * @return the engine what have been built.
     */
    private RocketEngine createRocketEngine(double thrust) {

        return createRocketEngine(thrust, Constants.DEFAULT_THRUST_RATIO, Constants.DEFAULT_ISP);
    }
}
