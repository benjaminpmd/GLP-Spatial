package tests.unit;

import config.Constants;
import config.SimConfig;
import data.mission.CelestialObject;
import org.junit.Before;
import org.junit.Test;
import process.builders.CelestialObjectBuilder;

import static org.junit.Assert.assertEquals;

public class CelestialObjectBuildTest {

    private CelestialObject celestialObject;

    @Before
    public void prepareCalculation() {

        CelestialObjectBuilder celestialObjectBuilder = new CelestialObjectBuilder(SimConfig.CELESTIAL_OBJECTS_PATH);
        celestialObject = celestialObjectBuilder.buildCelestialObject("Earth");
    }

    @Test
    public void TestCelestialObjectValues() {

        assertEquals(Constants.EARTH_MASS, celestialObject.getMass(), 0);
        assertEquals(Constants.EARTH_RADIUS, celestialObject.getRadius(), 0);
    }
}
