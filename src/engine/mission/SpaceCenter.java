package engine.mission;

public class SpaceCenter {
	
	private String name;
	private float[] localisation;
	
	/**
	 * @param name
	 * @param localisation
	 */
	public SpaceCenter(String name, float[] localisation) {
		this.name = name;
		this.localisation = localisation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float[] getLocalisation() {
		return localisation;
	}

	public void setLocalisation(float[] localisation) {
		this.localisation = localisation;
	}
	
}
