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
	private SpaceCenter spaceCenter;
	private CelestialObject destination;
	private int orbitAltitude;
	private Date launchTime;

	public Mission(SpaceCenter spaceCenter, CelestialObject destination, int orbitAltitude) {
		this.spaceCenter = spaceCenter;
		this.destination = destination;
		this.orbitAltitude = orbitAltitude;
		name = "mission-" + new SimpleDateFormat("yyMMddmmss").format(new Date());
		description = "No description provided.";
		launchTime = new Date();
	}

	public Mission(SpaceCenter spaceCenter, CelestialObject destination, int orbitAltitude, String name) {
		this.name = name;
		this.spaceCenter = spaceCenter;
		description = "No description provided.";
		launchTime = new Date();
	}

	public Mission(SpaceCenter spaceCenter, CelestialObject destination, int orbitAltitude, String name, String description) {
		this.name = name;
		this.description = description;
		this.spaceCenter = spaceCenter;
		launchTime = new Date();
	}

	public Mission(SpaceCenter spaceCenter, CelestialObject destination, int orbitAltitude, String name, String description, Date launchTime) {
		this.name = name;
		this.description = description;
		this.spaceCenter = spaceCenter;
		this.launchTime = launchTime;
	}

	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", spaceCenter=" + spaceCenter +
				", destination=" + destination +
				", orbitAltitude=" + orbitAltitude +
				", launchTime=" + launchTime +
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

	public SpaceCenter getSpaceCenter() {
		return spaceCenter;
	}

	public void setSpaceCenter(SpaceCenter spaceCenter) {
		this.spaceCenter = spaceCenter;
	}

	public CelestialObject getDestination() {
		return destination;
	}

	public void setDestination(CelestialObject destination) {
		this.destination = destination;
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
