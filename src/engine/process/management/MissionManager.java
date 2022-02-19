package engine.process.management;

import config.SimConfig;
import data.mission.Mission;
import data.rocket.Rocket;
import data.rocket.Stage;
import data.rocket.Tank;
import engine.data.CartesianCoordinates;
import engine.process.calculations.Calculation;

import java.util.ArrayList;

/**
 * Class that contains all methods to manage a rocket.
 *
 * @author Benjamin P
 * @version 22.02.17 - I like math (1.0.0)
 * @since 14.02.22
 */
public class MissionManager {

    private static MissionManager instance = new MissionManager();
    private Mission mission;
    private Rocket rocket;
    private Calculation calculation;
    private ArrayList<CartesianCoordinates>trajectory;
    private final double DELTA_TIME = (SimConfig.SIMULATION_SPEED / 1000);

    // private constructor
    private MissionManager() {
        calculation = new Calculation();
        trajectory = new ArrayList<CartesianCoordinates>();
    }

    public static MissionManager getInstance() {
        return instance;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Mission getMission() {
        return mission;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Rocket getRocket() {
        return rocket;
    }

    /**
     * Method that updates the rocket. It removes the used propellant mass and manage stage once the tank is empty.
     */
    public void updateRocket() {
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
        double a = calculation.accelerationFromThrust(rocket.getWeight(), rocket.getThrust());
        double v = calculation.velocityFromAcceleration(a, rocket.getVelocity(), DELTA_TIME);
        double x = calculation.positionFromVelocity(v, rocket.getCoordinates().getR(), DELTA_TIME);
        rocket.getCoordinates().setR(x);
        trajectory.add(calculation.polarToCartesian(rocket.getCoordinates()));
    }

    public void next() {
        updateRocket();
        updateRocketPosition();
    }
}
