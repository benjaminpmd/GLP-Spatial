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
    private int orbit;
    private Planet planet;

    public Target(Planet planet) {
        this.orbit = 0;
        this.planet = planet;
    }

    public Target(int orbit) {
        this.orbit = orbit;
        this.planet = null;
    }

    @Override
    public String toString() {
        return "Target{" +
                "altitude=" + orbit +
                ", planet=" + planet +
                '}';
    }

    public int getOrbit() {
        return orbit;
    }

    public void setAltitude(int orbit) {
        this.orbit = orbit;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }
}
