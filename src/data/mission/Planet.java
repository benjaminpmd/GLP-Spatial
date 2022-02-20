package data.mission;

import java.util.Objects;

import engine.data.PolarCoordinates;

/**
 * The mission class is the center core of the engine, all
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @since 11.02.22
 */
public class Planet {

    private String name;
    private PolarCoordinates polarCoordinates;
    private double weight;
    private int radius;

    public Planet(String name, PolarCoordinates polarCoordinates, double weight, int radius) {
        this.name = name;
        this.polarCoordinates = polarCoordinates;
        this.weight = weight;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", coordinates=" + polarCoordinates +
                ", weight=" + weight +
                ", radius=" + radius +
                '}';
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planet other = (Planet) obj;
		return Objects.equals(polarCoordinates, other.polarCoordinates) && Objects.equals(name, other.name)
				&& radius == other.radius && Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PolarCoordinates getCoordinates() {
        return polarCoordinates;
    }

    public void setCoordinates(PolarCoordinates polarCoordinates) {
        this.polarCoordinates = polarCoordinates;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
