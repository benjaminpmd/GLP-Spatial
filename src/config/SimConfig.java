package config;

import java.awt.*;

public class SimConfig {

    public static final int WINDOW_WIDTH = 1152;
    public static final int WINDOW_HEIGHT = 648;

    public static final int GRAPHIC_WIDTH = 655;
    public static final int GRAPHIC_HEIGHT = 435;
    public static final int GRAPHIC_CENTER_X = GRAPHIC_WIDTH / 2;
    public static final int GRAPHIC_CENTER_Y = GRAPHIC_HEIGHT / 2;
    public static final int DEFAULT_SCALE = 30000;

    public static final Dimension IDEAL_GRAPH_DIMENSION = new Dimension(200,200);

    // update speed of the screen in milliseconds
    public static final int SIMULATION_SPEED = 1000;

    // real time passed between 2 frames (in seconds)
    public static final int DELTA_TIME = 1;

    public static final String CENTERS_PATH = "./src/config/centers.csv";
    public static final String CELESTIAL_OBJECTS_PATH = "./src/config/celestialObjects.csv";
}
