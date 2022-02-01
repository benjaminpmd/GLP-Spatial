package rocket;


public class Rocket {
	private String name;
	private Stage firstStage;
	private Stage secondStage;
	private Payload payload;
	private float weight;
	
	/**
	 * @param name
	 * @param firstStage
	 * @param secondStage
	 * @param payload
	 * @param weight
	 */
	public Rocket(String name, Stage firstStage, Stage secondStage, Payload payload) {
		this.name = name;
		this.firstStage = firstStage;
		this.secondStage = secondStage;
		this.payload = payload;
		weight = firstStage.getWeight()+secondStage.getWeight();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stage getFirstStage() {
		return firstStage;
	}

	public void setFirstStage(Stage firstStage) {
		this.firstStage = firstStage;
	}

	public Stage getSecondStage() {
		return secondStage;
	}

	public void setSecondStage(Stage secondStage) {
		this.secondStage = secondStage;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
