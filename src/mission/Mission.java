package mission;

import rocket.Rocket;

public class Mission {
	
	private SpaceCenter spaceCenter;
	private Rocket rocket;
	private float orbit;
	private String launchTime;
	private String description;
	
	/**
	 * @param spaceCenter
	 * @param rocket
	 * @param orbit
	 * @param launchTime
	 * @param description
	 */
	public Mission(SpaceCenter spaceCenter, Rocket rocket, float orbit, String launchTime, String description) {
		this.spaceCenter = spaceCenter;
		this.rocket = rocket;
		this.orbit = orbit;
		this.launchTime = launchTime;
		this.description = description;
	}

	public SpaceCenter getSpaceCenter() {
		return spaceCenter;
	}

	public void setSpaceCenter(SpaceCenter spaceCenter) {
		this.spaceCenter = spaceCenter;
	}

	public Rocket getRocket() {
		return rocket;
	}

	public void setRocket(Rocket rocket) {
		this.rocket = rocket;
	}

	public float getOrbit() {
		return orbit;
	}

	public void setOrbit(float orbit) {
		this.orbit = orbit;
	}

	public String getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(String launchTime) {
		this.launchTime = launchTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
