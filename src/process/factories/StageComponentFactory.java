package process.factories;

import config.Constants;
import data.rocket.Propellant;
import data.rocket.RocketEngine;
import data.rocket.Tank;

/**
 * Factory that create various parts for a stage object.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @see data.rocket.Propellant
 * @see data.rocket.Tank
 * @see data.rocket.RocketEngine
 * @since 22.02.11
 */
public class StageComponentFactory {

    /**
     * Creates a new propellant object.
     *
     * @param density The density of the propellant in kg.L^-1.
     * @return {@link Propellant}
     */
    public static Propellant createPropellant(double density) {

        return new Propellant(density);
    }

    /**
     * Creates a new Tank object.
     *
     * @param propellant The propellant to be used.
     * @param capacity   The total volume capacity of the tank in L.
     * @return {@link Tank}
     */
    public static Tank createTank(Propellant propellant, int capacity) {

        return new Tank(propellant, capacity);
    }

    /**
     * Create an engine with and calculates its performances.
     *
     * @param thrust          The mass output of the engine (kg.s^-1).
     * @param thrustRatio     The ratio thrust/weight of the engine.
     * @param isp             The Specific impulse of the engine.
     * @param exhaustVelocity The speed of the gas at the exit of the nozzle.
     * @return {@link RocketEngine}
     */
    public static RocketEngine createRocketEngine(double thrust, double thrustRatio, int isp, int exhaustVelocity) {

        double engineMass = (thrust / (thrustRatio * Constants.GRAVITY));
        double propellantFlow = (thrust / (Constants.GRAVITY * isp));

        return new RocketEngine(thrust, propellantFlow, engineMass, exhaustVelocity, isp, thrustRatio);
    }
}
