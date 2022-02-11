package model.rocket;

/**
 * Class that contains informations about the payload
 * @author Alice M, Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 */
public class Payload {

	private String name;
	//kg
	private int weight;

	/**
	 * @param name Most of the time the name of the mission
	 * @param weight Weight (kg) of the payload
	 */
	public Payload(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Payload{" +
				"name='" + name + '\'' +
				", weight=" + weight +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
