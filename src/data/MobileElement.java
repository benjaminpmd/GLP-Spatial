package data;

import data.coordinate.CartesianCoordinate;

import java.io.Serializable;

/**
 * Class that contains a coordinate, a weight and a velocity of a MobileElement.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see data.coordinate.CartesianCoordinate
 * @since 06/03/22
 */
public class MobileElement {

    // in kg
    private double mass;
    private double velocity;
    private CartesianCoordinate cartesianCoordinate;

    /**
     * Constructor of the MobileElement.
     */
    public MobileElement(double mass, double velocity, CartesianCoordinate cartesianCoordinate) {
        this.mass = mass;
        this.velocity = velocity;
        this.cartesianCoordinate = cartesianCoordinate;
    }

    /**
     * Constructor of the MobileElement.
     */
    public MobileElement(double mass, CartesianCoordinate cartesianCoordinate) {
        this.mass = mass;
        this.cartesianCoordinate = cartesianCoordinate;
    }

    /**
     * Constructor of the MobileElement.
     */
    public MobileElement(double mass) {
        this.mass = mass;
        cartesianCoordinate = new CartesianCoordinate();
    }

    public MobileElement() {
        mass = 0;
        cartesianCoordinate = new CartesianCoordinate();
    }

    @Override
    public String toString() {
        return "MobileElement{" +
                "mass=" + mass +
                ", velocity=" + velocity +
                ", cartesianCoordinate=" + cartesianCoordinate +
                '}';
    }

    public CartesianCoordinate getCartesianCoordinate() {
        return cartesianCoordinate;
    }

    public void setCartesianCoordinate(CartesianCoordinate cartesianCoordinate) {
        this.cartesianCoordinate = cartesianCoordinate;
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
}
