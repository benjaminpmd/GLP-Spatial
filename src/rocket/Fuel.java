package rocket;

public class Fuel {
	
	//density has no unit of measurement
	private float density;
	
	//temperature is in °C
	private float temperature;

	/**
	 * @param density
	 * @param temperature
	 */
	public Fuel(float density, float temperature) {
		this.density = density;
		this.temperature = temperature;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	
}
