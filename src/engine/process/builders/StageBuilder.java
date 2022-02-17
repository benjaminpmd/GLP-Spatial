package engine.process.builders;

import data.rocket.Propellant;
import data.rocket.RocketEngine;
import data.rocket.Stage;
import data.rocket.Tank;

/**
 * Builder class for stage object
 *
 * @author Benjamin P
 * @version 22.02.13 - To Infinity and beyond (1.0.0)
 * @see data.rocket.Tank
 * @see data.rocket.Propellant
 * @since 11.02.22
 */
public class StageBuilder {

    private Stage stage;

    public Stage buildStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, double engineThrust, double engineThrustRatio, int engineIsp) {

        // instantiating a new tank object
        Tank tank = new Tank(new Propellant(propellantDensity, propellantTemperature), tankCapacity);
        RocketEngineBuilder rocketEngineBuilder = new RocketEngineBuilder();
        RocketEngine rocketEngine = rocketEngineBuilder.buildRocketEngine(engineThrust, engineThrustRatio, engineIsp);
        // initiate and return the stage
        stage = new Stage(engineNb, rocketEngine, tank);
        // calculating stage weight
        double radius = Math.pow(((3 * tankCapacity) / (4 * Math.PI)), (1.0 / 3.0)) / 10;
        double stageWeight = (28.3 * ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3))) + rocketEngine.getWeight() * engineNb;
        stage.setEmptyWeight(stageWeight);
        return stage;
    }

    public Stage buildStage(int tankCapacity, int engineNb, int engineThrust) {

        // instantiating a new tank object
        Tank tank = new Tank(new Propellant(), tankCapacity);
        RocketEngineBuilder rocketEngineBuilder = new RocketEngineBuilder();
        RocketEngine rocketEngine = rocketEngineBuilder.buildRocketEngine(engineThrust);
        // initiate and return the stage
        stage = new Stage(engineNb, rocketEngine, tank);

        // calculating stage weight
        double radius = Math.pow(((3 * tankCapacity) / (4 * Math.PI)), (1.0 / 3.0)) / 10;
        double stageWeight = (28.3 * ((4.0 / 3.0) * Math.PI * Math.pow(radius, 3))) + rocketEngine.getWeight() * engineNb;
        stage.setEmptyWeight(stageWeight);
        return stage;
    }
}
