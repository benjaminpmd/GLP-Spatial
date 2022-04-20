package data.mission;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The mission object contains all information like destination of the mission, orbit target or the center where the
 * rocket will be launched.
 *
 * @author Benjamin P
 * @version 22.03.06 (1.0.0)
 * @see SpaceCenter
 * @since 11.02.22
 */
public class Mission {

	private String name;
	private String description;
	private String spaceCenterName;
	private String destinationName;
	private int orbitAltitude;
	private Date launchTime;

	/**
	 * Constructor of a mission.
	 */
	public Mission(String spaceCenterName, String destinationName, int orbitAltitude, String name, String description, Date launchTime) {
		this.name = name;
		this.description = description;
		this.spaceCenterName = spaceCenterName;
		this.destinationName = destinationName;
		this.orbitAltitude = orbitAltitude;
		this.launchTime = launchTime;
	}

	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", spaceCenterName='" + spaceCenterName + '\'' +
				", destinationName='" + destinationName + '\'' +
				", orbitAltitude=" + orbitAltitude +
				", launchTime=" + launchTime +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Mission)) return false;

		Mission mission = (Mission) o;

		if (orbitAltitude != mission.orbitAltitude) return false;
		if (name != null ? !name.equals(mission.name) : mission.name != null) return false;
		if (description != null ? !description.equals(mission.description) : mission.description != null) return false;
		if (spaceCenterName != null ? !spaceCenterName.equals(mission.spaceCenterName) : mission.spaceCenterName != null)
			return false;
		if (destinationName != null ? !destinationName.equals(mission.destinationName) : mission.destinationName != null)
			return false;
		return launchTime != null ? launchTime.equals(mission.launchTime) : mission.launchTime == null;
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

	public Date getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
}
