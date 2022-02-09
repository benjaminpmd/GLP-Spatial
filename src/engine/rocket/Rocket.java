package engine.rocket;


public class Rocket {

	private Stage firstStage;
	private Stage secondStage;
	private Payload payload;

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
	
}
