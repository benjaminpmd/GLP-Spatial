package engine.process.configuration;

import data.mission.Center;
import data.mission.Mission;
import data.mission.Planet;
import data.mission.Target;
import engine.data.Constants;
import engine.data.PolarCoordinates;
import engine.process.builders.RocketBuilder;
import engine.process.management.MissionManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LaunchConfigurator {
    private final String SEPARATOR = ";";

    public void createFromFile(String filePath) {
        MissionManager simulation = MissionManager.getInstance();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            String content = "";
            while ((line = reader.readLine()) != null) {
                content = line;
            }
            String[] values = content.split(SEPARATOR);
            for (String v: values) {
                System.out.println(v);
            }
            Planet earth = new Planet("Earth", new PolarCoordinates(0), Constants.EARTH_MASS, Constants.EARTH_RADIUS);
            MissionManager.getInstance().setMission(new Mission(values[0], new Center("test-center", 0), new Target(200, earth)));
            RocketBuilder rocketBuilder = new RocketBuilder();
            rocketBuilder.addStage(Integer.valueOf(values[4]), Integer.valueOf(values[6]), Integer.valueOf(values[7]), 1);
            rocketBuilder.addStage(Integer.valueOf(values[8]), Integer.valueOf(values[10]), Integer.valueOf(values[11]), 2);
            rocketBuilder.addPayload(values[0], Integer.valueOf(values[12]));


            reader.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
