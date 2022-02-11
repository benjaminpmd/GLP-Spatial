package model.mission;

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
				", coordonates=" + coordinates +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coordinates getCoordonates() {
		return coordinates;
	}

	public void setCoordonates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
}
