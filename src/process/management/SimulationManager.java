package process.management;

import config.Constants;
import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import data.mission.Mission;
import data.rocket.Rocket;
import data.rocket.Stage;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.builders.CelestialObjectBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Manager of the simulation. This class updates the rocket mass given the delta time for each update. This manager also
 * manage various rocket elements like stage separation and move them in a List to be displayed separated from the rocket.
 * This class also store the celestial objects that will interact with the rocket, the Earth and the destination object
 * if it is not earth.
 *
 * @author Benjamin P
 * @version 22.02.17 (0.4.0 WIP)
 * @see data.mission.CelestialObject
 * @see data.rocket.Rocket
 * @see data.rocket.Stage
 * @since 14.02.22
 */
public class SimulationManager {

    private final Logger logger = LoggerUtility.getLogger(SimulationManager.class, "html");
    private final double launchAngle;
    private final Calculation calculation;
    private final TelemetryRecord telemetry;
    private final Mission mission;
    private final Rocket rocket;
    private final List<CartesianCoordinate> coordinatesHistory = new ArrayList<>(); // record the last 255 postions of the rocket.
    private final List<CartesianCoordinate> trajectoryHistory = new ArrayList<>(); // record path of the rocket.
    private final HashMap<String, CelestialObject> celestialObjects = new HashMap<String, CelestialObject>(); // celestial objects that will interact with the mission.
    private final List<Stage> releasedStages = new ArrayList<>(); // separated stages
    private double deltaTime;
    private int rocketConfig;
    private boolean escapeEarth;
    private double altitude;
    private int recordCycle;

    /**
     * Constructor of the simulation manager.
     *
     * @param rocket                 The rocket to use.
     * @param mission                The mission.
     * @param celestialObjectBuilder The builder for earth and possible destination (if not earth orbit).
     */
    public SimulationManager(Rocket rocket, Mission mission, CelestialObjectBuilder celestialObjectBuilder) {
        this.rocket = rocket;
        this.mission = mission;

        calculation = new Calculation();
        telemetry = new TelemetryRecord();

        altitude = 0;
        recordCycle = 0;

        deltaTime = SimConfig.DELTA_TIME;
        rocketConfig = 2;
        escapeEarth = false;


        celestialObjects.put("Earth", celestialObjectBuilder.buildCelestialObject("Earth"));

        if (!mission.getDestinationName().equals("Earth")) {
            CelestialObject destination = celestialObjectBuilder.buildCelestialObject(mission.getDestinationName());
            celestialObjects.put(destination.getName(), destination);
            launchAngle = calculation.calculateLaunchAngle(2000, false, celestialObjects.get("Earth"));

        } else {
            launchAngle = calculation.calculateLaunchAngle(mission.getOrbitAltitude(), true, celestialObjects.get("Earth"));
        }
    }

    public Rocket getRocket() {
        return rocket;
    }

    public Mission getMission() {
        return mission;
    }

    public TelemetryRecord getTelemetry() {
        return telemetry;
    }

    public List<CartesianCoordinate> getCoordinateHistory() {
        return coordinatesHistory;
    }

    public HashMap<String, CelestialObject> getCelestialObjects() {
        return celestialObjects;
    }

    public List<Stage> getReleasedStages() {
        return releasedStages;
    }

    public List<CartesianCoordinate> getTrajectoryHistory() {
        return trajectoryHistory;
    }

    public double getAltitude() {
        return altitude;
    }

    public int getRocketConfig() {
        return rocketConfig;
    }

    /**
     * Trigger all methods to update data using calculations.
     */
    public void next() {

        // rocket update
        updateRocketMass();
        updateRocket();

        // released stages
        updateReleasedStagesPosition();

        // telemetry
        updateCoordinateHistory();
        updateTrajectoryHistory();

        // delta time
        updateDeltaTime();
    }

    /**
     * Start the launch sequence by igniting the first stage engine(s).
     */
    public void launch() {
        rocket.getFirstStage().setFiring(true);
        rocketConfig = 1;
    }

    /**
     * Update the delta time depending on the rocket position from the destination.
     */
    private void updateDeltaTime() {

        if (!mission.getDestinationName().equals("Earth")) {
            CelestialObject destination = celestialObjects.get(mission.getDestinationName());
            double distanceFromDestination = calculation.calculateDistance(rocket.getCartesianCoordinate(), destination.getCartesianCoordinate()) - destination.getRadius();

            if ((altitude > 10000000) && (distanceFromDestination > 10000000)) {
                deltaTime = 86400;
            } else {
                deltaTime = 1;
            }
        }
    }

    /**
     * Method that updates the rocket mass. It removes the used propellant mass and manage stage once the tank is empty.
     */
    private void updateRocketMass() {

        Stage stage;
        if ((rocket.getFirstStage() != null) && (rocket.getFirstStage().isFiring())) {

            stage = rocket.getFirstStage();

            double propellantFlow = (stage.getEngine().getPropellantFlow() * stage.getEngineNb()) * deltaTime;
            double remainingPropellant = stage.getTank().getRemainingPropellant() - propellantFlow;

            if (remainingPropellant >= 0) {
                stage.getTank().setRemainingPropellant(remainingPropellant);

            } else {
                stage.setFiring(false);

                CartesianCoordinate coordinate = new CartesianCoordinate();
                coordinate.setX(rocket.getCartesianCoordinate().getX());
                coordinate.setY(rocket.getCartesianCoordinate().getY());

                stage.setCartesianCoordinate(coordinate);
                stage.setVelocity(rocket.getVelocity());
                releasedStages.add(rocket.getFirstStage());

                rocket.setFirstStage(null);
                rocket.getSecondStage().setFiring(true);
                rocketConfig = 3;
                logger.trace("Ignition of second stage at " + altitude);
            }

        } else if ((rocket.getSecondStage() != null) && (rocket.getSecondStage().isFiring())) {

            stage = rocket.getSecondStage();

            double propellantFlow = (stage.getEngine().getPropellantFlow() * stage.getEngineNb()) * deltaTime;
            double remainingPropellant = stage.getTank().getRemainingPropellant() - propellantFlow;

            if (remainingPropellant >= 0) {
                stage.getTank().setRemainingPropellant(remainingPropellant);

            } else {
                stage.setFiring(false);

                CartesianCoordinate coordinate = new CartesianCoordinate();
                coordinate.setX(rocket.getCartesianCoordinate().getX());
                coordinate.setY(rocket.getCartesianCoordinate().getY());

                stage.setCartesianCoordinate(coordinate);
                stage.setVelocity(rocket.getVelocity());
                releasedStages.add(rocket.getSecondStage());

                rocket.setSecondStage(null);
                rocketConfig = 5;
                logger.trace("Stage 2 released at " + altitude);
            }
        }
    }

    /**
     * Update the rocket elements. This method controls the firing of the second stage to achieve proper orbital insertion
     * or good escape of earth gravitation.
     */
    private void updateRocket() {

        CelestialObject nearestObject = celestialObjects.get("Earth");
        CelestialObject destination = celestialObjects.get(mission.getDestinationName());

        // comparing distances from objects in the list to get the nearest object.
        for (CelestialObject celestialObject : celestialObjects.values()) {
            if (calculation.calculateDistance(celestialObject.getCartesianCoordinate(), rocket.getCartesianCoordinate()) < calculation.calculateDistance(nearestObject.getCartesianCoordinate(), rocket.getCartesianCoordinate())) {
                nearestObject = celestialObject;
            }
        }

        // altitude relative to the nearest object.
        altitude = calculation.calculateAltitude(rocket.getCartesianCoordinate(), nearestObject);

        // acceleration of the rocket
        double acceleration = (calculation.calculateRocketAcceleration(rocket, nearestObject, altitude, deltaTime) / deltaTime);

        // TODO: TO BE MOVED IN THE VELOCITY CALCULATION METHOD
        if ((mission.getDestinationName().equals("Earth")) && (acceleration < 0)) {
            acceleration = 0;
        } else if ((!escapeEarth) && (!mission.getDestinationName().equals("Earth")) && (acceleration < 0)) {
            acceleration = 0;
        }
        // END OF TEMP
        double velocity = (calculation.calculateVelocity(rocket.getVelocity(), acceleration, deltaTime) / deltaTime);

        rocket.setVelocity(velocity);

        CartesianCoordinate rocketCoordinate = rocket.getCartesianCoordinate();

        // Here is control of second stage firing.
        // In real life, not all engines are able to start multiple time, to simplify operations
        // we will assume it is the case here.
        if (rocket.getSecondStage() != null) {

            if (!mission.getDestinationName().equals("Earth")) {
                if ((velocity > calculation.calculateMiniOrbitalVelocity(nearestObject)) && (rocket.getSecondStage().isFiring()) && (!escapeEarth)) {
                    logger.trace("SECO");
                    rocketConfig = 4;
                    rocket.getSecondStage().setFiring(false);
                }
                if ((rocketCoordinate.getX() < 15000) && (rocketCoordinate.getX() > -15000) && (rocketCoordinate.getY() > 0)) {
                    if ((velocity > calculation.calculateMiniOrbitalVelocity(nearestObject)) && (!rocket.getSecondStage().isFiring())) {
                        escapeEarth = true;
                        rocket.getSecondStage().setFiring(true);
                        logger.trace("SEI");
                        rocketConfig = 3;
                    }
                }
            } else {
                if (velocity > calculation.calculateMiniOrbitalVelocity(nearestObject)) {
                    if ((rocket.getFirstStage() == null) && (altitude >= mission.getOrbitAltitude())) {
                        rocket.getSecondStage().setFiring(false);
                        releasedStages.add(rocket.getSecondStage());
                        rocket.setSecondStage(null);
                    }
                } else {
                    rocket.getSecondStage().setFiring(true);
                }
            }
        }

        rocket.setCartesianCoordinate(calculation.calculateRocketPosition(rocket, destination, mission.getOrbitAltitude(), altitude, launchAngle, deltaTime, escapeEarth));

        updateTelemetry((int) acceleration, (int) velocity);
    }

    /**
     * Update the telemetry for instruments.
     *
     * @param acceleration Acceleration in m.s^-2.
     * @param velocity     Velocity in m.s^-1.
     */
    private void updateTelemetry(int acceleration, int velocity) {
        telemetry.addAcceleration(acceleration);
        telemetry.addVelocity(velocity);
    }

    /**
     * Update the last 510 positions of the rocket
     */
    private void updateCoordinateHistory() {
        if (coordinatesHistory.size() >= 510) {
            coordinatesHistory.remove(0);
        }
        CartesianCoordinate coordinate = new CartesianCoordinate(rocket.getCartesianCoordinate().getX(), rocket.getCartesianCoordinate().getY());

        coordinatesHistory.add(coordinate);
    }

    /**
     * Update the cycle of recording.
     */
    private void updateTrajectoryHistory() {

        if (recordCycle == 1000) {

            CartesianCoordinate tempCoordinate = coordinatesHistory.get(0);
            CartesianCoordinate coordinate = new CartesianCoordinate(tempCoordinate.getX(), tempCoordinate.getY());
            trajectoryHistory.add(coordinate);
            recordCycle = 0;

        } else recordCycle++;
    }

    /**
     * Update stages velocities and positions
     */
    private void updateReleasedStagesPosition() {

        for (int i = 0; i < releasedStages.size(); i++) {
            Stage stage = releasedStages.get(i);

            double velocity = calculation.calculateStageVelocity(stage, celestialObjects.get("Earth"));
            stage.setVelocity(velocity);

            CartesianCoordinate newCoordinate = calculation.calculateStagePosition(stage, celestialObjects.get("Earth"), launchAngle, deltaTime);
            PolarCoordinate tempCoordinate = calculation.cartesianToPolar(stage.getCartesianCoordinate());

            if ((tempCoordinate.getR() <= Constants.EARTH_RADIUS) || (tempCoordinate.getR() >= Constants.EARTH_RADIUS + 40000000)) {
                releasedStages.remove(i);
            } else {
                stage.setCartesianCoordinate(newCoordinate);
            }
        }
    }
}
