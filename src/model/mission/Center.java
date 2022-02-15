package model.mission;

import engine.data.Coordinates;

/**
 * Space center is the location of the center on earth, its position follows earth rotation. Space centers are usually
 * located near the equator to use the full benefit of earth rotation velocity.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @since 11.02.22
 */
public class Center {

    private String name;
    private Coordinates coordinates;

    /**
     * @param name        The name of the space center
     * @param coordinates location of the space center
     */
    public Center(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public Center(String name, double angle) {
        this.name = name;
        this.coordinates = new Coordinates(angle);
    }

    @Override
    public String toString() {
        return "Center{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
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
}
