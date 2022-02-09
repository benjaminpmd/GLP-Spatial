package engine.mission;

public class Coordinates {
	
	// north
	private int latitude;
	
	// east
	private int longitude;

	/**
	 * @param latitude
	 * @param longitude
	 */
	public Coordinates(int latitude, int longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

}
