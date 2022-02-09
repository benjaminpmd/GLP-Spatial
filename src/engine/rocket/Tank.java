package engine.rocket;

public class Tank {
	
	// fuel is in kilograms
	private long fuel;
	private float density;
	
	/**
	 * @param capacity
	 * @param fuel
	 */
	public Tank(long fuel, float density) {
		this.fuel = fuel;
	}

	public long getFuel() {
		return fuel;
	}

	public void setFuel(long fuel) {
		this.fuel = fuel;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}



}
