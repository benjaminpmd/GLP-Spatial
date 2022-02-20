package tests.unit;

import engine.process.calculations.Calculation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestCalculation {

    private Calculation calculation;

    @Before
    public void prepareCalculations() {
        calculation = new Calculation();
    }

    @Test
    public void testDegreeToRadian() {
        assertEquals(5.9, calculation.degreeToRadian(340), 0.1);
    }

    @Test
    public void testFormulas() {
        double acceleration = calculation.accelerationFromThrust(10, 100);
        double velocity = calculation.velocityFromAcceleration(acceleration, 0, 0.25);
        double position = calculation.positionFromVelocity(velocity, 0, 0.25);
        assertEquals(1.9, acceleration, 0.1);
        assertEquals(0.5 ,velocity, 0.1);
        assertEquals(0, position, 0.1);
    }

}
