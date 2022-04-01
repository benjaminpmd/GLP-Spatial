package process.factories;

import config.Constants;
import data.rocket.Propellant;
import data.rocket.RocketEngine;
import data.rocket.Tank;

/**
 * Factory that create various parts for a stage object.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see data.rocket.Propellant
 * @see data.rocket.Tank
 * @see data.rocket.RocketEngine
 * @since 11.02.22
 */
public class StageComponentFactory {

    /**
     * Creates a new propellant object.
     *
     * @param density     {@link Double} the density of the propellant in kg.L^-1.
     * @param temperature {@link Integer} the ideal temperature of the propellant in °C.
     * @return {@link Propellant}
     */
    public static Propellant createPropellant(double density, int temperature) {

        return new Propellant(density, temperature);
    }

    /**
     * Creates a new Tank object.
     *
     * @param propellant {@link Propellant} the propellant to be used.
     * @param capacity   {@link Integer} the total volume capacity of the tank in L.
     * @return {@link Tank}
     */
    public static Tank createTank(Propellant propellant, int capacity) {

        return new Tank(propellant, capacity);
    }

    /**
     * Create an engine with and calculates its performances.
     *
     * @param thrust      {@link Double} the mass output of the engine (kg.s^-1).
     * @param thrustRatio {@link Double} the ratio thrust/weight of the engine.
     * @param isp         {@link Integer} the Specific impulse of the engine.
     * @return {@link RocketEngine}
     */
    public static RocketEngine createRocketEngine(double thrust, double thrustRatio, int isp) {

        double engineWeight = ((thrust * Constants.GRAVITY) / (thrustRatio * Constants.GRAVITY));
        double propellantFlow = (thrust / (Constants.GRAVITY * isp));
        propellantFlow += ((propellantFlow * 10) / 100);

        return new RocketEngine(isp, thrust, propellantFlow, engineWeight);
    }
}
