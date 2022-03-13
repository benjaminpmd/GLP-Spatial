package tests.manual;

import process.builders.CelestialObjectBuilder;

public class ManualTestCelestialObjectBuild {
    public static void main(String[] args) {
        CelestialObjectBuilder cob = new CelestialObjectBuilder("./src/config/celestialObjects.csv");
        System.out.println(cob.buildCelestialObject("Earth"));
    }
}
