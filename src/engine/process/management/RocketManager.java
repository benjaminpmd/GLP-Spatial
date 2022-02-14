package process.management;

import model.rocket.Rocket;
import model.rocket.Stage;
import model.rocket.Tank;
import model.rocket.RocketEngine;

public class RocketManager {
    Rocket rocket;

    public void createRocket() {
        rocket = new Rocket();
    }

    public double calculateRocketWeight() {
        Stage firstStage = rocket.getFirstStage();
        Stage secondStage = rocket.getSecondStage();
        double weight = 0;
        if (firstStage != null) {
            weight += (firstStage.getEmptyWeight() + (firstStage.getTank().getRemainingPropellant() * firstStage.getTank().getPropellant().getDensity()) + (firstStage.getEngine() * firstStage.getEngineNb()));
        }
        if (secondStage != null) {
            weight += (secondStage.getEmptyWeight() + (secondStage.getTank().getRemainingPropellant() * secondStage.getTank().getPropellant().getDensity()) + (firstsecondStageStage.getEngine() * secondStage.getEngineNb()));
        }
        return weight;
    }

    /**
     * Check if the payload can be launched by the rocket
     *
     * @param payload The weight of the payload (in kg).
     * @return boolean, true if the weight is correct, false in the other case.
     */
    public boolean validPayloadWeight(Payload payload) {
        double thrust = rocket.getFirstStage().getEngineNb() * rocket.getFirstStage().getEngine().getThrust();
        return ((thrust * Constants.GRAVITY) > (calculateRocketWeight() + payload.getWeight()));
    }

    public void createStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp, int stageNb) throws IllegalArgumentException {
        Stage stage = new buildStage(tankCapacity, propellantDensity, propellantTemperature, engineNb, engineThrust, engineThrustRatio, engineIsp);
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        }
        else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        }
        else throw new IllegalArgumentException("The stage number must be 1 or 2");
    }

    public void createStage(int tankCapacity, int engineNb, int engineThrust, int stageNb) throws IllegalArgumentException {
        Stage stage = new buildStage(tankCapacity, engineNb, engineThrust);
        if (stageNb == 1) {
            rocket.setFirstStage(stage);
        }
        else if (stageNb == 2) {
            rocket.setSecondStage(stage);
        }
        else throw new IllegalArgumentException("The stage number must be 1 or 2");
    }

    public void updateRocket(double deltaTime) {
        Stage stage;
        if (rocket.getFirstStage() || rocket.getSecondStage()) {
            if (rocket.getFirstStage()) {
                // TODO: update weight (fuel consumed), remove first stage if fuel empty
                stage = rocket.getFirstStage();                
            }
            else {
                // TODO: update weight, remove S2 in case of fuel empty
                stage = rocket.getSecondStage();
            }
            double remainingPropellant = stage.getTank().getRemainingPropellant();
            double deltaPropellant = 
            tank.setRemainingPropellant();
        }
        else {
            // TODO: payload released
        }
        
    }
}
