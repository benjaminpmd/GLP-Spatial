package engine.rocket;


public class Payload {

	private String name;
	private float weight;

	/**
	 * @param name
	 * @param weight
	 */
	public Payload(String name, float weight) {
		this.name = name;
		this.weight = weight;
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
