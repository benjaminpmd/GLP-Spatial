package process.management;

import config.Constants;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;

/**
 * Class that contains all equations to use for objects movements.
 *
 * @author Benjamin P
 * @version 22.03.13 (0.1.0 WIP)
 * @see data.coordinate.CartesianCoordinate
 * @see data.coordinate.PolarCoordinate
 * @since 17.02.22
 * <p>
 * TODO: Add all calculs for rocket trajectories and orbital mechanics.
 */
public class Calculation {

    private final double deltaTime;

    public Calculation(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    public Calculation() {
        deltaTime = 1;
    }

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
     * @param polarCoordinate A coordinates objects to get the Cartesian coordinates from.
     * @return a CartesianCoordinate object.
     */
    public CartesianCoordinate polarToCartesian(PolarCoordinate polarCoordinate) {
        int x = (int) (polarCoordinate.getR() * Math.cos(polarCoordinate.getAngle()));
        int y = (int) (polarCoordinate.getR() * Math.sin(polarCoordinate.getAngle()));
        return new CartesianCoordinate(x, y);
    }

    /**
     * Calculates the polar coordinates from cartesian ones.
     *
     * @param cartesianCoordinate A coordinates objects to get the polar coordinates from.
     * @return a PolarCoordinate object.
     */
    public PolarCoordinate cartesianToPolar(CartesianCoordinate cartesianCoordinate) {
        double r = Math.sqrt(Math.pow(cartesianCoordinate.getX(), 2) + Math.pow(cartesianCoordinate.getY(), 2));
        double angle = Math.atan2(cartesianCoordinate.getX(), cartesianCoordinate.getY());
        return new PolarCoordinate(r, angle);
    }

    /**
     * Method that calculates the gravity of an object using the gravity formula.
     *
     * @param mass   the mass in kg of the object emitting the gravity you want to get.
     * @param radius the radius of the object in m.
     * @return a double, gravity value in N.
     */
    public double gravity(double mass, double radius) {
        return ((Constants.GRAVITATIONAL_CONST * mass) / Math.pow(radius, 2));
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
    public double velocityFromAcceleration(double acceleration, double initialVelocity, double deltaTime) {
        // TODO: Improve to 2D trajectory
        return initialVelocity + (acceleration * deltaTime);
    }

    /**
     * Method that calculates the position from the velocity.
     *
     * @param velocity     the velocity of the object.
     * @param acceleration the acceleration of the object.
     * @return a double, the new position of the object.
     */
    public CartesianCoordinate calculateNewPosition(CartesianCoordinate cartesianCoordinate, double velocity, double acceleration, double rotationAngle) {
        // TODO: Improve to 2D trajectory
        int x = cartesianCoordinate.getX();
        cartesianCoordinate.setX( (int) (x + 10));
        return cartesianCoordinate;
    }


}
