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
 * @version 22.03.13 (1.0.0)
 * @see process.factories.StageComponentFactory
 * @see data.rocket.Stage
 * @since 14.02.22
 */
public class StageBuilder {

    /**
     * Method to build a stage.
     *
     * @param tankCapacity          {@link Integer} the capacity in L.
     * @param propellantDensity     {@link Double} the density of the propellant to use in kg.L^-1.
     * @param propellantTemperature {@link Integer} the ideal temperature of the propellant to use in °C.
     * @param engineNb              {@link Integer} the number of engine.
     * @param engineThrust          {@link Double} the thrust produced per engine in N.
     * @param engineThrustRatio     {@link Double} the "Weight to Thrust Ratio" of the engine.
     * @param engineIsp             {@link Integer} the Specific Impulse of the engine.
     * @return {@link Stage}
     */
    public Stage buildStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp) {

        // create the propellant for the stage
        Propellant propellant = StageComponentFactory.createPropellant(propellantDensity, propellantTemperature);

        // create the tank
        Tank tank = StageComponentFactory.createTank(propellant, tankCapacity);

        // create the engine
        RocketEngine rocketEngine = StageComponentFactory.createRocketEngine(engineThrust, engineThrustRatio, engineIsp);

        // To get the area to use to calculate material weight on the stage, we represent the tank as a sphere
        double radius = Math.pow(((3 * tankCapacity) / (4 * Math.PI)), (1.0 / 3.0)) / 10;
        // using 40.5kg as the mass (in kg.m^-2 +3mm large) of the used material to build the rocket, it corresponds to a light aluminium
        double mass = (40.5 * ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3))) + rocketEngine.getMass() * engineNb;

        // build and return the stage
        return new Stage(tank, rocketEngine, engineNb, mass);
    }
}
