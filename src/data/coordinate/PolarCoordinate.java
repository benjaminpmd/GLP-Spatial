package data.coordinate;

/**
 * Class that store the polar location of an object relative to a reference anchor point.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @see data.coordinate.Coordinate
 * @since 22.02.11
 */
public class PolarCoordinate extends Coordinate {

    // in meter
    private double r;
    // the angle in radian
    private double angle;

    public PolarCoordinate() {
        super();
        r = 0;
        angle = 0;
    }

    public PolarCoordinate(double r, double angle) {
        super();
        this.r = r;
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "PolarCoordinate{" +
                "r=" + r +
                ", angle=" + angle +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PolarCoordinate)) return false;

        PolarCoordinate that = (PolarCoordinate) o;

        if (Double.compare(that.getR(), getR()) != 0) return false;
        return Double.compare(that.getAngle(), getAngle()) == 0;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
