package data.mission;

import engine.data.PolarCoordinates;

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
    private PolarCoordinates polarCoordinates;

    /**
     * @param name        The name of the space center
     * @param polarCoordinates location of the space center
     */
    public Center(String name, PolarCoordinates polarCoordinates) {
        this.name = name;
        this.polarCoordinates = polarCoordinates;
    }

    public Center(String name, double angle) {
        this.name = name;
        this.polarCoordinates = new PolarCoordinates(angle);
    }

    @Override
    public String toString() {
        return "Center{" +
                "name='" + name + '\'' +
                ", coordinates=" + polarCoordinates +
                '}';
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
}
