package tests.unit;

import config.Constants;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import org.junit.Before;
import org.junit.Test;
import process.management.Calculation;

import static org.junit.Assert.assertEquals;

public class CalculationTest {

    private Calculation calculation;

    @Before
    public void prepareCalculation() {
        calculation = new Calculation();
    }

    @Test
    public void TestConversionCalculs() {
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(10, 10);
        PolarCoordinate polarCoordinate = new PolarCoordinate(14.15, Math.toRadians(45));

        PolarCoordinate convertedPolarCoordinate = calculation.cartesianToPolar(cartesianCoordinate);
        CartesianCoordinate convertedCartesianCoordinate = calculation.polarToCartesian(polarCoordinate);

        assertEquals(14.15, convertedPolarCoordinate.getR(), 0.01);
        assertEquals(Math.toRadians(45), convertedPolarCoordinate.getAngle(), 0);

        assertEquals(10, convertedCartesianCoordinate.getX());
        assertEquals(10, convertedCartesianCoordinate.getY());
    }

    @Test
    public void TestCalculs() {

        CelestialObject celestialObject = new CelestialObject("TestMainGUI", Constants.EARTH_RADIUS, Constants.EARTH_MASS, new CartesianCoordinate());
        CartesianCoordinate originCoordinate = new CartesianCoordinate(10, 10);
        CartesianCoordinate endCoordinate = new CartesianCoordinate(15, 15);

        assertEquals(9.81, calculation.calculateGravity(Constants.EARTH_MASS, Constants.EARTH_RADIUS), 0.01);
        assertEquals(7800, calculation.calculateMiniOrbitalVelocity(celestialObject), 200);

        assertEquals(-44.4, calculation.calculateTemperatureAtAltitude(9144), 0.1);
        assertEquals(300.82, calculation.calculatePressureAtAltitude(9144, -44.4), 0.1);

        assertEquals(1.22, calculation.calculateAirDensity(0), 0.1);

        assertEquals(7.07, calculation.calculateDistance(originCoordinate, endCoordinate), 0.01);

        //assertEquals(22.24, calculation.calculateLaunchAngle(400000, true), 0.01);
    }
}
