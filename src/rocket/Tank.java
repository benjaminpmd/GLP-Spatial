package rocket;

public class Tank {
	
	//capacity is in kilograms
	private float capacity;
	private Fuel fuel;
	
	/**
	 * @param capacity
	 * @param fuel
	 */
	public Tank(float capacity, Fuel fuel) {
		this.capacity = capacity;
		this.fuel = fuel;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public Fuel getFuel() {
		return fuel;
	}

	public void setFuel(Fuel fuel) {
		this.fuel = fuel;
	}



}
