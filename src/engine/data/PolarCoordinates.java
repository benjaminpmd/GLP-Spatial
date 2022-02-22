package engine.data;

/**
 * Class that store the location of an object in the interstellar void of deep space relative to an anchor point (our lovely earth)
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @since 11.02.22
 */
public class PolarCoordinates extends Coordinates {
	
	// km
	private double r;
	// the angle from 0 to 359 degrees from the north (top of the window), added to the altitude, it forms the location
	// of an object in space in radian
	private double angle;

	public PolarCoordinates() {
		this(Constants.EARTH_RADIUS, 0, 0);
	}

	public PolarCoordinates(double angle) {
		this(Constants.EARTH_RADIUS, angle, 0);
	}

	public PolarCoordinates(double angle, double r) {
		this(r, angle, 0);
	}

	public PolarCoordinates(double r, double angle, double selfAngle) {
		super(selfAngle);
		this.r = r;
		this.angle = angle;
	}

	@Override
	public String toString() {
		return "PolarCoordinates{" +
				"r=" + r +
				", angle=" + angle +
				"} " + super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PolarCoordinates)) return false;
		if (!super.equals(o)) return false;

		PolarCoordinates that = (PolarCoordinates) o;

		if (Double.compare(that.r, r) != 0) return false;
		return Double.compare(that.angle, angle) == 0;
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
