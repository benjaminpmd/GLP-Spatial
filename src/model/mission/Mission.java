package model.mission;

import engine.data.Coordinates;
import model.rocket.Rocket;

import java.util.Date;

/**
 * The mission class is the center core of the engine, all important information like target of the mission, rocket etc.
 * are stored in this object.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see model.rocket.Rocket
 * @see model.mission.Center
 * @see engine.data.Coordinates
 * @since 11.02.22
 */
public class Mission {

	private String name;
	private String description;
	private Date launchTime;
	private Center center;
	private Rocket rocket;
	private Coordinates rocketCoordinates;
	private Planet earth;
	private Target target;

	public Mission(String name, String description, Date launchTime, Center center, Rocket rocket, Coordinates rocketCoordinates, Planet earth, Target target) {
		this.name = name;
		this.description = description;
		this.launchTime = launchTime;
		this.center = center;
		this.rocket = rocket;
		this.rocketCoordinates = rocketCoordinates;
		this.earth = earth;
		this.target = target;
	}

	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", launchTime=" + launchTime +
				", center=" + center +
				", rocket=" + rocket +
				", rocketCoordinates=" + rocketCoordinates +
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

	public Rocket getRocket() {
		return rocket;
	}

	public void setRocket(Rocket rocket) {
		this.rocket = rocket;
	}

	public Coordinates getRocketCoordinates() {
		return rocketCoordinates;
	}

	public void setRocketCoordinates(Coordinates rocketCoordinates) {
		this.rocketCoordinates = rocketCoordinates;
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
