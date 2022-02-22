package engine.process.management;

import engine.data.CartesianCoordinates;

import java.util.ArrayList;

public class Simulation {

    private ArrayList<CartesianCoordinates>trajectories = new ArrayList<CartesianCoordinates>();
    private ArrayList<Integer>altitudes = new ArrayList<Integer>();
    private ArrayList<Integer>velocities = new ArrayList<Integer>();
    private ArrayList<Integer>accelerations = new ArrayList<Integer>();

    public ArrayList<CartesianCoordinates> getTrajectories() {
        return trajectories;
    }

    public ArrayList<Integer> getVelocities() {
        return velocities;
    }

    public ArrayList<Integer> getAccelerations() {
        return accelerations;
    }

    public ArrayList<Integer> getAltitudes() {
        return altitudes;
    }

    public void addTrajectory(CartesianCoordinates cartesianCoordinates) {
        trajectories.add(cartesianCoordinates);
    }

    public void addVelocity(int velocity) {
        velocities.add(velocity);
    }

    public void addAcceleration(int acceleration) {
        accelerations.add(acceleration);
    }

    public void addAltitude(int altitude) {
        altitudes.add(altitude);
    }
}
