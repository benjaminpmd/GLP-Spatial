package process.management;

import config.Constants;
import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import data.mission.SpaceCenter;
import data.rocket.Rocket;
import data.rocket.Stage;

/**
 * Class that contains all equations to use.
 *
 * @author Benjamin P
 * @version 22.03.13 (0.1.0 WIP)
 * @see data.coordinate.CartesianCoordinate
 * @see data.coordinate.PolarCoordinate
 * @since 17.02.22
 */
public class Calculation {

    /**
     * Angle of the launch in degree
     */
    private int LAUNCH_ANGLE = 20;

    /**
     * Calculates the cartesian coordinate from polar ones.
     *
     * @param polarCoordinate {@link PolarCoordinate} The coordinate to convert.
     * @return {@link CartesianCoordinate}
     */
    public CartesianCoordinate polarToCartesian(PolarCoordinate polarCoordinate) {

        int x = (int) (polarCoordinate.getR() * Math.cos(polarCoordinate.getAngle()));
        int y = (int) (polarCoordinate.getR() * Math.sin(polarCoordinate.getAngle()));
        return new CartesianCoordinate(x, y);
    }

    /**
     * Calculates the polar coordinate from cartesian ones.
     *
     * @param cartesianCoordinate {@link CartesianCoordinate} The coordinate to convert.
     * @return {@link PolarCoordinate}
     */
    public PolarCoordinate cartesianToPolar(CartesianCoordinate cartesianCoordinate) {

        double r = Math.sqrt(Math.pow(cartesianCoordinate.getX(), 2) + Math.pow(cartesianCoordinate.getY(), 2));
        double angle = Math.atan2(cartesianCoordinate.getY(), cartesianCoordinate.getX());
        return new PolarCoordinate(r, angle);
    }

    public double degreeToRadian(double angle) {
        if ((-360 <= angle) && (angle <= 360)) {
            if (angle < 0) {
                return -Math.toRadians(angle);
            }
            else {
                return Math.toRadians(angle);
            }
        }
        else {
            return 0;
        }
    }

    public double radianToDegree(double angle) {
        if ((-6.28319 <= angle) && (angle <= 6.28319)) {
            if (angle < 0) {
                return -Math.toDegrees(angle);
            }
            else {
                return Math.toDegrees(angle);
            }
        }
        else {
            return 0;
        }
    }

    /**
     * Method that calculates the gravity of an object using the gravity formula.
     *
     * @param attractiveObjectMass double The mass of the object emitting gravity force.
     * @param distance             double The distance from the center of the object.
     * @return double gravity value in N.
     */
    public double calculateGravity(double attractiveObjectMass, double distance) {

        return ((Constants.GRAVITATIONAL_CONST * attractiveObjectMass) / Math.pow(distance, 2));
    }


    /**
     * Method to calculate the minimal velocity required to stay in orbit of a given celestial object that can be a planet, moon etc...
     *
     * @param object {@link CelestialObject} The object you want to orbit.
     * @return double The minimal velocity to stay in orbit.
     */
    public double calculateMiniOrbitalVelocity(CelestialObject object) {

        return Math.sqrt(((Constants.GRAVITATIONAL_CONST * object.getMass()) / object.getRadius()));
    }

    /**
     * Method to calculate the distance in meters between two cartesian coordinates.
     *
     * @param origin {@link CartesianCoordinate} The origin.
     * @param end    {@link CartesianCoordinate} The end.
     * @return double The distance between two coordinates.
     */
    public double calculateDistance(CartesianCoordinate origin, CartesianCoordinate end) {

        return Math.sqrt(Math.pow((end.getX() - origin.getX()), 2) + Math.pow((end.getY() - origin.getY()), 2));
    }

    /**
     * Method to calculate the temperature in Celsius degrees of the air at a given altitude. This method takes some default values for the weather.
     * They can be modified in {@link Constants}.
     *
     * @param altitude double The altitude in meter.
     * @return double The temperature at the given altitude in Celsius degrees.
     */
    public double calculateTemperatureAtAltitude(double altitude) {

        return Constants.AIR_TEMPERATURE_SEA_LEVEL - (0.0065 * altitude);
    }

    /**
     * Method to calculate the pressure in hPa of the air at a given altitude. This method takes some default values for the weather.
     * They can be modified in {@link Constants}.
     *
     * @param altitude    double The altitude in meter.
     * @param temperature double The temperature of the air at the given altitude in Celsius degrees.
     * @return double The pressure at the given altitude in hPa.
     */
    public double calculatePressureAtAltitude(double altitude, double temperature) {

        return Constants.AIR_PRESSURE_SEA_LEVEL * Math.pow((1 - ((0.0065 * altitude) / (temperature + (0.0065 * altitude) + Constants.ABSOLUTE_ZERO))), 5.257);
    }

    /**
     * Method to calculate the density of the air in kg.m^-3 at a given altitude.
     *
     * @param altitude double The altitude in meter.
     * @return double The air density in kg.m^-3.
     */
    public double calculateAirDensity(double altitude) {

        double T = calculateTemperatureAtAltitude(altitude) + Constants.ABSOLUTE_ZERO;

        double pd = calculatePressureAtAltitude(altitude, T) * 100;

        return (pd / (Constants.AIR_CONSTANT * T));
    }

    /**
     * Method that calculate the acceleration of the rocket. The method take in count multiple configuration such as what stage
     * is powering the rocket, if the rocket is in the atmosphere or if it is near a large celestial object such as a planet and
     * calculate the gravity of atmosphere drag in consequence.
     *
     * @param rocket {@link Rocket} The rocket to use for calculations.
     * @param celestialObject {@link CelestialObject} The nearest celestial object of the rocket.
     * @param altitude double The altitude of the rocket from Earth ground.
     * @param deltaTime double The delta time to use for calculation.
     * @return double The acceleration in m.s^-2.
     */
    public double calculateAcceleration(Rocket rocket, CelestialObject celestialObject, double altitude, double deltaTime) {

        double acceleration;
        double deltaMass = 0;
        int exhaustVelocity;

        if ((rocket.getFirstStage() != null) && (rocket.getFirstStage().isFiring())) {
            deltaMass = (rocket.getFirstStage().getEngine().getPropellantFlow() * rocket.getFirstStage().getEngineNb());
            exhaustVelocity = rocket.getFirstStage().getEngine().getExhaustVelocity();
        } else if ((rocket.getSecondStage() != null) && (rocket.getSecondStage().isFiring())) {
            deltaMass = (rocket.getSecondStage().getEngine().getPropellantFlow() * rocket.getSecondStage().getEngineNb());
            exhaustVelocity = rocket.getSecondStage().getEngine().getExhaustVelocity();
        }
        else {
            exhaustVelocity = 0;
        }

        acceleration = (exhaustVelocity / rocket.getMass()) * (deltaMass / deltaTime) - calculateGravity(celestialObject.getMass(), calculateDistance(celestialObject.getCartesianCoordinate(), rocket.getCartesianCoordinate()));

        if (altitude < 80000) {
            double airDensity = calculateAirDensity(altitude);
            acceleration -= ((1 / 2) * Constants.DEFAULT_DRAG_COEFFICIENT * airDensity * Math.pow(rocket.getVelocity(), 2) * Constants.DEFAULT_ROCKET_SURFACE);
        }
        return acceleration;
    }

    public double calculateAcceleration(Stage stage, double deltaTime) {

        double acceleration;
        CartesianCoordinate coordinate = stage.getCartesianCoordinate();
        acceleration = - calculateGravity(Constants.EARTH_MASS, calculateDistance(new CartesianCoordinate(), coordinate));

        double attitude = cartesianToPolar(coordinate).getR() - Constants.EARTH_RADIUS;
        if (attitude < 80000) {
            double airDensity = calculateAirDensity(attitude);
            acceleration += ((1 / 2) * Constants.DEFAULT_DRAG_COEFFICIENT * airDensity * Math.pow(stage.getVelocity(), 2) * Constants.DEFAULT_ROCKET_SURFACE);
        }
        return acceleration;
    }

    /**
     * Method that calculates the velocity from the acceleration.
     *
     * @param acceleration    double The acceleration of the object in m.s^-2.
     * @param initialVelocity double Its initial velocity in m.s^-1.
     * @param deltaTime       double The delta time to use in s.
     * @return double The new velocity of the object in m.s^-1.
     */
    public double calculateVelocity(double initialVelocity, double acceleration, double deltaTime) {

        return initialVelocity + (acceleration * deltaTime);
    }

    public double calculateAltitude(CartesianCoordinate objectCoordinate, CelestialObject celestialObject) {
        double altitude = calculateDistance(celestialObject.getCartesianCoordinate(), objectCoordinate) - celestialObject.getRadius();
        if (altitude < 0) {
            altitude = 0;
        }
        return altitude;
    }

    public double calculateLaunchAngle(double attitude, boolean orbitingEarth) {
        double velocity;
        if (orbitingEarth) {
            velocity = 7400;
        }
        else {
            velocity = 11500;
        }
        double result = Math.pow(velocity, 2) / (2 * Constants.GRAVITY * attitude);
        result = Math.sqrt(result);
        result = Math.pow(result, -1);
        result = Math.toDegrees(Math.asin(result));
        return result;
    }

    public double calculateRocketMass(Rocket rocket) {

        double mass = 0;
        if (rocket.getFirstStage() != null) {
            mass += (rocket.getFirstStage().getMass() +
                    (rocket.getFirstStage().getTank().getRemainingPropellant() * rocket.getFirstStage().getTank().getPropellant().getDensity()) +
                    (rocket.getFirstStage().getEngine().getMass() * rocket.getFirstStage().getEngineNb()));
        }
        if (rocket.getSecondStage() != null) {
            mass += (rocket.getSecondStage().getMass() +
                    (rocket.getSecondStage().getTank().getRemainingPropellant() * rocket.getSecondStage().getTank().getPropellant().getDensity()) +
                    (rocket.getSecondStage().getEngine().getMass() * rocket.getSecondStage().getEngineNb()));
        }
        if (rocket.getPayload() != null) {
            mass += rocket.getPayload().getMass();
        }

        return mass;
    }

    public CartesianCoordinate calculateRocketPosition(Rocket rocket, CelestialObject destination, int destinationOrbit, double altitude, double deltaTime) {
        // TODO : add trajectory system
        CartesianCoordinate returnCoordinate;
        if (((rocket.getFirstStage() != null) && (rocket.getFirstStage().isFiring())) || ((rocket.getSecondStage() != null) && (rocket.getSecondStage().isFiring()))) {
            if ((0 <= altitude) && (altitude < destinationOrbit)) {
                PolarCoordinate polarCoordinate = cartesianToPolar(rocket.getCartesianCoordinate());

                polarCoordinate.setR(polarCoordinate.getR() + rocket.getVelocity() * deltaTime);

                double angle = polarCoordinate.getAngle();
                angle += degreeToRadian(0.02 + (altitude/10000000));
                polarCoordinate.setAngle(angle);

                return polarToCartesian(polarCoordinate);
            }
            else {
                PolarCoordinate coordinate = cartesianToPolar(rocket.getCartesianCoordinate());
                double orbitCircumference = 2 * Math.PI * (destinationOrbit + destination.getRadius());
                double orbitTime = orbitCircumference / rocket.getVelocity();
                double orbitAngle = (359 * deltaTime) / orbitTime;
                coordinate.setAngle(coordinate.getAngle() + degreeToRadian(orbitAngle));
                returnCoordinate = polarToCartesian(coordinate);
            }
        }
        else {
            if ((0 <= altitude) && (altitude < destinationOrbit)) {
                PolarCoordinate polarCoordinate = cartesianToPolar(rocket.getCartesianCoordinate());

                polarCoordinate.setR(polarCoordinate.getR() + rocket.getVelocity() * deltaTime);

                double angle = polarCoordinate.getAngle();
                angle += degreeToRadian(0.02 + (altitude/10000000));
                polarCoordinate.setAngle(angle);

                return polarToCartesian(polarCoordinate);
            }
            else {
                PolarCoordinate coordinate = cartesianToPolar(rocket.getCartesianCoordinate());
                double orbitCircumference = 2 * Math.PI * (destinationOrbit + destination.getRadius());
                double orbitTime = orbitCircumference / rocket.getVelocity();
                double orbitAngle = (359 * deltaTime) / orbitTime;
                coordinate.setAngle(coordinate.getAngle() + degreeToRadian(orbitAngle));
                returnCoordinate = polarToCartesian(coordinate);
            }
        }
        return returnCoordinate;
    }

    public CartesianCoordinate calculateStagePosition(Stage stage, double deltaTime) {
        // TODO : add trajectory system
        return stage.getCartesianCoordinate();
    }
}
