package model.mission;

import engine.data.Coordinates;

import java.math.BigInteger;
import java.util.Objects;
/**
 * The mission class is the center core of the engine, all
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @since 11.02.22
 */
public class Planet {

    private String name;
    private Coordinates coordinates;
    private BigInteger weight;
    private int radius;

    public Planet(String name, Coordinates coordinates, BigInteger weight, int radius) {
        this.name = name;
        this.coordinates = coordinates;
        this.weight = weight;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", weight=" + weight +
                ", radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planet)) return false;
        Planet planet = (Planet) o;
        return getRadius() == planet.getRadius() && getName().equals(planet.getName()) && getCoordinates().equals(planet.getCoordinates()) && getWeight().equals(planet.getWeight());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public BigInteger getWeight() {
        return weight;
    }

    public void setWeight(BigInteger weight) {
        this.weight = weight;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
