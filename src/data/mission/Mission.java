package data.mission;

import engine.data.Constants;
import engine.data.PolarCoordinates;
import data.rocket.Rocket;

import java.util.Date;

/**
 * The mission class is the center core of the engine, all important information like target of the mission, rocket etc.
 * are stored in this object.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see data.rocket.Rocket
 * @see data.mission.Center
 * @see engine.data.PolarCoordinates
 * @since 11.02.22
 */
public class Mission {

	private String name;
	private String description;
	private Date launchTime;
	private Center center;
	private Planet earth;
	private Target target;

	public Mission(String name, String description, Date launchTime, Center center, Target target) {
		this.name = name;
		this.description = description;
		this.launchTime = launchTime;
		this.center = center;
		this.target = target;
		earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
	}

	public Mission(Center center, Target target) {
		this.center = center;
		this.target = target;
		earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
	}

	public Mission() {
		earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
	}

	public Mission(String name, Center center, Target target) {
		earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
	}


	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", launchTime=" + launchTime +
				", center=" + center +
				", earth=" + earth +
				", target=" + target +
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

	public Date getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public Planet getEarth() {
		return earth;
	}

	public void setEarth(Planet earth) {
		this.earth = earth;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}
