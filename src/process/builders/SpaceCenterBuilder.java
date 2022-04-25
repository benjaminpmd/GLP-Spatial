package process.builders;

import config.Constants;
import data.coordinate.PolarCoordinate;
import data.mission.SpaceCenter;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.management.Calculation;
import process.repositories.SpaceCenterRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to build space centers.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see data.mission.SpaceCenter
 * @see SpaceCenterRepository
 * @since 14.02.22
 */
public class SpaceCenterBuilder {

    private final Logger logger = LoggerUtility.getLogger(SpaceCenterBuilder.class, "html");

    private final String SEPARATOR = ";";

    private final Calculation calculation;

    private final SpaceCenterRepository spaceCenterRepository = SpaceCenterRepository.getInstance();

    /**
     * Constructor of the SpaceCenter.
     */
    public SpaceCenterBuilder(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {

                // for each line extract the name and an angle in degree,
                String[] values = line.split(SEPARATOR);

                // registering them into the repository
                spaceCenterRepository.register(values[0], Integer.valueOf(values[1]));
                logger.info("New SpaceCenter data registered: " + values[0]);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        calculation = new Calculation();
    }

    /**
     * Build a space center registered in the {@link process.repositories.SpaceCenterRepository}.
     *
     * @param name {@link String} the name of the space center
     * @return {@link SpaceCenter}
     */
    public SpaceCenter buildSpaceCenter(String name) {
        try {
            int angleValue = spaceCenterRepository.getValue(name);
            PolarCoordinate polarCoordinate = new PolarCoordinate(Constants.EARTH_RADIUS, Math.toRadians(-angleValue));
            return new SpaceCenter(name, calculation.polarToCartesian(polarCoordinate));

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
