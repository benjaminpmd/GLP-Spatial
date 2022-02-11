package model.rocket;

// TODO: make the rocket singleton

/**
 * Class that contains informations about the rocket
 * @author Alice M, Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 * @see {@link Stage}, {@link Payload}
 */
public class Rocket {

	private Stage firstStage;
	private Stage secondStage;
	private Payload payload;

	@Override
	public String toString() {
		return "Rocket{" +
				"firstStage=" + firstStage +
				", secondStage=" + secondStage +
				", payload=" + payload +
				'}';
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
}
