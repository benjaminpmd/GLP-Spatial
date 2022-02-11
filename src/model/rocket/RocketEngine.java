package model.rocket;

/**
 * Class that contains informations about the rocket engine
 * @author Alice M, Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 */
public class RocketEngine {
	
	// kg.s^-1
	private int thrust;
	// kg/s
	private int fuelFlow;
	// kg
	private int weight;
	// s
	private int isp;
	
	/**
	 * @param thrust the thrust of the engine in Kg/s
	 * @param fuelFlow the amont of fuel in kg used per second
	 * @param weight the weight of the engine
	 * @param isp the specific impulse of the engine
	 */
	public RocketEngine(int isp, int thrust, int fuelFlow, int weight) {
		this.isp = isp;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getisp() {
		return isp;
	}

	public void setIsp(int isp) {
		this.isp = isp;
	}
}
