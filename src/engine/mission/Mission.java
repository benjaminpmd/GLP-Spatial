package engine.mission;

public class Mission {

	private String description;
	private SpaceCenter spaceCenter;
	private float orbit;
	private String launchTime;
	
	/**
	 * @param spaceCenter
	 * @param rocket
	 * @param orbit
	 * @param launchTime
	 * @param description
	 */
	public Mission(SpaceCenter spaceCenter, float orbit, String launchTime, String description) {
		this.spaceCenter = spaceCenter;
		this.orbit = orbit;
		this.launchTime = launchTime;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Mission{" +
				"description='" + description + '\'' +
				", spaceCenter=" + spaceCenter +
				", orbit=" + orbit +
				", launchTime='" + launchTime + '\'' +
				'}';
	}

	public SpaceCenter getSpaceCenter() {
		return spaceCenter;
	}

	public void setSpaceCenter(SpaceCenter spaceCenter) {
		this.spaceCenter = spaceCenter;
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
