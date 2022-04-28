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
    private final Calculation calculation;
    private final TelemetryRecord telemetry;
    private final Mission mission;
    private final Rocket rocket;
    private volatile List<CartesianCoordinate> coordinatesHistory = new ArrayList<>(); // record the last 255 postions of the rocket.
    private volatile List<CartesianCoordinate> trajectoryHistory = new ArrayList<>(); // record path of the rocket.
    private final HashMap<String, CelestialObject> celestialObjects = new HashMap<String, CelestialObject>(); // celestial objects that will interact with the mission.
    private volatile List<Stage> releasedStages = new ArrayList<>(); // separated stages
    private double deltaTime;
    private int rocketConfig;
    private boolean escapeGravity;
    private boolean isOrbiting;
    private double altitude;
    private int recordCycle;
    private CelestialObject nearestObject;

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
        escapeGravity = false;
        isOrbiting = false;


        celestialObjects.put("Earth", celestialObjectBuilder.buildCelestialObject("Earth"));
        nearestObject = celestialObjects.get("Earth");

        if (!mission.getDestinationName().equals("Earth")) {
            CelestialObject destination = celestialObjectBuilder.buildCelestialObject(mission.getDestinationName());
            celestialObjects.put(destination.getName(), destination);
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

        // telemetry
        updateCoordinateHistory();
        updateTrajectoryHistory();

        // delta time
        updateDeltaTime();

        // released stages
        updateReleasedStagesPosition();
        updateNearestObject();

    }

    /**
     * Start the launch sequence by igniting the first stage engine(s).
     */
    public void launch() {
        rocket.getFirstStage().setFiring(true);
        rocketConfig = 1;
    }

    public boolean hasCrashed() {
        double distance = Math.abs(calculation.calculateDistance(rocket.getCartesianCoordinate(), nearestObject.getCartesianCoordinate()));

        if (distance < nearestObject.getRadius()-10) {
            rocketConfig = 6;
            return true;
        }
        else return false;
    }

    /**
     * Update the delta time depending on the rocket position from the destination.
     */
    private void updateDeltaTime() {

        if (altitude > 40000000) {
            deltaTime = 86400;
        } else {
            deltaTime = 1;
        }
    }

    private void updateNearestObject() {

        if (!nearestObject.getName().equals(mission.getDestinationName())) {
            // comparing distances from objects in the list to get the nearest object.
            for (CelestialObject celestialObject : celestialObjects.values()) {
                CartesianCoordinate rocketCoordinate = rocket.getCartesianCoordinate();
                double distanceFromObject = calculation.calculateDistance(celestialObject.getCartesianCoordinate(), rocketCoordinate);
                double distanceFromNearest = calculation.calculateDistance(nearestObject.getCartesianCoordinate(), rocketCoordinate);
                if (Math.abs(distanceFromObject) <= Math.abs(distanceFromNearest)) {
                    nearestObject = celestialObject;
                }
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
                releaseStage(stage);
                releasedStages.add(rocket.getFirstStage());

                rocket.setFirstStage(null);
                rocket.getSecondStage().setFiring(true);
                rocketConfig = 3;

                logger.trace("First Stage release and Second Stage ignition at " + altitude + "m");
            }

        } else if ((rocket.getSecondStage() != null) && (rocket.getSecondStage().isFiring())) {

            stage = rocket.getSecondStage();

            double propellantFlow = (stage.getEngine().getPropellantFlow() * stage.getEngineNb()) * deltaTime;
            double remainingPropellant = stage.getTank().getRemainingPropellant() - propellantFlow;

            if (remainingPropellant >= 0) {
                stage.getTank().setRemainingPropellant(remainingPropellant);

            } else {
                releaseStage(stage);
                releasedStages.add(stage);

                rocket.setSecondStage(null);
                rocketConfig = 5;
                logger.trace("Second Stage released at " + altitude + "m");
            }
        }
        rocket.setMass(calculation.calculateRocketMass(rocket));
    }

    /**
     * Update the rocket elements. This method controls the firing of the second stage to achieve proper orbital insertion
     * or good escape of earth gravitation.
     */
    private void updateRocket() {

        CelestialObject destination = celestialObjects.get(mission.getDestinationName());

        CartesianCoordinate previousCoordinate;
        if (coordinatesHistory.size() > 1) {
            previousCoordinate = coordinatesHistory.get(coordinatesHistory.size()-1);
        } else {
            previousCoordinate = rocket.getCartesianCoordinate();
        }

        double previousAltitude = calculation.calculateAltitude(previousCoordinate, nearestObject);

        // adjusting the angle of the rocket
        double targetAltitude;
        if (mission.getDestinationName().equals("Earth")) {
            targetAltitude = mission.getOrbitAltitude();
        } else {
            if (!escapeGravity && !nearestObject.getName().equals("Earth")) {
                targetAltitude = mission.getOrbitAltitude();
            } else {
                targetAltitude = Constants.PARKING_ORBIT;
            }
        }

        if (escapeGravity) {
            rocket.getCartesianCoordinate().setSelfAngle(0);

        } else if (nearestObject.getName().equals("Earth")) {
            double delta = (altitude * 90) / targetAltitude;
            rocket.getCartesianCoordinate().setSelfAngle(Math.toRadians(90 - delta));

        } else {
            if (((altitude < targetAltitude) && (targetAltitude < previousAltitude)) || ((altitude > targetAltitude) && (targetAltitude > previousAltitude)) || isOrbiting) {
                rocket.getCartesianCoordinate().setSelfAngle(0);
                isOrbiting = true;

            }
            else if (altitude < targetAltitude) {
                rocket.getCartesianCoordinate().setSelfAngle(Math.toRadians(10));

            } else if (altitude > targetAltitude) {
                rocket.getCartesianCoordinate().setSelfAngle(Math.toRadians(-10));

            } else {
                rocket.getCartesianCoordinate().setSelfAngle(Math.toRadians(-20));
            }
        }

        // altitude relative to the nearest object.
        altitude = calculation.calculateAltitude(rocket.getCartesianCoordinate(), nearestObject);
        double distanceFromObject = calculation.calculateDistance(rocket.getCartesianCoordinate(), nearestObject.getCartesianCoordinate());
        double destinationX = destination.getCartesianCoordinate().getX();
        // acceleration of the rocket
        double acceleration = (calculation.calculateRocketAcceleration(rocket, nearestObject, altitude, deltaTime) / deltaTime);
        double velocity = (calculation.calculateVelocity(rocket.getVelocity(), acceleration, deltaTime) / deltaTime);

        rocket.setVelocity(velocity);

        CartesianCoordinate rocketCoordinate = rocket.getCartesianCoordinate();

        // Here is control of second stage firing.
        // In real life, not all engines are able to start multiple time, to simplify operations
        // we will assume it is the case here.
        if (rocket.getSecondStage() != null) {
            Stage stage = rocket.getSecondStage();
            if (!mission.getDestinationName().equals("Earth")) {
                if ((velocity > calculation.calculateMiniOrbitalVelocity(nearestObject)) && (stage.isFiring()) && (!escapeGravity)) {
                    logger.trace("SECO 1");
                    rocketConfig = 4;
                    rocket.getSecondStage().setFiring(false);
                }
                if ((rocketCoordinate.getX() < 15000) && (rocketCoordinate.getX() > -15000) && (rocketCoordinate.getY() > 0)) {
                    if ((velocity > calculation.calculateMiniOrbitalVelocity(nearestObject)) && (!stage.isFiring())) {
                        escapeGravity = true;
                        stage.setFiring(true);
                        logger.trace("Second Engine Ignition 2");
                        rocketConfig = 3;
                    }
                }
            } else {
                if (velocity > calculation.calculateMiniOrbitalVelocity(nearestObject)) {
                    if (rocket.getFirstStage() == null) {
                        releaseStage(stage);
                        releasedStages.add(stage);

                        rocket.setSecondStage(null);
                        rocketConfig = 5;
                        logger.trace("SECO 2 at " + altitude + "m");
                    }
                }
            }
        } else if ((rocket.getVelocity() > calculation.calculateEscapeVelocity(nearestObject, distanceFromObject)) && !nearestObject.getName().equals("Earth")) {
            // if approaching the destination, mission use onboard thrusters to reduce their speed to be captured by the gravitational force of the object
            rocket.setVelocity(rocket.getVelocity() - 1);
        }

        if (rocket.getCartesianCoordinate().getX() > destinationX && escapeGravity) {
            escapeGravity = false;
        }

        rocket.setCartesianCoordinate(calculation.calculateRocketPosition(rocket, nearestObject, altitude, deltaTime, escapeGravity));

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

            CartesianCoordinate newCoordinate = calculation.calculateStagePosition(stage, celestialObjects.get("Earth"), deltaTime);
            PolarCoordinate tempCoordinate = calculation.cartesianToPolar(stage.getCartesianCoordinate());

            if ((tempCoordinate.getR() <= Constants.EARTH_RADIUS) || (tempCoordinate.getR() >= Constants.EARTH_RADIUS + 40000000)) {
                releasedStages.remove(i);
            } else {
                stage.setCartesianCoordinate(newCoordinate);
            }
        }
    }

    private void releaseStage(Stage stage) {
        stage.setFiring(false);

        CartesianCoordinate coordinate = new CartesianCoordinate();
        CartesianCoordinate rocketCoordinate = rocket.getCartesianCoordinate();

        coordinate.setX(rocketCoordinate.getX());
        coordinate.setY(rocketCoordinate.getY());
        coordinate.setSelfAngle(rocketCoordinate.getSelfAngle());

        stage.setCartesianCoordinate(coordinate);
        stage.setVelocity(rocket.getVelocity());
    }
}
