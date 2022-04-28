package process.management;

import config.Constants;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import data.rocket.Rocket;
import data.rocket.Stage;

/**
 * Class that contains all methods for calculs of masses, accelerations, accelerations and trajectories.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.17
 */
public class Calculation {

    /**
     * Calculates the cartesian coordinate from polar ones.
     *
     * @param polarCoordinate The coordinate to convert.
     * @return a CartesianCoordinate object.
     */
    public CartesianCoordinate polarToCartesian(PolarCoordinate polarCoordinate) {

        int x = (int) (polarCoordinate.getR() * Math.cos(polarCoordinate.getAngle()));
        int y = (int) (polarCoordinate.getR() * Math.sin(polarCoordinate.getAngle()));

        return new CartesianCoordinate(x, y);
    }

    /**
     * Calculates the polar coordinate from cartesian ones.
     *
     * @param cartesianCoordinate The coordinate to convert.
     * @return a PolarCoordinate object.
     */
    public PolarCoordinate cartesianToPolar(CartesianCoordinate cartesianCoordinate) {

        double r = Math.sqrt(Math.pow(cartesianCoordinate.getX(), 2) + Math.pow(cartesianCoordinate.getY(), 2));
        double angle = Math.atan2(cartesianCoordinate.getY(), cartesianCoordinate.getX());

        return new PolarCoordinate(r, angle);
    }

    /**
     * Method to calculate the distance in meters between two cartesian coordinates.
     *
     * @param origin The origin.
     * @param end    The end.
     * @return The distance between two coordinates.
     */
    public double calculateDistance(CartesianCoordinate origin, CartesianCoordinate end) {

        return Math.sqrt(Math.pow((end.getX() - origin.getX()), 2) + Math.pow((end.getY() - origin.getY()), 2));
    }

    /**
     * Method that calculates the gravity of an object using the gravity formula.
     *
     * @param attractiveObjectMass The mass of the object emitting gravity force.
     * @param distance             The distance from the center of the object.
     * @return Gravity value in N.
     */
    public double calculateGravity(double attractiveObjectMass, double distance) {

        return ((Constants.GRAVITATIONAL_CONST * attractiveObjectMass) / Math.pow(distance, 2));
    }


    /**
     * Method to calculate the minimal velocity required to stay in orbit of a given celestial object that can be a
     * planet, moon etc...
     *
     * @param object The object you want to orbit.
     * @return The minimal velocity to stay in orbit.
     */
    public double calculateMiniOrbitalVelocity(CelestialObject object) {

        return Math.sqrt(((Constants.GRAVITATIONAL_CONST * object.getMass()) / object.getRadius()));
    }

    /**
     * Method to calculate the gravitational escape velocity of an object.
     *
     * @param object             The object to get the escape velocity from.
     * @param distanceFromCenter The distance from the center of the object.
     * @return The escape velocity of the object.
     */
    public double calculateEscapeVelocity(CelestialObject object, double distanceFromCenter) {

        return Math.sqrt(((2 * Constants.GRAVITATIONAL_CONST * object.getMass()) / distanceFromCenter));
    }

    /**
     * Method to calculate the temperature in Celsius degrees of the air at a given altitude. This method takes some default values for the weather.
     * They can be modified in {@link Constants}.
     *
     * @param altitude The altitude in meter.
     * @return The temperature at the given altitude in Celsius degrees.
     */
    public double calculateTemperatureAtAltitude(double altitude) {

        return Constants.AIR_TEMPERATURE_SEA_LEVEL - (0.0065 * altitude);
    }

    /**
     * Method to calculate the pressure in hPa of the air at a given altitude. This method takes some default values for the weather.
     * They can be modified in {@link Constants}.
     *
     * @param altitude    The altitude in meter.
     * @param temperature The temperature of the air at the given altitude in Celsius degrees.
     * @return The pressure at the given altitude in hPa.
     */
    public double calculatePressureAtAltitude(double altitude, double temperature) {

        return Constants.AIR_PRESSURE_SEA_LEVEL * Math.pow((1 - ((0.0065 * altitude) / (temperature + (0.0065 * altitude) + Constants.ABSOLUTE_ZERO))), 5.257);
    }

    /**
     * Method that calculate the density at a given altitude.
     *
     * @param altitude The altitude for calculs.
     * @return The density of the air at the requested altitude.
     */
    public double calculateAirDensity(double altitude) {

        double T = calculateTemperatureAtAltitude(altitude) + Constants.ABSOLUTE_ZERO;
        double pd = calculatePressureAtAltitude(altitude, T) * 100;
        return (pd / (Constants.AIR_CONSTANT * T));
    }

    /**
     * Method that calculate the acceleration of the rocket.
     *
     * @param rocket        The rocket to take for calculs.
     * @param nearestObject The nearest planet or moon.
     * @param altitude      The altitude of the rocket in m.
     * @param deltaTime     The delta time between each update in s.
     * @return the acceleration of the rocket in m.s^-1.
     */
    public double calculateRocketAcceleration(Rocket rocket, CelestialObject nearestObject, double altitude, double deltaTime) {

        double acceleration;
        double deltaMass = 0;
        double distanceFromNearestObject = calculateDistance(nearestObject.getCartesianCoordinate(), rocket.getCartesianCoordinate());
        double gravity = calculateGravity(nearestObject.getMass(), distanceFromNearestObject);
        double centripetalForce = Math.pow(rocket.getVelocity(), 2) / distanceFromNearestObject;
        int exhaustVelocity;

        if ((rocket.getFirstStage() != null) && (rocket.getFirstStage().isFiring())) {
            deltaMass = (rocket.getFirstStage().getEngine().getPropellantFlow() * rocket.getFirstStage().getEngineNb());
            exhaustVelocity = rocket.getFirstStage().getEngine().getExhaustVelocity();

        } else if ((rocket.getSecondStage() != null) && (rocket.getSecondStage().isFiring())) {
            deltaMass = (rocket.getSecondStage().getEngine().getPropellantFlow() * rocket.getSecondStage().getEngineNb());
            exhaustVelocity = rocket.getSecondStage().getEngine().getExhaustVelocity();

        } else {
            exhaustVelocity = 0;

        }
        acceleration = (exhaustVelocity / rocket.getMass()) * (deltaMass / deltaTime);
        if (!Double.isNaN(centripetalForce)) {
            if (centripetalForce < gravity) {
                acceleration -= gravity;
            }
        }


        if (altitude < 80000) {
            double airDensity = calculateAirDensity(altitude);
            acceleration -= ((1 / 2) * Constants.DEFAULT_DRAG_COEFFICIENT * airDensity * Math.pow(rocket.getVelocity(), 2) * Constants.DEFAULT_ROCKET_SURFACE);
        }

        return acceleration;
    }

    /**
     * Method that calculates the velocity from the acceleration.
     *
     * @param acceleration    The acceleration of the object in m.s^-2.
     * @param initialVelocity Its initial velocity in m.s^-1.
     * @param deltaTime       The delta time to use in s.
     * @return The new velocity of the object in m.s^-1.
     */
    public double calculateVelocity(double initialVelocity, double acceleration, double deltaTime) {

        return (initialVelocity * deltaTime) + (acceleration * deltaTime);
    }

    /**
     * Calculate the altitude (distance from ground) of a coordinate object relative to a planet or moon.
     *
     * @param objectCoordinate The coordinate object.
     * @param celestialObject  The planet or moon to take use as reference for altitude.
     * @return the altitude in m.
     */
    public double calculateAltitude(CartesianCoordinate objectCoordinate, CelestialObject celestialObject) {

        double altitude = calculateDistance(celestialObject.getCartesianCoordinate(), objectCoordinate) - celestialObject.getRadius();

        if (altitude < 0) {
            altitude = 0;
        }

        return altitude;
    }

    /**
     * Calculs the mass of the rocket
     *
     * @param rocket the rocket to take for calculs.
     * @return The total mass of the rocket.
     */
    public double calculateRocketMass(Rocket rocket) {

        Stage stage;
        double mass = 0;
        if (rocket.getFirstStage() != null) {

            stage = rocket.getFirstStage();
            mass += (stage.getMass() + stage.getTank().getRemainingPropellant() + (stage.getEngine().getMass() * stage.getEngineNb()));
        }
        if (rocket.getSecondStage() != null) {

            stage = rocket.getSecondStage();
            mass += (stage.getMass() + stage.getTank().getRemainingPropellant() + (stage.getEngine().getMass() * stage.getEngineNb()));
        }
        if (rocket.getPayload() != null) {

            mass += rocket.getPayload().getMass();
        }

        return mass;
    }


    /**
     * Method that calculate the position of the rocket.
     *
     * @param rocket        The rocket to use.
     * @param nearestObject The destination, can be the earth or another planet/moon.
     * @param deltaTime     The delta time between 2 updates in s.
     * @param escapeGravity A boolean that trigger some variation in the trajectory.
     * @return A {@link CartesianCoordinate} object.
     */
    public CartesianCoordinate calculateRocketPosition(Rocket rocket, CelestialObject nearestObject, double altitude, double deltaTime, boolean escapeGravity) {

        CartesianCoordinate returnCoordinate;
        double escapeVelocity = calculateEscapeVelocity(nearestObject, altitude + nearestObject.getRadius());

        if (escapeGravity) {

            if (nearestObject.getName().equals("Earth")) {

                if (rocket.getVelocity() < escapeVelocity) {
                    double rocketAngle = rocket.getCartesianCoordinate().getSelfAngle();
                    double vx = rocket.getVelocity() * Math.cos(rocketAngle);
                    double vy = rocket.getVelocity() * Math.sin(rocketAngle);
                    PolarCoordinate polarCoordinate = cartesianToPolar(rocket.getCartesianCoordinate());
                    polarCoordinate.setR(polarCoordinate.getR() + (vy * deltaTime));
                    double angle = polarCoordinate.getAngle() - (vx / (nearestObject.getRadius() + altitude));
                    polarCoordinate.setAngle(angle);
                    returnCoordinate = polarToCartesian(polarCoordinate);
                    returnCoordinate.setSelfAngle(rocketAngle);
                } else {

                    returnCoordinate = rocket.getCartesianCoordinate();
                    returnCoordinate.setX((int) (returnCoordinate.getX() + rocket.getVelocity()));
                }
            } else {

                returnCoordinate = rocket.getCartesianCoordinate();
                returnCoordinate.setX((int) (returnCoordinate.getX() + rocket.getVelocity()));
            }
        } else {

            CartesianCoordinate rocketCoordinate = rocket.getCartesianCoordinate();
            CartesianCoordinate destinationCoordinate = nearestObject.getCartesianCoordinate();
            int tempX = rocketCoordinate.getX() - destinationCoordinate.getX();
            int tempY = rocketCoordinate.getY() - destinationCoordinate.getY();
            rocketCoordinate.setX(tempX);
            rocketCoordinate.setY(tempY);

            double rocketAngle = rocket.getCartesianCoordinate().getSelfAngle();
            double vx = Math.abs(rocket.getVelocity() * Math.cos(rocketAngle));
            double vy = rocket.getVelocity() * Math.sin(rocketAngle);

            PolarCoordinate polarCoordinate = cartesianToPolar(rocketCoordinate);
            polarCoordinate.setR(polarCoordinate.getR() + (vy * deltaTime));

            double angle = polarCoordinate.getAngle() - (vx / (nearestObject.getRadius() + altitude));
            polarCoordinate.setAngle(angle);

            returnCoordinate = polarToCartesian(polarCoordinate);
            returnCoordinate.setX(returnCoordinate.getX() + destinationCoordinate.getX());
            returnCoordinate.setY(returnCoordinate.getY() + destinationCoordinate.getY());
            returnCoordinate.setSelfAngle(rocketCoordinate.getSelfAngle());
        }

        return returnCoordinate;
    }

    /**
     * Method that calculates the position of a stage.
     *
     * @param stage     The stage to use.
     * @param earth     The earth object.
     * @param deltaTime The delta time between 2 updates in s.
     * @return A {@link CartesianCoordinate} object.
     */
    public CartesianCoordinate calculateStagePosition(Stage stage, CelestialObject earth, double deltaTime) {

        CartesianCoordinate returnCoordinate;
        CartesianCoordinate stageCoordinate = stage.getCartesianCoordinate();

        double stageVelocity = stage.getVelocity();
        double distanceFromEarth = calculateDistance(earth.getCartesianCoordinate(), stageCoordinate);
        double escapeVelocity = calculateEscapeVelocity(earth, distanceFromEarth);
        double minimalOrbitVelocity = calculateMiniOrbitalVelocity(earth);

        if ((stageVelocity < minimalOrbitVelocity)) {

            double centripetalForce = Math.pow(stage.getVelocity(), 2) / distanceFromEarth;
            double gravity = calculateGravity(earth.getMass(), distanceFromEarth);

            // if velocity is not high enough to remain in orbit, stage fall back to earth, angle is then negative
            double vx = Math.abs(stage.getVelocity() * Math.cos(Math.toRadians(-60)));
            double vy = stage.getVelocity() * Math.sin(Math.toRadians(-60)) - (centripetalForce - gravity);

            PolarCoordinate polarCoordinate = cartesianToPolar(stageCoordinate);
            polarCoordinate.setR(polarCoordinate.getR() + (vy * deltaTime));

            double angle = polarCoordinate.getAngle() - (vx / distanceFromEarth);

            polarCoordinate.setAngle(angle);
            returnCoordinate = polarToCartesian(polarCoordinate);
        } else if ((minimalOrbitVelocity <= stageVelocity) && (stageVelocity < escapeVelocity)) {

            // if stage velocity is high enough to remain in orbit, then angle is 90 (remain at a stable altitude)
            double vx = stageVelocity * Math.cos(0);
            double vy = stageVelocity * Math.sin(0);

            PolarCoordinate polarCoordinate = cartesianToPolar(stage.getCartesianCoordinate());
            polarCoordinate.setR(polarCoordinate.getR() + (vy * deltaTime));

            double angle = polarCoordinate.getAngle() - (vx / distanceFromEarth);

            polarCoordinate.setAngle(angle);
            returnCoordinate = polarToCartesian(polarCoordinate);
        } else {

            // if stage velocity exceed escape velocity, it will increase its orbit until leaving earth gravitation
            int vx = (int) (stageVelocity * Math.cos(stageCoordinate.getSelfAngle()));
            int vy = (int) (stageVelocity * Math.sin(stageCoordinate.getSelfAngle()));

            stageCoordinate.setX(stageCoordinate.getX() + vx);
            stageCoordinate.setY(stageCoordinate.getY() + vy);

            returnCoordinate = stageCoordinate;
        }
        return returnCoordinate;
    }
}
