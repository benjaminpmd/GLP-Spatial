package config;

import java.awt.*;

public class SimConfig {

    public static final int WINDOW_WIDTH = 1152;
    public static final int WINDOW_HEIGHT = 648;

    public static final Dimension IDEAL_GRAPH_DIMENSION = new Dimension(190,210);

    // update speed of the screen in milliseconds
    public static final int SIMULATION_SPEED = 1000;

    // real time passed between 2 frames (in seconds)
    public static final int DELTA_TIME = 1;

    public static final int MIN_ENGINES = 1;
    public static final int MAX_ENGINES = 32;
    
    public static final String CENTERS_PATH = "./centers/centers.csv";
    public static final boolean beginnerMode = false;
}
