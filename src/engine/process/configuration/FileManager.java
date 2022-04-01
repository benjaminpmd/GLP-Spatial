package engine.process.configuration;

import data.mission.Center;
import data.mission.Mission;
import data.mission.Target;
import data.rocket.Rocket;
import engine.process.factories.RocketFactory;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import engine.process.repositories.CenterRepository;
import log.LoggerUtility;
import org.apache.log4j.Logger;

public class FileManager {
    private final Logger logger = LoggerUtility.getLogger(FileManager.class, "html");
    private final String SEPARATOR = ";";

    public Mission importMission(String filePath) {
        Mission mission;
        RocketFactory rocketFactory = new RocketFactory();
        String[] values;
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content = line;
            }
            reader.close();
            values = content.split(SEPARATOR);

            rocketFactory.createStage(Integer.valueOf(values[3]), Integer.valueOf(values[5]), Integer.valueOf(values[6]), 1);
            rocketFactory.createStage(Integer.valueOf(values[7]), Integer.valueOf(values[9]), Integer.valueOf(values[11]), 2);
            rocketFactory.createPayload(values[0], Integer.valueOf(values[12]));
            Rocket rocket = rocketFactory.getBuiltRocket();
            CenterRepository centerRepo = CenterRepository.getInstance();
            Center center = centerRepo.getCenter(values[2]);
            return new Mission(values[0], values[1], center, new Target(Integer.valueOf(values[12])), rocket);
        } catch (FileNotFoundException e) {
            logger.info(filePath);
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    public void exportMission(Mission mission) {

    }
}