package data;

import data.coordinate.CartesianCoordinate;

/**
 * Class that contains a coordinate, a mass and a velocity of a MobileElement.
 *
 * @author Benjamin P
 * @version 22.04.28
 * @see data.coordinate.CartesianCoordinate
 * @since 22.03.06
 */
public class MobileElement {

    // in kg
    private double mass;
    // in m.s^-1
    private double velocity;

    private CartesianCoordinate cartesianCoordinate;

    /**
     * Constructor of the MobileElement.
     */
    public MobileElement(double mass, CartesianCoordinate cartesianCoordinate) {
        this.mass = mass;
        this.cartesianCoordinate = cartesianCoordinate;
        velocity = 0;
    }

    /**
     * Constructor of the MobileElement.
     */
    public MobileElement(double mass) {
        this.mass = mass;
        cartesianCoordinate = new CartesianCoordinate();
        velocity = 0;
    }

    @Override
    public String toString() {
        return "MobileElement{" +
                "mass=" + mass +
                ", velocity=" + velocity +
                ", cartesianCoordinate=" + cartesianCoordinate +
                '}';
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public CartesianCoordinate getCartesianCoordinate() {
        return cartesianCoordinate;
    }

    public void setCartesianCoordinate(CartesianCoordinate cartesianCoordinate) {
        this.cartesianCoordinate = cartesianCoordinate;
    }
}
