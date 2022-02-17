package engine.process.builders;

import engine.process.factories.CenterFactory;
import engine.process.repositories.CenterRepository;
import data.mission.Center;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CenterBuilder {

    private Logger logger = LoggerUtility.getLogger(CenterBuilder.class, "html");

    private String SEPARATOR = ";";
    private String filePath;
    private CenterRepository centerRepository = CenterRepository.getInstance();

    public CenterBuilder(String filePath) {
        this.filePath = filePath;
    }

    public void buildCenters() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(SEPARATOR);
                Center center = CenterFactory.createCenter(values[0], Integer.valueOf(values[1]));
                centerRepository.register(center);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
