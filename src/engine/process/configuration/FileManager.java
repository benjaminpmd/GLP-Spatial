package engine.process.configuration;

import data.mission.Mission;
import engine.process.builders.RocketBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import log.LoggerUtility;
import org.apache.log4j.Logger;

public class FileManager {
    private final Logger logger = LoggerUtility.getLogger(FileManager.class, "html");
    private final String SEPARATOR = ";";

    public Mission importMission(String filePath) {
        Mission mission;
        RocketBuilder rocketBuilder = new RocketBuilder();
        String[] values;
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                content = line;
            }
            values = content.split(SEPARATOR);

            rocketBuilder.addStage(Integer.valueOf(values[4]), Integer.valueOf(values[6]), Integer.valueOf(values[7]), 1);
            rocketBuilder.addStage(Integer.valueOf(values[8]), Integer.valueOf(values[10]), Integer.valueOf(values[11]), 2);
            rocketBuilder.addPayload(values[0], Integer.valueOf(values[12]));
            reader.close();

            mission = new Mission();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    public void exportMission(Mission mission) {

    }
}
