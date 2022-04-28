package tests.unit;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.mission.SpaceCenter;
import org.junit.Before;
import org.junit.Test;
import process.builders.SpaceCenterBuilder;

import static org.junit.Assert.assertEquals;

public class SpaceCenterBuildTest {

    private SpaceCenter spaceCenter;

    @Before
    public void prepareCalculation() {

        SpaceCenterBuilder spaceCenterBuilder = new SpaceCenterBuilder(SimConfig.CENTERS_PATH);
        spaceCenter = spaceCenterBuilder.buildSpaceCenter("Wenchang");
    }

    @Test
    public void TestCelestialObjectValues() {
        CartesianCoordinate exceptedCoordinate = new CartesianCoordinate(-4095199, 4880469);
        assertEquals(exceptedCoordinate.getX(), spaceCenter.getCartesianCoordinate().getX(), 100);
        assertEquals(exceptedCoordinate.getY(), spaceCenter.getCartesianCoordinate().getY(), 100);
    }
}
