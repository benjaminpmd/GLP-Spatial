package rocket;

import javax.swing.Icon;

public class Stage {
	
	private float weight;
	private int engineNb;
	private RocketEngine engine;
	private Tank fuelTankOne;
	private Tank fuelTankTwo;
	private Icon icon;
	

	/**
	 * @param engineNb
	 * @param engine
	 * @param fuelTankOne
	 * @param fuelTankTwo
	 */
	public Stage(int engineNb, RocketEngine engine, Tank fuelTankOne, Tank fuelTankTwo) {
		this.engineNb = engineNb;
		this.engine = engine;
		this.fuelTankOne = fuelTankOne;
		this.fuelTankTwo = fuelTankTwo;
	}



	public float getWeight() {
		return weight;
	}



	public void setWeight(float weight) {
		this.weight = weight;
	}



	public int getEngineNb() {
		return engineNb;
	}



	public void setEngineNb(int engineNb) {
		this.engineNb = engineNb;
	}



	public RocketEngine getEngine() {
		return engine;
	}



	public void setEngine(RocketEngine engine) {
		this.engine = engine;
	}



	public Tank getFuelTankOne() {
		return fuelTankOne;
	}



	public void setFuelTankOne(Tank fuelTankOne) {
		this.fuelTankOne = fuelTankOne;
	}



	public Tank getFuelTankTwo() {
		return fuelTankTwo;
	}



	public void setFuelTankTwo(Tank fuelTankTwo) {
		this.fuelTankTwo = fuelTankTwo;
	}



	public Icon getIcon() {
		return icon;
	}



	public void setIcon(Icon icon) {
		this.icon = icon;
	}




}
