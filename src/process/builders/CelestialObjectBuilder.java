package process.builders;

import data.coordinate.CartesianCoordinate;
import data.coordinate.PolarCoordinate;
import data.mission.CelestialObject;
import process.management.Calculation;
import process.repositories.CelestialObjectRepository;
import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to build various celestial objects. All the objects must be registered in the CSV file passed to the constructor.
 * Once the object is initialized, the build method allows to get a built celestial object stored in the CelestialObjectRepository.
 *
 * @author Benjamin P
 * @version 22.03.13 (0.1.0)
 * @see CelestialObject
 * @see CelestialObjectRepository
 * @since 14.02.22
 */
public class CelestialObjectBuilder {

    private final Logger logger = LoggerUtility.getLogger(CelestialObjectBuilder.class, "html");

    private final String SEPARATOR = ";";

    private final CelestialObjectRepository celestialObjectRepository = CelestialObjectRepository.getInstance();

    private final Calculation calculation = new Calculation();

    /**
     * Constructor of the CelestialObjectBuilder.
     *
     * @param filePath {@link String} the path of the CSV file where the celestials object data are stored.
     */
    public CelestialObjectBuilder(String filePath) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {

                // for each line extract the name and an array of two String,
                // The first one is expected to be the radius
                // The second one is expected to be the mass
                String[] values = line.split(SEPARATOR);
                String[] props = {values[1], values[2], values[3], values[4]};

                // registering them into the repository
                celestialObjectRepository.register(values[0], props);
                logger.trace("New CelestialObject data registered: " + values[0]);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Build a celestial object registered in the {@link process.repositories.CelestialObjectRepository}.
     *
     * @param name {@link String} the name of the celestial object you want to build.
     * @return {@link CelestialObject}
     */
    public CelestialObject buildCelestialObject(String name) {

        try {
            // try to get the String array with the provided name.
            String[] props = celestialObjectRepository.getValue(name);

            PolarCoordinate polarCoordinate = new PolarCoordinate(Double.valueOf(props[2]), Double.valueOf(props[3]));
            CartesianCoordinate coordinate = calculation.polarToCartesian(polarCoordinate);

            return new CelestialObject(name, Integer.valueOf(props[0]), Double.valueOf(props[1]), coordinate);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
