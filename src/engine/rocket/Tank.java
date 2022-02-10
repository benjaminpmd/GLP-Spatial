package engine.rocket;

public class Tank {
	
	// fuel is in kilograms
	private int fuel;
	private float density;
	
	/**
	 * @param capacity
	 * @param fuel
	 */
	public Tank(int fuel, float density) {
		this.fuel = fuel;
	}

	@Override
	public String toString() {
		return "Tank{" +
				"fuel=" + fuel +
				", density=" + density +
				'}';
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}



}
