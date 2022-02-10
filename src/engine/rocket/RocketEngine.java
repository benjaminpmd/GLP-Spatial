package engine.rocket;


public class RocketEngine {
	
	// kg.m.s^-2
	private int thrust;
	// kg/min
	private int fuelFlow;
	// kg
	private int weight;
	
	/**
	 * @param thrust
	 * @param fuelFlow
	 * @param weight
	 */
	public RocketEngine(int thrust, int fuelFlow, int weight) {
		this.thrust = thrust;
		this.fuelFlow = fuelFlow;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "RocketEngine{" +
				"thrust=" + thrust +
				", fuelFlow=" + fuelFlow +
				", weight=" + weight +
				'}';
	}

	public int getThrust() {
		return thrust;
	}

	public void setThrust(int thrust) {
		this.thrust = thrust;
	}

	public int getFuelFlow() {
		return fuelFlow;
	}

	public void setFuelFlow(int fuelFlow) {
		this.fuelFlow = fuelFlow;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
