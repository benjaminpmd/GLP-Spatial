package engine.process.builders;

import model.rocket.RocketEngine;

import engine.data.Constants;

/**
 * Builder for the RocketEngine it features two methods for engine creation, one with standard stettings and another simplier.
 * 
 * @author Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 * @see {@link model.rocket.RocketEngine}
 */
public class RocketEngineBuilder {
    
    RocketEngine rocketEngine;
    
    /**
     * Create an engine with and calculates its performances
     * @param thrust the mass output of the engine (kg.s^-1).
     * @param thrustRatio the ratio thrust/weight of the engine.
     * @param isp the Specific impule of the engine.
     * @return the engine what have been built.
     */
    public RocketEngine buildRocketEngine(int thrust, int thrustRatio, int isp) {
        
        // calculations, cast to int as we don't need to keep the precision
        int engineWeight = (int) ((thrustRatio * Constants.GRAVITY) / (thrust * Constants.GRAVITY));
        int fuelFlow = (int) (thrust / (Constants.GRAVITY * isp));
        
        rocketEngine = new RocketEngine(isp, thrust, engineWeight, fuelFlow);
        return rocketEngine;
    }

    /**
     * Simplifed version of the engine build, it will be initiated with default values, ISP as 280 and Thrust to Ratio as 70.
     * @param thrust the mass output of the engine (kg.s^-1).
     * @return the engine what have been built.
     */
    public RocketEngine buildRocketEngine(int thrust) {
        
        // calculations using default values, cast to int as we don't need to keep the precision
        int engineWeight = (int) ((70 * Constants.GRAVITY) / (thrust * Constants.GRAVITY));
        int fuelFlow = (int) (thrust / (Constants.GRAVITY * 280));

        rocketEngine = new RocketEngine(280, thrust, engineWeight, fuelFlow);
        return rocketEngine;
    }
}
