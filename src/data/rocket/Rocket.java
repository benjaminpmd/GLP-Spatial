package data.rocket;

import data.MobileElement;
import data.coordinate.CartesianCoordinate;

/**
 * Class that contains information about the rocket.
 *
 * @author Alice M, Benjamin P
 * @version 22.04.28 (1.0.0)
 * @see data.rocket.Stage
 * @see data.rocket.Payload
 * @since 22.02.11
 */
public class Rocket extends MobileElement {

    private Stage firstStage;
    private Stage secondStage;
    private Payload payload;

    /**
     * Constructor of the Rocket.
     */
    public Rocket(Stage firstStage, Stage secondStage, Payload payload, CartesianCoordinate cartesianCoordinate) {
        super(0, cartesianCoordinate);
        this.firstStage = firstStage;
        this.secondStage = secondStage;
        this.payload = payload;
        double mass = 0;
        if (firstStage != null) {
            mass += (firstStage.getMass() + (firstStage.getTank().getRemainingPropellant() * firstStage.getTank().getPropellant().getDensity()) + (firstStage.getEngine().getMass() * firstStage.getEngineNb()));
        }
        if (secondStage != null) {
            mass += (secondStage.getMass() + (secondStage.getTank().getRemainingPropellant() * secondStage.getTank().getPropellant().getDensity()) + (secondStage.getEngine().getMass() * secondStage.getEngineNb()));
        }
        if (payload != null) {
            mass += payload.getMass();
        }
        setMass(mass);
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "firstStage=" + firstStage +
                ", secondStage=" + secondStage +
                ", payload=" + payload +
                "} " + super.toString();
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
}
