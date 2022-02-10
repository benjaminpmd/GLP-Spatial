package engine.mission;

public class SpaceCenter {
	
	private String name;
	private Coordonates coordonates;
	
	/**
	 * @param name The name of the space center
	 * @param coordonates location of the space center
	 */
	public SpaceCenter(String name, Coordonates coordonates) {
		this.name = name;
		this.coordonates = coordonates;
	}

	@Override
	public String toString() {
		return "SpaceCenter{" +
				"name='" + name + '\'' +
				", coordonates=" + coordonates +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coordonates getCoordonates() {
		return coordonates;
	}

	public void setCoordonates(Coordonates coordonates) {
		this.coordonates = coordonates;
	}
}
