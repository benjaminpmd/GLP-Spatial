package process.management;

import config.SimConfig;
import exceptions.MissingPartException;
import exceptions.TooLowThrustException;
import process.builders.CelestialObjectBuilder;
import process.builders.SimulationBuilder;

import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.builders.SpaceCenterBuilder;

import java.io.*;
import java.util.HashMap;

/**
 * Class to manage the import and export of a simulation settings including the rocket and all the information about
 * the mission itself.
 *
 * //TODO: fix import/export system.
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
    private SpaceCenterBuilder spaceCenterBuilder;

    public FileManager(CelestialObjectBuilder celestialObjectBuilder, SpaceCenterBuilder spaceCenterBuilder) {
        this.celestialObjectBuilder = celestialObjectBuilder;
        this.spaceCenterBuilder = spaceCenterBuilder;
    }

    public FileManager() {
         celestialObjectBuilder = new CelestialObjectBuilder(SimConfig.CELESTIAL_OBJECTS_PATH);
    }

    /**
     * Method that export a simulation settings into a file.
     *
     * @param outputPath {@link String} the path of the file to create, a ".launch" extension will be added to the path.
     */
    public void exportSimulation(HashMap<String, String>firstStageParam, HashMap<String, String>secondStageParam, HashMap<String, String>payloadParam, HashMap<String, String>missionParam, String outputPath) {
        HashMap<String, HashMap<String, String>> save = new HashMap<>();

        save.put("firstStageParam", firstStageParam);
        save.put("secondStageParam", secondStageParam);
        save.put("payloadParam", payloadParam);
        save.put("missionParam", missionParam);

        logger.trace("Object map has been created and filled.");
        ObjectOutputStream oos;

        outputPath += ".launch";
        try {
            new File(outputPath).createNewFile();
            logger.trace("File has been created.");
            oos = new ObjectOutputStream(new FileOutputStream(outputPath));
            oos.writeObject(save);
            logger.trace("map has been saved.");
            oos.close();
            logger.trace("oos closed.");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Method to import simulation settings from a file.
     *
     * @param filePath {@link String} the path of the file to import the settings from.
     * @return {@link SimulationManager} a SimulationManager object ready to be used.
     * @throws IllegalArgumentException in case the simulation could not be correctly initiated
     */
    public SimulationManager importSimulation(String filePath) throws IllegalArgumentException, MissingPartException, TooLowThrustException {

        HashMap<String, HashMap<String, String>> save = null;
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath));
            logger.trace("ObjectInputStream created");
            save = new HashMap<>();

            while (oos.readObject() != null) {
                save = (HashMap<String, HashMap<String, String>>) oos.readObject();
                System.out.println("t");
                for (HashMap<String, String> map : save.values()) {
                    for (String val : map.values()) {
                        System.out.println(val);
                    }
                }
                oos.close();
                logger.trace("map successfully read");
            }
        } catch (EOFException e) {
            System.err.println(e.getMessage());
            logger.error(e);

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());

        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Simulation could not be initiated correctly, perhaps the file may be corrupted?");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        SimulationBuilder simulationBuilder = new SimulationBuilder(celestialObjectBuilder, spaceCenterBuilder);


        String payloadMass = save.get("payloadParam").get("mass");

        String missionName = save.get("missionParam").get("missionName");
        String missionDescription = save.get("missionParam").get("missionDescription");
        String spaceCenterName = save.get("missionParam").get("spaceCenterName");
        String destinationName = save.get("missionParam").get("destinationName");
        String orbit = save.get("missionParam").get("orbit");

        SimulationManager manager = simulationBuilder.buildSimulation(save.get("firstStageParam"), save.get("secondStageParam"), payloadMass, missionName, missionDescription, spaceCenterName, destinationName, Integer.valueOf(orbit));


        return manager;
    }
}
