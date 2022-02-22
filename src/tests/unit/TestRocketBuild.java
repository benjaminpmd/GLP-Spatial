package tests.unit;


import data.rocket.Rocket;
import engine.process.factories.RocketFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRocketBuild {
    
	private Rocket rocket;

    @Before
    public void prepareStage() {
    	RocketFactory rocketFactory = new RocketFactory();
        rocketFactory.createStage(10000, 3, 2000, 1);
        rocketFactory.createStage(7000, 1, 2200, 2);
        rocketFactory.createPayload("test", 200);
        rocket = rocketFactory.getBuiltRocket();
    }

    @Test
    public void testCreation () {
        assertNotNull(rocket);
        assertNotNull(rocket.getFirstStage());
        assertNotNull(rocket.getSecondStage());
        assertNotNull(rocket.getPayload());
    }
}
