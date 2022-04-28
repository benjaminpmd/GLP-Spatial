package data.mission;

/**
 * The mission object contains all information like destination of the mission, orbit target or the center where the
 * rocket will be launched.
 *
 * @see data.mission.SpaceCenter
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class Mission {

	private String name;
	private String description;
	private String spaceCenterName;
	private String destinationName;
	private int orbitAltitude;

	/**
	 * Constructor of a mission.
	 */
	public Mission(String spaceCenterName, String destinationName, int orbitAltitude, String name, String description) {
		this.name = name;
		this.description = description;
		this.spaceCenterName = spaceCenterName;
		this.destinationName = destinationName;
		this.orbitAltitude = orbitAltitude;
	}

	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", spaceCenterName='" + spaceCenterName + '\'' +
				", destinationName='" + destinationName + '\'' +
				", orbitAltitude=" + orbitAltitude +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpaceCenterName() {
		return spaceCenterName;
	}

	public void setSpaceCenterName(String spaceCenterName) {
		this.spaceCenterName = spaceCenterName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public int getOrbitAltitude() {
		return orbitAltitude;
	}

	public void setOrbitAltitude(int orbitAltitude) {
		this.orbitAltitude = orbitAltitude;
	}
}
