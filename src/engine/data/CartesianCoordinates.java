package engine.data;

/**
 * Class that store the location of an object in the interstellar void of deep space relative to an anchor point (our lovely earth)
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @since 11.02.22
 */
public class CartesianCoordinates extends Coordinates {
	
	// in m
	private int x;
	// in m
	private int y;

	public CartesianCoordinates() {
		this(0, 0, 0);
	}

	public CartesianCoordinates(int x, int y) {
		this(x, y, 0);
	}

	public CartesianCoordinates(int x, int y, double selfAngle) {
		super(selfAngle);
		this.x = x;
		this.y = y;
	}

	@Override
	public java.lang.String toString() {
		return "CartesianCoordinates{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof CartesianCoordinates)) return false;
		if (!super.equals(object)) return false;

		CartesianCoordinates that = (CartesianCoordinates) object;

		if (x != that.x) return false;
		if (y != that.y) return false;

		return true;
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
