package data.mission;

/**
 * Target contains the planet and the altitude of the orbit targeted, the planet can be the earth in the case of the mission
 * targets an earth orbit.
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see data.mission.Planet
 * @since 11.02.22
 */
public class Target {
    private int altitude;
    private Planet planet;

    public Target(int altitude, Planet planet) {
        this.altitude = altitude;
        this.planet = planet;
    }

    @Override
    public String toString() {
        return "Target{" +
                "altitude=" + altitude +
                ", planet=" + planet +
                '}';
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }
}
