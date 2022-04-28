package data.coordinate;

/**
 * Class that store the cartesian location of an object relative to a reference anchor point.
 *
 * @see data.coordinate.Coordinate
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class CartesianCoordinate extends Coordinate {
	
	// in m
	private int x;
	// in m
	private int y;

	public CartesianCoordinate() {
		super();
		x = 0;
		y = 0;
	}

	public CartesianCoordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public CartesianCoordinate(int x, int y, double selfAngle) {
		super(selfAngle);
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "CartesianCoordinate{" +
				"x=" + x +
				", y=" + y +
				"} " + super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CartesianCoordinate)) return false;

		CartesianCoordinate that = (CartesianCoordinate) o;

		if (getX() != that.getX()) return false;
		return getY() == that.getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
