package engine.process.management;

import config.SimConfig;
import data.mission.Mission;
import data.rocket.Rocket;
import data.rocket.Stage;
import data.rocket.Tank;
import engine.data.Constants;
import engine.process.calculations.Calculation;

/**
 * Class that contains all methods to manage a rocket.
 *
 * @author Benjamin P
 * @version 22.02.17 - I like math (1.0.0)
 * @since 14.02.22
 */
public class MissionManager {

    private static final MissionManager instance = new MissionManager();
    private final double DELTA_TIME = (SimConfig.DELTA_TIME);
    private Simulation simulation;
    private Mission mission;
    private final Calculation calculation;

    // private constructor
    private MissionManager() {
        calculation = new Calculation();
        simulation = new Simulation();
    }

    public static MissionManager getInstance() {
        return instance;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Method that updates the rocket. It removes the used propellant mass and manage stage once the tank is empty.
     */
    public void updateRocket() {
        Rocket rocket = mission.getRocket();
        Stage stage;
        Tank tank;
        if ((rocket.getFirstStage() != null) || (rocket.getSecondStage() != null)) {
            if ((rocket.getFirstStage() != null)) {
                stage = rocket.getFirstStage();
            } else {
                stage = rocket.getSecondStage();
            }
            tank = stage.getTank();
            double remainingPropellant = tank.getRemainingPropellant();
            double usedPropellant = (stage.getEngineNb() * stage.getEngine().getPropellantFlow()) * DELTA_TIME;
            if ((remainingPropellant - usedPropellant) > 0) {
                tank.setRemainingPropellant(remainingPropellant - usedPropellant);
            } else {
                if (stage.equals(rocket.getFirstStage())) {
                    rocket.setFirstStage(null);
                } else {
                    rocket.setSecondStage(null);
                }
            }
        }
    }

    public void updateRocketPosition() {
        // TODO: Improve to 2D trajectory
        Rocket rocket = mission.getRocket();
        double acceleration = calculation.accelerationFromThrust(rocket.getWeight(), rocket.getThrust());
        double velocity = calculation.velocityFromAcceleration(acceleration, rocket.getVelocity(), DELTA_TIME);
        double altitude = calculation.positionFromVelocity(velocity, rocket.getCoordinates().getR(), DELTA_TIME);
        System.out.println(altitude - Constants.EARTH_RADIUS);
        if (altitude >= Constants.EARTH_RADIUS) {
            rocket.getCoordinates().setR(altitude);
        }
        simulation.addAcceleration((int) acceleration);
        simulation.addVelocity((int) velocity);
        simulation.addTrajectory(calculation.polarToCartesian(rocket.getCoordinates()));
    }

    /**
     * Get the altitude of the rocket from the earth surface
     * @return a double, the rocket altitude
     */
    public double getRocketAltitude() {
        return mission.getRocket().getCoordinates().getR() - Constants.EARTH_RADIUS;
    }

    public void next() {
        updateRocket();
        updateRocketPosition();
    }
}
