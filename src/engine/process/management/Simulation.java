package engine.process.management;

import data.coordinate.CartesianCoordinate;

import java.util.ArrayList;

public class Simulation {

    private ArrayList<CartesianCoordinate>trajectories = new ArrayList<CartesianCoordinate>();
    private ArrayList<Integer>velocities = new ArrayList<Integer>();
    private ArrayList<Integer>accelerations = new ArrayList<Integer>();

    public ArrayList<CartesianCoordinate> getTrajectories() {
        return trajectories;
    }

    public ArrayList<Integer> getVelocities() {
        return velocities;
    }

    public ArrayList<Integer> getAccelerations() {
        return accelerations;
    }

    public void addTrajectory(CartesianCoordinate cartesianCoordinate) {
        trajectories.add(cartesianCoordinate);
    }

    public void addVelocity(int velocity) {
        velocities.add(velocity);
    }

    public void addAcceleration(int acceleration) {
        accelerations.add(acceleration);
    }
}
