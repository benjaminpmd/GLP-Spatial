package rocket;


public class RocketEngine {
	
	//kg.m.s-²
	private float thrust;
	
	//kg/min
	private float fuelFlow;
	
	//kg
	private float weight;
	
	/**
	 * @param thrust
	 * @param fuelFlow
	 * @param weight
	 */
	public RocketEngine(float thrust, float fuelFlow, float weight) {
		this.thrust = thrust;
		this.fuelFlow = fuelFlow;
		this.weight = weight;
	}

	public float getThrust() {
		return thrust;
	}

	public void setThrust(float thrust) {
		this.thrust = thrust;
	}

	public float getFuelFlow() {
		return fuelFlow;
	}

	public void setFuelFlow(float fuelFlow) {
		this.fuelFlow = fuelFlow;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	

}
