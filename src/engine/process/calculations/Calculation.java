package engine.process.calculations;

import engine.data.Constants;
import engine.data.Coordinates;

/**
 * Class that contains all equations to use for objects movements.
 *
 * @author Benjamin P
 * @version 22.02.17 - I like math (1.0.0)
 * @since 17.02.22
 */
public class Calculation {

    /**
     * Convert from degree to radian
     *
     * @param angle the angle in degree, must be in [0, 360].
     * @return if the angle is in [0, 360], returns the radian value. return 0 in others cases.
     */
    public double degreeToRadian(double angle) {
        if ((angle >= 0) && (angle <= 360)) {
            return ((angle * Math.PI) / 180);
        } else return 0;
    }

    /**
     * Calculates the cartesian coordinates from polar ones.
     *
     * @param coordinates A coordinates objects to get the polar coordinates from.
     * @return an int array containing two values like this: [x, y].
     */
    public int[] polarToCartesian(Coordinates coordinates) {
        int[] tab = new int[2];
        tab[0] = (int) (coordinates.getAltitude() * Math.cos(coordinates.getAngle()));
        tab[1] = (int) (coordinates.getAltitude() * Math.sin(coordinates.getAngle()));
        return tab;
    }

    /**
     * Method that calculates the gravity of an object using the gravity formula.
     *
     * @param mass the mass in kg of the object emitting the gravity you want to get.
     * @param distance the distance of the attracted object in m.
     * @return a double, gravity value in N.
     */
    public double gravity(double mass, double distance) {
        return ((Constants.GRAVITATIONAL_CONST * mass) / Math.pow(distance, 2));
    }

    /**
     * Method that calculates the acceleration in meter per second using thrust and weight.
     *
     * @param mass   the mass of the object in kg.
     * @param thrust the thrust in N.
     * @return a double, the acceleration in m.s^-2.
     */
    public double accelerationFromThrust(double mass, double thrust) {
        // TODO: Adapt to variable gravity
        return (thrust - (mass * Constants.GRAVITY));
    }

    /**
     * Method that calculates the velocity from the acceleration.
     *
     * @param acceleration    the acceleration of the object in m.s^-2.
     * @param initialVelocity its initial velocity in m.s^-1.
     * @param deltaTime       the delta time to use in s.
     * @return a double, the new speed of the object in m.s^-1.
     */
    public double velocityFromAcceleration(double acceleration, double initialVelocity, double deltaTime) {
        return initialVelocity + (acceleration * deltaTime);
    }

    /**
     * Method that calculates the position from the velocity.
     *
     * @param velocity  the velocity of the object.
     * @param x         the initial position of the object.
     * @param deltaTime the delta time to use in s.
     * @return a double, the new position of the object.
     */
    public double positionFromSpeed(double velocity, double x, int deltaTime) {
        return (x + (velocity * deltaTime));
    }
}
