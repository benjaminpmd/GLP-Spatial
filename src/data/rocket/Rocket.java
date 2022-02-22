package data.rocket;

// TODO: make the rocket singleton

import engine.data.PolarCoordinates;

/**
 * Class that contains information about the rocket
 *
 * @author Alice M, Benjamin P
 * @version 22.02.11 - thrust me (1.0.0)
 * @see Stage
 * @see Payload
 * @since 11.02.22
 */
public class Rocket {

    private Stage firstStage;
    private Stage secondStage;
    private Payload payload;
    private PolarCoordinates polarCoordinates;
    private double velocity;

    public Rocket() {
        velocity = 0;
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "firstStage=" + firstStage +
                ", secondStage=" + secondStage +
                ", payload=" + payload +
                '}';
    }

    public Stage getFirstStage() {
        return firstStage;
    }

    public void setFirstStage(Stage firstStage) {
        this.firstStage = firstStage;
    }

    public Stage getSecondStage() {
        return secondStage;
    }

    public void setSecondStage(Stage secondStage) {
        this.secondStage = secondStage;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public PolarCoordinates getCoordinates() {
        return polarCoordinates;
    }

    public void setCoordinates(PolarCoordinates polarCoordinates) {
        this.polarCoordinates = polarCoordinates;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getWeight() {
        double weight = 0;
        if (firstStage != null) {
            weight += (firstStage.getEmptyWeight() + (firstStage.getTank().getRemainingPropellant() * firstStage.getTank().getPropellant().getDensity()) + (firstStage.getEngine().getWeight() * firstStage.getEngineNb()));
        }
        if (secondStage != null) {
            weight += (secondStage.getEmptyWeight() + (secondStage.getTank().getRemainingPropellant() * secondStage.getTank().getPropellant().getDensity()) + (secondStage.getEngine().getWeight() * secondStage.getEngineNb()));
        }
        if (payload != null) {
            weight += payload.getWeight();
        }
        return weight;
    }

    public double getThrust() {
        if (firstStage != null) {
            return (firstStage.getEngine().getThrust() * firstStage.getEngineNb());
        }
        else if (secondStage != null) {
            return (secondStage.getEngine().getThrust() * secondStage.getEngineNb());
        }
        else return 0;
    }
}
