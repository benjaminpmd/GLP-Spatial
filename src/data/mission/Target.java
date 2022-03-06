package data.mission;

/**
 * Target contains the celestialObject and the altitude of the orbit targeted, the celestialObject can be the earth in
 * the case of the mission targets an earth orbit.
 *
 * @author Benjamin P
 * @version 22.03.06
 * @see CelestialObject
 * @since 11.02.22
 */
public class Target {

    // in m
    private int orbit;
    private CelestialObject celestialObject;

    public Target(CelestialObject celestialObject, int orbit) {
        this.orbit = orbit;
        this.celestialObject = celestialObject;
    }

    @Override
    public String toString() {
        return "Target{" +
                "altitude=" + orbit +
                ", celestialObject=" + celestialObject +
                '}';
    }

    public int getOrbit() {
        return orbit;
    }

    public void setAltitude(int orbit) {
        this.orbit = orbit;
    }

    public CelestialObject getPlanet() {
        return celestialObject;
    }

    public void setPlanet(CelestialObject celestialObject) {
        this.celestialObject = celestialObject;
    }
}
