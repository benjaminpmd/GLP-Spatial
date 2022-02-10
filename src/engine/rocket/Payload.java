package engine.rocket;


public class Payload {

	private String name;
	private int weight;

	/**
	 * @param name
	 * @param weight
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
