package process.management;

import java.util.ArrayList;

/**
 * Class containing all the processes required to record and store the acceleration and velocity values of the rocket during the simulation. 
 * Used in {@link ChartPanel} and {@link SimulationGUI}.
 * 
 * @see gui.elements.ChartPanel
 * @see gui.SimulationGUI
 * @author Lucas L
 * @version 22.04.28 (1.0.0)
 * @since 22.02.22
 */
public class TelemetryRecord {

    private ArrayList<Integer>velocities = new ArrayList<Integer>();
    private ArrayList<Integer>accelerations = new ArrayList<Integer>();

    public ArrayList<Integer> getVelocities() {
        return velocities;
    }

    public ArrayList<Integer> getAccelerations() {
        return accelerations;
    }

    public void addVelocity(int velocity) {
        velocities.add(velocity);
    }

    public void addAcceleration(int acceleration) {
        accelerations.add(acceleration);
    }
}
