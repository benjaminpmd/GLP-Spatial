package config;

import java.awt.*;

/**
 * Class containing all the constants and paths used for the simulation configuration.
 *
 * @author Lucas L
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class SimConfig {

    //Dimensions of all the windows
    public static final int WINDOW_WIDTH = 1152;
    public static final int WINDOW_HEIGHT = 648;

    //Dimensions of the trajectory display in SimulationGUI
    public static final int GRAPHIC_WIDTH = 655;
    public static final int GRAPHIC_HEIGHT = 435;
    public static final int GRAPHIC_CENTER_X = GRAPHIC_WIDTH / 2;
    public static final int GRAPHIC_CENTER_Y = GRAPHIC_HEIGHT / 2;
    public static final int MIN_SCALE = 2048;
    public static final int DEFAULT_SCALE = 30000;

    //Relative position of the telemetry panels in SimulationGUI
    public static final int TELEMETRY_CENTER_XSPEED = 60;
    public static final int TELEMETRY_CENTER_YSPEED = 100;
    public static final int TELEMETRY_CENTER_XALTITUDE = 200;
    public static final int TELEMETRY_CENTER_YALTITUDE = 100;

    //Relative position of the Rocket Schema in SimulationGUI
    public static final int ROCKET_SCHEMA_X = 270;
    public static final int ROCKET_SCHEMA_Y = 20;

    // Dimension of the graphs used in SimulationGUI
    public static final Dimension IDEAL_GRAPH_DIMENSION = new Dimension(200, 200);

    // Update speed of the screen in milliseconds
    public static final int SIMULATION_SPEED = 1000;

    // Real time passed between 2 frames (in seconds)
    public static final int DELTA_TIME = 1;

    // Relative path of all the files and directories used in the project
    public static final String CENTERS_PATH = "./assets/csv/centers.csv";
    public static final String CELESTIAL_OBJECTS_PATH = "./assets/csv/celestialObjects.csv";
    public static final String IMAGE_PATH = "./assets/img/";
}
