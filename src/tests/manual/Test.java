package tests.manual;

import data.mission.Mission;
import data.mission.Target;
import engine.process.builders.CelestialObjectBuilder;
import engine.process.builders.SpaceCenterBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {

        SpaceCenterBuilder scb = new SpaceCenterBuilder("./src/config/centers.csv");
        CelestialObjectBuilder cob = new CelestialObjectBuilder("./src/config/celestialObjects.csv");
        Mission m = new Mission(scb.buildSpaceCenter("Centre Spatial Guyanais"), new Target(cob.buildCelestialObject("Earth"), 200000));
        System.out.println(m.getName());
    }
}
