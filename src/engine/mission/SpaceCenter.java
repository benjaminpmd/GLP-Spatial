package engine.mission;

public class SpaceCenter {
	
	private String name;
	private Coordinates coordinates;
	
	/**
	 * @param name The name of the space center
	 * @param coordonates location of the space center
	 */
	public SpaceCenter(String name, Coordinates coordinates) {
		this.name = name;
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "SpaceCenter{" +
				"name='" + name + '\'' +
				", coordinates=" + coordinates +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
}
