package data.coordinate;

/**
 * Class containing the base of a coordinate, the angle of the object in radian.
 *
 * @author Benjamin P
 * @version 22.03.06
 * @since 11.02.22
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
	public java.lang.String toString() {
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
