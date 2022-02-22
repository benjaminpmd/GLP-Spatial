package engine.data;

public class Coordinates {
	// in radian
	private double selfAngle;

	Coordinates(double selfAngle) {
		this.selfAngle = selfAngle;
	}

	@Override
	public java.lang.String toString() {
		return "Coordinates{" +
				"selfAngle=" + selfAngle +
				'}';
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof Coordinates)) return false;
		if (!super.equals(object)) return false;

		Coordinates that = (Coordinates) object;

		if (java.lang.Double.compare(that.getSelfAngle(), getSelfAngle()) != 0) return false;

		return true;
	}

	public double getSelfAngle() {
		return selfAngle;
	}

	public void setSelfAngle(double selfAngle) {
		this.selfAngle = selfAngle;
	}
}
