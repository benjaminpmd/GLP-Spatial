package test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import engine.process.builders.RocketEngineBuilder;
import data.rocket.RocketEngine;

public class TestRocketEngineBuild {
    
	private RocketEngine rocketEngine;
    private RocketEngine simpleRocketEngine;

    @Before
    public void prepareRocketEngine() {
    	RocketEngineBuilder rocketEngineBuilder = new RocketEngineBuilder();
        rocketEngine = rocketEngineBuilder.buildRocketEngine(80000, 60, 300);
        simpleRocketEngine = rocketEngineBuilder.buildRocketEngine(60000);
    }

    @Test
    public void testEngine () {
        double weight = rocketEngine.getWeight();
        double propellantFlow = rocketEngine.getPropellantFlow();

        assertEquals(1333, weight, 1);
        assertEquals(29.9, propellantFlow, 1);
    }

    @Test
    public void testSimpleEngine () {
        double weight = simpleRocketEngine.getWeight();
        double propellantFlow = simpleRocketEngine.getPropellantFlow();

        assertEquals(923, weight, 1);
        assertEquals(17.7, propellantFlow, 1);
    }
}
