package engine.data;

/**
 * Class that store the location of an object in the interstellar void of deep space relative to an anchor point (our lovely earth)
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @since 11.02.22
 */
public class Coordinates {
	
	// km
	private double altitude;
	// the angle from 0 to 359 degrees from the north (top of the window), added to the altitude, it forms the location
	// of an object in space in radian
	private double angle;
	// orientation of the object in radian
	private double selfAngle;

	public Coordinates(double angle) {
		this.angle = angle;
		selfAngle = 0;
		altitude = Constants.EARTH_RADIUS;
	}

	@Override
	public String toString() {
		return "Coordinates{" +
				"altitude=" + altitude +
				", angle=" + angle +
				", selfAngle=" + selfAngle +
				'}';
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getSelfAngle() {
		return selfAngle;
	}

	public void setSelfAngle(double selfAngle) {
		this.selfAngle = selfAngle;
	}
}
