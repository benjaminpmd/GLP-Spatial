package tests.unit;


import data.rocket.Rocket;
import engine.process.builders.RocketBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRocketBuild {
    
	private Rocket rocket;

    @Before
    public void prepareStage() {
    	RocketBuilder rocketBuilder = new RocketBuilder();
        rocketBuilder.addStage(10000, 3, 2000, 1);
        rocketBuilder.addStage(7000, 1, 2200, 2);
        rocketBuilder.addPayload("test", 200);
        rocket = rocketBuilder.getBuiltRocket();
    }

    @Test
    public void testCreation () {
        assertNotNull(rocket);
        assertNotNull(rocket.getFirstStage());
        assertNotNull(rocket.getSecondStage());
        assertNotNull(rocket.getPayload());
    }
}
