package engine.process.builders;

import data.mission.CelestialObject;
import engine.process.repositories.CelestialObjectRepository;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to build various celestial objects. All the objects must be registered in the CSV file passed to the constructor.
 * Once the object is initialized, the build method allows to get a built celestial object stored in the CelestialObjectRepository.
 * TODO: add logging
 *
 * @author Benjamin P
 * @version 22.03.06 (0.9.0)
 * @see CelestialObject
 * @see CelestialObjectRepository
 * @since 14.02.22
 */
public class CelestialObjectBuilder {

    private Logger logger = LoggerUtility.getLogger(SpaceCenterBuilder.class, "html");

    private String SEPARATOR = ";";
    private CelestialObjectRepository celestialObjectRepository = CelestialObjectRepository.getInstance();

    public CelestialObjectBuilder(String filePath) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {

                // for each line extract the name and an array of two String,
                // The first one is expected to be the radius
                // The second one is expected to be the mass
                String[] values = line.split(SEPARATOR);
                String[] props = {values[1], values[2]};

                // registering them into the repository
                celestialObjectRepository.register(values[0], props);
                logger.info("New CelestialObject data registered: " + values[0]);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Build a celestial object registered in the {@link engine.process.repositories.CelestialObjectRepository}
     *
     * @param name the name of the celestial object you want to build
     * @return a CelestialObject
     */
    public CelestialObject buildCelestialObject(String name) {

        CelestialObject celestialObject;
        try {
            // try to get the String array with the provided name.
            String[] props = celestialObjectRepository.getValue(name);
            celestialObject = new CelestialObject(name, Integer.valueOf(props[0]), Double.valueOf(props[1]));

            return celestialObject;

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
