package engine.process.calculations;

import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import engine.data.Constants;

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
    public static double degreeToRadian(double angle) {
        if ((angle >= 0) && (angle <= 360)) {
            return ((angle * Math.PI) / 180);
        } else return 0;
    }

    /**
     * Calculates the cartesian coordinates from polar ones.
     *
     * @param polarCoordinates A coordinates objects to get the Cartesian coordinates from.
     * @return a CartesianCoordinate object.
     */
    public static CartesianCoordinate polarToCartesian(PolarCoordinate polarCoordinates) {
        int x = (int) (polarCoordinates.getR() * Math.cos(polarCoordinates.getAngle()));
        int y = (int) (polarCoordinates.getR() * Math.sin(polarCoordinates.getAngle()));
        return new CartesianCoordinate(x, y);
    }
    
    /**
     * Calculates the polar coordinates from cartesian ones.
     *
     * @param cartesianCoordinate A coordinates objects to get the polar coordinates from.
     * @return a PolarCoordinate object.
     */
    public static PolarCoordinate cartesianToPolar(CartesianCoordinate cartesianCoordinate) {
        double r = Math.sqrt(Math.pow(cartesianCoordinate.getX(), 2) + Math.pow(cartesianCoordinate.getY(), 2));
        double angle = Math.atan2(cartesianCoordinate.getX(), cartesianCoordinate.getY());
        return new PolarCoordinate(r, angle);
    }

    /**
     * Method that calculates the gravity of an object using the gravity formula.
     *
     * @param mass      the mass in kg of the object emitting the gravity you want to get.
     * @param distance  the distance of the attracted object in m.
     * @return a double, gravity value in N.
     */
    public static double gravity(double mass, double distance) {
        return ((Constants.GRAVITATIONAL_CONST * mass) / Math.pow(distance, 2));
    }

    /**
     * Method that calculates the acceleration in meter per second using thrust and weight.
     *
     * @param mass   the mass of the object in kg.
     * @param thrust the thrust in N.
     * @return a double, the acceleration in m.s^-2.
     */
    public static double accelerationFromThrust(double mass, double thrust) {
        // TODO: Adapt to variable gravity
        // TODO: Improve to 2D trajectory
        return (thrust - (mass * Constants.GRAVITY));
    }

    /**
     * Method that calculates the velocity from the acceleration.
     *
     * @param acceleration    the acceleration of the object in m.s^-2.
     * @param initialVelocity its initial velocity in m.s^-1.
     * @param deltaTime       the delta time to use in s.
     * @return a double, the new velocity of the object in m.s^-1.
     */
    public static double velocityFromAcceleration(double acceleration, double initialVelocity, double deltaTime) {
        // TODO: Improve to 2D trajectory
        return initialVelocity + (acceleration * deltaTime);
    }

    /**
     * Method that calculates the position from the velocity.
     *
     * @param velocity  the velocity of the object.
     * @param x         the initial position of the object.
     * @param d         the delta time to use in s.
     * @return a double, the new position of the object.
     */
    public static double positionFromVelocity(double velocity, double x, double d) {
        // TODO: Improve to 2D trajectory
        return (x + (velocity * d));
    }
}
