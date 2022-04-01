package process.management;

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
 *
 * TODO: implement all calculs once they are ready to be used.
 */
public class SimulationManager {

    private double deltaTime;
    private Calculation calculation;
    private TelemetryRecord telemetry;

    private Mission mission;
    private Rocket rocket;

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
        logger.trace(mission);

        calculation = new Calculation();
        telemetry = new TelemetryRecord();
        deltaTime = SimConfig.DELTA_TIME;

        celestialObjects.put("Earth", celestialObjectBuilder.buildCelestialObject("Earth"));
        System.out.println(celestialObjects.get("Earth"));
        if (!mission.getDestinationName().equals("Earth")) {
            CelestialObject destination = celestialObjectBuilder.buildCelestialObject(mission.getDestinationName());
            celestialObjects.put(destination.getName(), destination);
        }

        logger.trace(rocket);
    }

    public void next() {
        updateRocketMass();
        updateRocketPosition();
        updateReleasedStagesPosition();
        updateCelestialObjectsPosition();
        updateCoordinateHistory();
        updateTrajectoryHistory();
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

    /**
     * Method that updates the rocket. It removes the used propellant mass and manage stage once the tank is empty.
    */
     private void updateRocketMass() {
         double mass = 0;
         if (rocket.getFirstStage() != null) {
             mass += (rocket.getFirstStage().getMass() +
                     (rocket.getFirstStage().getTank().getRemainingPropellant() * rocket.getFirstStage().getTank().getPropellant().getDensity()) +
                     (rocket.getFirstStage().getEngine().getWeight() * rocket.getFirstStage().getEngineNb()));
         }
         if (rocket.getSecondStage() != null) {
             mass += (rocket.getSecondStage().getMass() +
                     (rocket.getSecondStage().getTank().getRemainingPropellant() * rocket.getSecondStage().getTank().getPropellant().getDensity()) +
                     (rocket.getSecondStage().getEngine().getWeight() * rocket.getSecondStage().getEngineNb()));
         }
         if (rocket.getPayload() != null) {
             mass += rocket.getPayload().getMass();
         }
         rocket.setMass(mass);
    }

    private void updateRocketPosition() {

        double acceleration = calculation.accelerationFromThrust(rocket.getMass(), calculation.calculateRocketThrust(rocket));
        double velocity = calculation.velocityFromAcceleration(acceleration, rocket.getVelocity(), deltaTime);
        double altitude;

        if (rocket.getCartesianCoordinate().getX() > rocket.getCartesianCoordinate().getY()) {
            altitude = rocket.getCartesianCoordinate().getX() - celestialObjects.get("Earth").getRadius();
        }
        else {
            altitude = rocket.getCartesianCoordinate().getY() - celestialObjects.get("Earth").getRadius();
        }
        rocket.getCartesianCoordinate().setX(rocket.getCartesianCoordinate().getX() +1000);

        // TEMP
        PolarCoordinate polarCoordinate = calculation.cartesianToPolar(rocket.getCartesianCoordinate());
        double angle = polarCoordinate.getAngle();

        angle += calculation.degreeToRadian(0.02);
        polarCoordinate.setAngle(angle);
        rocket.setCartesianCoordinate(calculation.polarToCartesian(polarCoordinate));
        System.out.println(rocket.getCartesianCoordinate());
        // END TEMP

        updateTelemetry((int) acceleration, (int) velocity, (int) altitude);
    }

    private void updateTelemetry(int acceleration, int velocity, int altitude) {
         logger.trace(acceleration + " " + velocity + " " + altitude);
         telemetry.addAcceleration(acceleration);
         telemetry.addVelocity(velocity);
         telemetry.addAltitude(altitude);
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
            stage.setCartesianCoordinate(calculation.calculateNewPosition(stage.getCartesianCoordinate(), stage.getVelocity(), 0, 0));
        }
    }

    private void updateCelestialObjectsPosition() {
        for (CelestialObject celestialObject : celestialObjects.values()) {
            celestialObject.setCartesianCoordinate(calculation.calculateNewPosition(celestialObject.getCartesianCoordinate(), celestialObject.getVelocity(), 0, 0));
        }
    }
}
