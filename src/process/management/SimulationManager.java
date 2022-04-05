package process.management;

import config.SimConfig;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import data.mission.Mission;
import data.rocket.Propellant;
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
 *
 * TODO: implement all calculs once they are ready to be used.
 */
public class SimulationManager {

    private double deltaTime;
    private Calculation calculation;
    private TelemetryRecord telemetry;

    private Mission mission;
    private Rocket rocket;

    private double altitude;

    // record the last 255 postions of the rocket.
    private List<CartesianCoordinate>coordinatesHistory = new ArrayList<>();
    private List<CartesianCoordinate>trajectoryHistory = new ArrayList<>();

    private int recordCycle = 0;

    // celestial objects that will interact with the mission
    private HashMap<String, CelestialObject> celestialObjects = new HashMap<String, CelestialObject>();

    // separated stages
    private List<Stage>releasedStages = new ArrayList<>();

    private final Logger logger = LoggerUtility.getLogger(SimulationManager.class, "html");

    public SimulationManager(Rocket rocket, Mission mission, CelestialObjectBuilder celestialObjectBuilder) {
        this.rocket = rocket;
        this.mission = mission;

        calculation = new Calculation();
        telemetry = new TelemetryRecord();
        altitude = 0;
        deltaTime = SimConfig.DELTA_TIME;

        celestialObjects.put("Earth", celestialObjectBuilder.buildCelestialObject("Earth"));
        System.out.println(celestialObjects.get("Earth"));
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

    public void next() {

        // rocket update
        updateRocketMass();
        updateRocket();

        // released stages
        updateReleasedStagesPosition();

        // telemetry
        updateCoordinateHistory();
        updateTrajectoryHistory();
    }

    /**
     * Method that updates the rocket. It removes the used propellant mass and manage stage once the tank is empty.
    */
     private void updateRocketMass() {

         if ((rocket.getFirstStage() != null) && (rocket.getFirstStage().isFiring())) {
             double propellantFlow = (rocket.getFirstStage().getEngine().getPropellantFlow() * rocket.getFirstStage().getEngineNb()) * deltaTime;

             if ((rocket.getFirstStage().getTank().getRemainingPropellant() - propellantFlow) >= 0) {
                 rocket.getFirstStage().getTank().setRemainingPropellant(rocket.getFirstStage().getTank().getRemainingPropellant() - propellantFlow);
                 rocket.setMass(calculation.calculateRocketMass(rocket));
             }
             else {
                 CartesianCoordinate coordinate = new CartesianCoordinate(rocket.getCartesianCoordinate().getX(), rocket.getCartesianCoordinate().getY());
                 rocket.getFirstStage().setCartesianCoordinate(coordinate);
                 releasedStages.add(rocket.getFirstStage());
                 rocket.setFirstStage(null);
                 rocket.getSecondStage().setFiring(true);
                 logger.trace("ignition of second stage at " + altitude);
             }
             rocket.setMass(calculation.calculateRocketMass(rocket));
         }
         else if ((rocket.getSecondStage() != null) && (rocket.getSecondStage().isFiring())) {
             double propellantFlow = (rocket.getSecondStage().getEngine().getPropellantFlow() * rocket.getSecondStage().getEngineNb()) * deltaTime;

             if ((rocket.getSecondStage().getTank().getRemainingPropellant() - propellantFlow) >= 0) {
                 rocket.getSecondStage().getTank().setRemainingPropellant(rocket.getSecondStage().getTank().getRemainingPropellant() - propellantFlow);
                 rocket.setMass(calculation.calculateRocketMass(rocket));
             }
             else {
                 CartesianCoordinate coordinate = new CartesianCoordinate(rocket.getCartesianCoordinate().getX(), rocket.getCartesianCoordinate().getY());
                 rocket.getSecondStage().setCartesianCoordinate(coordinate);
                 releasedStages.add(rocket.getSecondStage());
                 rocket.setSecondStage(null);
             }
         }
    }

    private void updateRocket() {

        CelestialObject nearestObject = celestialObjects.get("Earth");
        CelestialObject destination = celestialObjects.get(mission.getDestinationName());
        for (CelestialObject celestialObject : celestialObjects.values()) {
            if (calculation.calculateDistance(celestialObject.getCartesianCoordinate(), rocket.getCartesianCoordinate()) < calculation.calculateDistance(nearestObject.getCartesianCoordinate(), rocket.getCartesianCoordinate())) {
                nearestObject = celestialObject;
            }
        }

        altitude = calculation.calculateAltitude(rocket.getCartesianCoordinate(), celestialObjects.get("Earth"));

        double acceleration = calculation.calculateAcceleration(rocket, nearestObject, altitude, deltaTime);

        double velocity = calculation.calculateVelocity(rocket.getVelocity(), acceleration, deltaTime);
        logger.trace(velocity);
        rocket.setVelocity(velocity);

        rocket.setCartesianCoordinate(calculation.calculateRocketPosition(rocket, destination, mission.getOrbitAltitude(), altitude, deltaTime));

        updateTelemetry((int) acceleration, (int) velocity);
    }

    private void updateTelemetry(int acceleration, int velocity) {
         telemetry.addAcceleration(acceleration);
         telemetry.addVelocity(velocity);
    }

    private void updateCoordinateHistory() {
         if (coordinatesHistory.size() >= 510) {
             coordinatesHistory.remove(0);
         }
         CartesianCoordinate coordinate = new CartesianCoordinate(rocket.getCartesianCoordinate().getX(), rocket.getCartesianCoordinate().getY());

         coordinatesHistory.add(coordinate);
    }

    private void updateTrajectoryHistory() {
         if (recordCycle == 1000) {
             CartesianCoordinate tempCoordinate = coordinatesHistory.get(0);
             CartesianCoordinate coordinate = new CartesianCoordinate(tempCoordinate.getX(), tempCoordinate.getY());
             trajectoryHistory.add(coordinate);
             recordCycle = 0;
         }
         else recordCycle++;
    }

    private void updateReleasedStagesPosition() {
        for (Stage stage: releasedStages) {
            //stage.setCartesianCoordinate(calculation.calculateStagePosition(stage.getCartesianCoordinate(), stage.getVelocity(), 0, 0));
        }
    }
}
