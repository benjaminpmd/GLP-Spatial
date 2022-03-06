package data;

import data.coordinate.CartesianCoordinate;

/**
 * Class that contains a coordinate to standardize coordinate management of different objects to show.
 *
 * @author Benjamin P
 * @version 22.03.06
 * @see data.coordinate.CartesianCoordinate
 * @since 06/03/22
 */
public class MobileElement {

    // in kg
    private double mass;
    private CartesianCoordinate cartesianCoordinate;


    public MobileElement(double mass, CartesianCoordinate cartesianCoordinate) {
        this.mass = mass;
        this.cartesianCoordinate = cartesianCoordinate;
    }

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
}
