package data.mission;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The mission object contains all information like target of the mission, the center where the rocket will be launched.
 *
 * @author Benjamin P
 * @version 22.03.06
 * @see SpaceCenter
 * @since 11.02.22
 */
public class Mission {

	private String name;
	private String description;
	private SpaceCenter spaceCenter;
	private Target target;
	private Date launchTime;

	public Mission(SpaceCenter spaceCenter, Target target) {
		this.spaceCenter = spaceCenter;
		this.target = target;
		name = "mission-" + new SimpleDateFormat("yyMMddmmss").format(new Date());
		description = "No description provided.";
		launchTime = new Date();
	}

	public Mission(String name, SpaceCenter spaceCenter, Target target) {
		this.name = name;
		this.spaceCenter = spaceCenter;
		this.target = target;
		description = "No description provided.";
		launchTime = new Date();
	}

	public Mission(String name, String description, SpaceCenter spaceCenter, Target target) {
		this.name = name;
		this.description = description;
		this.spaceCenter = spaceCenter;
		this.target = target;
		launchTime = new Date();
	}

	public Mission(String name, String description, SpaceCenter spaceCenter, Target target, Date launchTime) {
		this.name = name;
		this.description = description;
		this.spaceCenter = spaceCenter;
		this.target = target;
		this.launchTime = launchTime;
	}

	@Override
	public String toString() {
		return "Mission{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", spaceCenter=" + spaceCenter +
				", target=" + target +
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

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Date getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Date launchTime) {
		this.launchTime = launchTime;
	}
}
