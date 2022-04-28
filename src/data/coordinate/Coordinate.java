package data.coordinate;

/**
 * Class containing the base of a coordinate, the angle of the object in radian.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class Coordinate {
    // in radian
    private double selfAngle;

    Coordinate(double selfAngle) {
        this.selfAngle = selfAngle;
    }

    Coordinate() {
        this.selfAngle = 0;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "selfAngle=" + selfAngle +
                '}';
    }

    public double getSelfAngle() {
        return selfAngle;
    }

    public void setSelfAngle(double selfAngle) {
        this.selfAngle = selfAngle;
    }
}
