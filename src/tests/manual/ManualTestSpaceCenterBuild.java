package tests.manual;

import process.builders.SpaceCenterBuilder;

public class ManualTestSpaceCenterBuild {
    public static void main(String[] args) {
        SpaceCenterBuilder cob = new SpaceCenterBuilder("./src/config/centers.csv");
        System.out.println(cob.buildSpaceCenter("Centre Spatial Guyanais"));
    }
}
