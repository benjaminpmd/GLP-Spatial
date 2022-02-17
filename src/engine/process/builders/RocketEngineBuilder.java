package engine.process.builders;

import engine.data.Constants;
import data.rocket.RocketEngine;

/**
 * Builder for the RocketEngine it features two methods for engine creation, one with standard settings and another simpler.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see data.rocket.RocketEngine
 * @since 11.02.22
 */
public class RocketEngineBuilder {

    RocketEngine rocketEngine;

    /**
     * Create an engine with and calculates its performances
     *
     * @param thrust      the mass output of the engine (kg.s^-1).
     * @param thrustRatio the ratio thrust/weight of the engine.
     * @param isp         the Specific impulse of the engine.
     * @return the engine what have been built.
     */
    public RocketEngine buildRocketEngine(double thrust, double thrustRatio, int isp) {

        // calculations, cast to int as we don't need to keep the precision
        double engineWeight = ((thrust * Constants.GRAVITY) / (thrustRatio * Constants.GRAVITY));
        double propellantFlow = (thrust / (Constants.GRAVITY * isp));
        propellantFlow += ((propellantFlow * 10) / 100);

        rocketEngine = new RocketEngine(isp, thrust, propellantFlow, engineWeight);
        return rocketEngine;
    }

    /**
     * Simplified version of the engine build, it will be initiated with default values, ISP as 380 and Thrust to Ratio as 65.
     *
     * @param thrust the mass output of the engine (kg.s^-1).
     * @return the engine what have been built.
     */
    public RocketEngine buildRocketEngine(int thrust) {
        return buildRocketEngine(thrust, Constants.DEFAULT_THRUST_RATIO, Constants.DEFAULT_ISP);
    }
}
