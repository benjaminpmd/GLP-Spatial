package engine.process.builders;

import model.rocket.Propellant;
import model.rocket.Stage;
import model.rocket.Tank;

/**
 * Builder class for stage object
 * @author Benjamin P
 * @since 11.02.22
 * @version 22.02.11 - thrust me (1.0.0)
 * @see {@link model.rocket.Tank}, {@link model.rocket.Propellant}
 */
public class StageBuilder {

    private Stage stage;

    public Stage buildStage(int tankCapacity, double propellantDensity, int propellantTemperature, int engineNb, int engineThrust, int engineThrustRatio, int engineIsp) {
        
        // instanciating a new tank object
        Tank tank = new Tank(new Propellant(propellantDensity, propellantTemperature), tankCapacity);
        RocketEngineBuilder rocketEngineBuilder = new RocketEngineBuilder();
        // initiate and return the stage
        stage = new Stage(engineNb, rocketEngineBuilder.buildRocketEngine(engineThrust, engineThrustRatio, engineIsp), tank);
        return stage;
    }

    public Stage buildStage(int tankCapacity, int engineNb, int engineThrust) {
        
        // instanciating a new tank object
        Tank tank = new Tank(new Propellant(), tankCapacity);
        RocketEngineBuilder rocketEngineBuilder = new RocketEngineBuilder();
        // initiate and return the stage
        stage = new Stage(engineNb, rocketEngineBuilder.buildRocketEngine(engineThrust), tank);
        return stage;
    }
}
