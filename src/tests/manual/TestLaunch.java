package tests.manual;

import data.mission.Center;
import data.rocket.Rocket;
import engine.process.builders.RocketBuilder;
import engine.process.configuration.FileManager;
import engine.process.configuration.LaunchConfigurator;
import engine.process.management.MissionManager;
import engine.process.repositories.CenterRepository;

public class TestLaunch {
    public static void main(String[] args) {
        CenterRepository cr = CenterRepository.getInstance();
        for (Center c: cr.getCenters().values()) {
            System.out.println(c);
        }

        FileManager fileManager = new FileManager();
        LaunchConfigurator launchConfigurator = new LaunchConfigurator();
        launchConfigurator.createLaunch("src/launch.csv");

        MissionManager mM = MissionManager.getInstance();
        for (int i = 0; i < 10; i++) {
            mM.next();
            System.out.println(mM.getMission().getRocket().getCoordinates());
        }
    }
}
