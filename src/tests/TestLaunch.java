package tests;

import data.mission.Center;
import data.rocket.Rocket;
import engine.process.builders.RocketBuilder;
import engine.process.configuration.LaunchConfigurator;
import engine.process.management.MissionManager;
import engine.process.repositories.CenterRepository;

public class TestLaunch {
    public static void main(String[] args) {
        CenterRepository cr = CenterRepository.getInstance();
        for (Center c: cr.getCenters().values()) {
            System.out.println(c);
        }
        RocketBuilder rocketBuilder = new RocketBuilder();
        rocketBuilder.addStage(10000, 3, 2000, 1);
        rocketBuilder.addStage(7000, 1, 2200, 2);
        rocketBuilder.addPayload("test", 200);
        Rocket rocket = rocketBuilder.getBuiltRocket();
        System.out.println(rocket);

        LaunchConfigurator lc = new LaunchConfigurator();
        lc.createFromFile("src/launch.csv");
        System.out.println(MissionManager.getInstance().getMission());
    }
}
