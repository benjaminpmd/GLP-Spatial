package engine.rocket;

import javax.swing.Icon;

public class Stage {
	
	private float weight;
	private int engineNb;
	private RocketEngine engine;
	private Tank fuelTank;
	private Icon icon;
	
	/**
	 * @param engineNb
	 * @param engine
	 * @param fuelTank
	 */
	public Stage(int engineNb, RocketEngine engine, Tank fuelTank) {
		this.engineNb = engineNb;
		this.engine = engine;
		this.fuelTank = fuelTank;
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

	public Tank getFuelTank() {
		return fuelTank;
	}

	public void setFuelTank(Tank fuelTank) {
		this.fuelTank = fuelTank;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}
}
