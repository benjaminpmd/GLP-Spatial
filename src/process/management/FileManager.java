package process.management;

import config.SimConfig;
import data.mission.Mission;
import data.rocket.Rocket;
import process.builders.CelestialObjectBuilder;

import log.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;

/**
 * Class to manage the import and export of a simulation settings including the rocket and all the information about
 * the mission itself.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see data.mission.Mission
 * @see data.rocket.Rocket
 * @since 07.03.22
 */
public class FileManager {

    private final Logger logger = LoggerUtility.getLogger(FileManager.class, "html");
    private CelestialObjectBuilder celestialObjectBuilder;

    public FileManager(CelestialObjectBuilder celestialObjectBuilder) {
        this.celestialObjectBuilder = celestialObjectBuilder;
    }

    public FileManager() {
         celestialObjectBuilder = new CelestialObjectBuilder(SimConfig.CELESTIAL_OBJECTS_PATH);
    }

    /**
     * Method that export a simulation settings into a file.
     *
     * @param mission    {@link Mission} the mission to save.
     * @param rocket     {@link Rocket} the rocket that should be saved.
     * @param outputPath {@link String} the path of the file to create, a ".launch" extension will be added to the path.
     */
    public void exportSimulation(Mission mission, Rocket rocket, String outputPath) {

        HashMap<String, Object> saveMap = new HashMap<>();
        saveMap.put("mission", mission);
        saveMap.put("rocket", rocket);
        logger.trace("Object map has been created and filled.");
        ObjectOutputStream oos;

        try {
            new File(outputPath + ".launch").createNewFile();
            logger.trace("File has been created.");
            oos = new ObjectOutputStream(new FileOutputStream(outputPath));
            oos.writeObject(saveMap);
            oos.close();
            logger.trace("save map has been saved.");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Method to import simulation settings from a file.
     *
     * @param filePath {@link String} the path of the file to import the settings from.
     * @return {@link SimulationManager} a SimulationManager object ready to be used.
     * @throws IllegalArgumentException in case the simulation could not be correctly initiated
     */
    public SimulationManager importSimulation(String filePath) throws IllegalArgumentException {

        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath));
            logger.trace("ObjectInputStream created");
            HashMap<String, Object> saveMap;

            while (oos.readObject() != null) {
                saveMap = (HashMap<String, Object>) oos.readObject();
                oos.close();
                logger.trace("map successfully read");

                return new SimulationManager((Rocket) saveMap.get("rocket"), (Mission) saveMap.get("mission"), celestialObjectBuilder);
            }

        } catch (EOFException e) {
            logger.error(e.getMessage());

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());

        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Simulation could not be initiated correctly, perhaps the file may be corrupted?");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
