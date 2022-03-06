package data.mission;

import data.MobileElement;
import data.coordinate.CartesianCoordinate;

/**
 * Space center is the location of the center on earth, its position follows earth rotation. Space centers are usually
 * located near the equator to use the full benefit of earth rotation velocity.
 *
 * @author Benjamin P
 * @version 22.03.16
 * @since 11.02.22
 */
public class SpaceCenter {

    private String name;
    private CartesianCoordinate cartesianCoordinate;


    public SpaceCenter(String name, CartesianCoordinate cartesianCoordinate) {
        this.name = name;
        this.cartesianCoordinate = cartesianCoordinate;
    }

    public SpaceCenter() {}

    @Override
    public String toString() {
        return "SpaceCenter{" +
                "name='" + name + '\'' +
                ", cartesianCoordinate=" + cartesianCoordinate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceCenter)) return false;

        SpaceCenter that = (SpaceCenter) o;

        if (!getName().equals(that.getName())) return false;
        return getCartesianCoordinate().equals(that.getCartesianCoordinate());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CartesianCoordinate getCartesianCoordinate() {
        return cartesianCoordinate;
    }

    public void setCartesianCoordinate(CartesianCoordinate cartesianCoordinate) {
        this.cartesianCoordinate = cartesianCoordinate;
    }
}
