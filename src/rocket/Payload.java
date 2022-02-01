package rocket;


public class Payload {

	private String name;
	private String type;
	private float weight;

	/**
	 * @param name
	 * @param type
	 * @param weight
	 */
	public Payload(String name, String type, float weight) {
		this.name = name;
		this.type = type;
		this.weight = weight;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
