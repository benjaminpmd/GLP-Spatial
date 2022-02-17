package engine.process.management;

import data.mission.Mission;
import data.mission.Planet;
import engine.data.Constants;
import engine.data.Coordinates;

/**
 * Class containing repositories for various elements such as Centers
 *
 * @author Benjamin P
 * @version 22.02.17 - I like math (1.0.0)
 * @see data.mission.Mission;
 * @since 14.02.22
 */
public class Simulation {

    private Mission mission;

    private RocketManager rocketManager = new RocketManager();

    private static Simulation instance = new Simulation();

    public static Simulation getInstance() {
        return instance;
    }

    // TODO: Method to update the earth rotation, rocket position, everything...
}
