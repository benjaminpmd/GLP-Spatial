package data.mission;


import config.SimConfig;
import data.MobileElement;
import data.coordinate.CartesianCoordinate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
    private Image image;

    public CelestialObject(String name, int radius, double mass, CartesianCoordinate coordinate) {
        super(mass, coordinate);
        this.name = name;
        this.radius = radius;
        try {
            image = ImageIO.read(new File(SimConfig.IMAGE_PATH + name + ".png"));
        } catch (IOException e) {
            image = null;
        }
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
