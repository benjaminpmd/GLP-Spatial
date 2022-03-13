package process.management;

import java.util.ArrayList;

public class TelemetryRecord {

    private ArrayList<Integer>altitudes = new ArrayList<Integer>();
    private ArrayList<Integer>velocities = new ArrayList<Integer>();
    private ArrayList<Integer>accelerations = new ArrayList<Integer>();

    public ArrayList<Integer> getAltitudes() {
        return altitudes;
    }

    public ArrayList<Integer> getVelocities() {
        return velocities;
    }

    public ArrayList<Integer> getAccelerations() {
        return accelerations;
    }

    public void addAltitude(int altitude) {
        altitudes.add(altitude);
    }

    public void addVelocity(int velocity) {
        velocities.add(velocity);
    }

    public void addAcceleration(int acceleration) {
        accelerations.add(acceleration);
    }
}
