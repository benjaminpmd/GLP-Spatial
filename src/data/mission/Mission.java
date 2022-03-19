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

	public Mission(String spaceCenterName, String destinationName, int orbitAltitude) {
		this.spaceCenterName = spaceCenterName;
		this.destinationName = destinationName;
		this.orbitAltitude = orbitAltitude;
		name = "mission-" + new SimpleDateFormat("yyMMddmmss").format(new Date());
		description = "No description provided.";
		launchTime = new Date();
	}

	public Mission(String spaceCenterName, String destination, int orbitAltitude, String name) {
		this.name = name;
		this.spaceCenterName = spaceCenterName;
		description = "No description provided.";
		launchTime = new Date();
	}

	public Mission(String spaceCenterName, String destinationName, int orbitAltitude, String name, String description) {
		this.name = name;
		this.description = description;
		this.spaceCenterName = spaceCenterName;
		launchTime = new Date();
	}

	public Mission(String spaceCenterName, String destinationName, int orbitAltitude, String name, String description, Date launchTime) {
		this.name = name;
		this.description = description;
		this.spaceCenterName = spaceCenterName;
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

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (spaceCenterName != null ? spaceCenterName.hashCode() : 0);
		result = 31 * result + (destinationName != null ? destinationName.hashCode() : 0);
		result = 31 * result + orbitAltitude;
		result = 31 * result + (launchTime != null ? launchTime.hashCode() : 0);
		return result;
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
