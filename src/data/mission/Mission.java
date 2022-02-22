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
	private Rocket rocket;
	private Center center;
	private Planet earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
	private Target target;

	// TODO: invert, use this() with the smallest value input
	public Mission(String name, String description, Date launchTime, Center center, Target target, Rocket rocket) {
		this.name = name;
		this.description = description;
		this.launchTime = launchTime;
		this.center = center;
		this.target = target;
		this.rocket = rocket;
	}

	public Mission(String name, String description, Date launchTime, Center center, Target target) {
		this.name = name;
		this.description = description;
		this.launchTime = launchTime;
		this.center = center;
		this.target = target;
	}

	public Mission(String name, String description, Center center, Target target, Rocket rocket) {
		this.name = name;
		this.description = description;
		this.center = center;
		this.target = target;
		this.rocket = rocket;
	}

	public Mission(String name, Center center, Target target) {
		this(name, null, null, center, target);
	}

	public Mission(Center center, Target target) {
		this(null, null, null, center, target);
	}

	public Mission() {}

	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", launchTime=" + launchTime +
				", center=" + center +
				", earth=" + earth +
				", target=" + target +
				", rocket=" + rocket +
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

	public Rocket getRocket() {
		return rocket;
	}

	public void setRocket(Rocket rocket) {
		this.rocket = rocket;
	}
}
