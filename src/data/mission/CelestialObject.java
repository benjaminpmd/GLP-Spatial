package data.mission;


import data.MobileElement;
import data.coordinate.CartesianCoordinate;

/**
 * Object containing a celestial object and its properties. A celestial object is a naturally occurring object that is
 * in the observable universe. It can be a star, planet, moon, asteroid etc...
 *
 * @author Benjamin P
 * @version 22.03.06 (1.0.0)
 * @see data.MobileElement
 * @since 11.02.22
 */
public class CelestialObject extends MobileElement {

    private String name;
    private int radius;

    public CelestialObject(String name, int radius, double mass) {
        super(mass);
        this.name = name;
        this.radius = radius;
    }

    public CelestialObject(String name, int radius, double mass, CartesianCoordinate cartesianCoordinate) {
        super(mass, cartesianCoordinate);
        this.name = name;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "CelestialObject{" +
                "name='" + name + '\'' +
                ", radius=" + radius +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CelestialObject)) return false;

        CelestialObject that = (CelestialObject) o;

        if (radius != that.radius) return false;
        return name.equals(that.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
