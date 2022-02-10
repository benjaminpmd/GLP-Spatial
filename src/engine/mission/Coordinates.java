package engine.mission;

public class Coordinates {
	
	// km
	private float altitude;
	// the angle from 0 to 359 degrees from the north (top of the window), added to the altitude, it forms the location
	// of an object in space
	private float angle;
	// orientation of the object
	private float selfAngle;

	@Override
	public String toString() {
		return "Coordinates{" +
				"altitude=" + altitude +
				", angle=" + angle +
				", selfAngle=" + selfAngle +
				'}';
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getSelfAngle() {
		return selfAngle;
	}

	public void setSelfAngle(float selfAngle) {
		this.selfAngle = selfAngle;
	}
}
