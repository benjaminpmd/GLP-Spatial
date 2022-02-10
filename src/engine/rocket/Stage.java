package engine.rocket;

import javax.swing.Icon;

public class Stage {
	
	private int emptyWeight;
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

	@Override
	public String toString() {
		return "Stage{" +
				"emptyWeight=" + emptyWeight +
				", engineNb=" + engineNb +
				", engine=" + engine +
				", fuelTank=" + fuelTank +
				", icon=" + icon +
				'}';
	}

	public int getEmptyWeight() {
		return emptyWeight;
	}

	public void setEmptyWeight(int emptyWeight) {
		this.emptyWeight = emptyWeight;
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
