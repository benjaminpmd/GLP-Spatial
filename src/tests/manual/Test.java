package tests.manual;

import data.coordinate.CartesianCoordinate;
import data.mission.Mission;
import data.rocket.Rocket;
import gui.MainGUI;
import process.builders.CelestialObjectBuilder;
import process.builders.RocketBuilder;
import process.builders.SpaceCenterBuilder;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {

        Double.valueOf("70.8");

        HashMap<String, String>firstStageParam = new HashMap<>();
        firstStageParam.put("tankCapacity", "20000");
        firstStageParam.put("propellantDensity", "0.72");
        firstStageParam.put("propellantTemperature", "-250");
        firstStageParam.put("engineNb", "3");
        firstStageParam.put("engineThrust", "20000");
        firstStageParam.put("engineThrustRatio", "65");
        firstStageParam.put("isp", "400");

        HashMap<String, String>secondStageParam = new HashMap<>();
        secondStageParam.put("tankCapacity", "2000");
        secondStageParam.put("propellantDensity", "0.72");
        secondStageParam.put("propellantTemperature", "-250");
        secondStageParam.put("engineNb", "1");
        secondStageParam.put("engineThrust", "30000");
        secondStageParam.put("engineThrustRatio", "65");
        secondStageParam.put("isp", "280");

        /**
        RocketBuilder rb = new RocketBuilder();
        try {
            Rocket rocket = rb.buildRocket(firstStageParam, secondStageParam, 1500, new CartesianCoordinate());
            System.out.println(rocket.toString());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
         */
        new MainGUI("Test");
    }
}
