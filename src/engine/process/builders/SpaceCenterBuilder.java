package engine.process.builders;

import data.coordinate.PolarCoordinate;
import data.mission.SpaceCenter;
import engine.data.Constants;
import engine.process.calculations.Calculation;
import engine.process.repositories.SpaceCenterRepository;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to build space centers.
 * TODO: add logging
 *
 * @author Benjamin P
 * @version 22.03.06 (0.9.0)
 * @see data.mission.SpaceCenter
 * @see SpaceCenterRepository
 * @since 14.02.22
 */
public class SpaceCenterBuilder {

    private Logger logger = LoggerUtility.getLogger(SpaceCenterBuilder.class, "html");

    private String SEPARATOR = ";";
    private SpaceCenterRepository spaceCenterRepository = SpaceCenterRepository.getInstance();

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
    }

    /**
     * Build a space center registered in the {@link engine.process.repositories.SpaceCenterRepository}
     *
     * @param name the name of the space center
     * @return a SpaceCenter object
     */
    public SpaceCenter buildSpaceCenter(String name) {

        SpaceCenter spaceCenter = new SpaceCenter();
        try {
            int radiusValue = spaceCenterRepository.getValue(name);
            PolarCoordinate pc = new PolarCoordinate(Constants.EARTH_RADIUS, Calculation.degreeToRadian(radiusValue));
            spaceCenter.setName(name);
            spaceCenter.setCartesianCoordinate(Calculation.polarToCartesian(pc));

            return spaceCenter;

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
