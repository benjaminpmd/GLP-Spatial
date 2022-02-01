package rocket;


public class RocketEngine {
	
	private float thrust;
	private float fuelFlow;
	private String name;
	private float weight;
	
	/**
	 * @param thrust
	 * @param fuelFlow
	 * @param name
	 * @param weight
	 */
	public RocketEngine(float thrust, float fuelFlow, String name, float weight) {
		this.thrust = thrust;
		this.fuelFlow = fuelFlow;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	

}
