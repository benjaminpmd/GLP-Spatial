package process.management;

import config.SimConfig;
import data.mission.Mission;
import data.rocket.Rocket;
import data.rocket.Stage;
import data.rocket.Tank;
import exceptions.MissingPartException;
import exceptions.TooLowThrustException;
import log.LoggerUtility;
import org.apache.log4j.Logger;
import process.builders.CelestialObjectBuilder;
import process.builders.SimulationBuilder;
import process.builders.SpaceCenterBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.List;

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
    private final CelestialObjectBuilder celestialObjectBuilder;
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
    public void exportSimulation(SimulationManager manager, String outputPath) {
        // TODO : add ISP and Thrust to weight ratio to stages
        Rocket rocket = manager.getRocket();
        List<Stage> releasedStages = manager.getReleasedStages();
        Mission mission = manager.getMission();

        HashMap<String, HashMap<String, String>> save = new HashMap<>();
        Stage stage;

        // first stage
        if (rocket.getFirstStage() != null) {
            stage = rocket.getFirstStage();
        } else {
            stage = releasedStages.get(0);
        }
        Tank tank = stage.getTank();
        HashMap<String, String> firstStageParam = new HashMap<>();
        //propellant
        firstStageParam.put("propellantDensity", String.valueOf(tank.getPropellant().getDensity()));
        // tank
        firstStageParam.put("tankCapacity", String.valueOf((int) tank.getCapacity()));
        // engine
        firstStageParam.put("engineThrust", String.valueOf(stage.getEngine().getThrust()));
        firstStageParam.put("propellantFlow", String.valueOf(stage.getEngine().getPropellantFlow()));
        firstStageParam.put("mass", String.valueOf(stage.getEngine().getMass()));
        firstStageParam.put("exhaustVelocity", String.valueOf(stage.getEngine().getExhaustVelocity()));
        // stage
        firstStageParam.put("engineNb", String.valueOf(stage.getEngineNb()));

        // second stage
        if (rocket.getSecondStage() != null) {
            stage = rocket.getSecondStage();
        } else {
            stage = releasedStages.get(1);
        }
        HashMap<String, String> secondStageParam = new HashMap<>();
        //propellant
        secondStageParam.put("propellantDensity", String.valueOf(tank.getPropellant().getDensity()));
        // tank
        secondStageParam.put("tankCapacity", String.valueOf((int) tank.getCapacity()));
        // engine
        secondStageParam.put("engineThrust", String.valueOf(stage.getEngine().getThrust()));
        secondStageParam.put("propellantFlow", String.valueOf(stage.getEngine().getPropellantFlow()));
        secondStageParam.put("mass", String.valueOf(stage.getEngine().getMass()));
        secondStageParam.put("exhaustVelocity", String.valueOf(stage.getEngine().getExhaustVelocity()));
        // stage
        secondStageParam.put("engineNb", String.valueOf(stage.getEngineNb()));

        // payload
        HashMap<String, String> payloadParam = new HashMap<>();
        payloadParam.put("mass", String.valueOf((int) rocket.getPayload().getMass()));

        // mission
        HashMap<String, String> missionParam = new HashMap<>();
        missionParam.put("name", mission.getName());
        missionParam.put("description", mission.getDescription());
        missionParam.put("spaceCenterName", mission.getSpaceCenterName());
        missionParam.put("destinationName", mission.getDestinationName());
        missionParam.put("orbitAltitude", String.valueOf(mission.getOrbitAltitude()));
        missionParam.put("launchTime", String.valueOf(mission.getLaunchTime().getTime()));


        save.put("firstStageParam", firstStageParam);
        save.put("secondStageParam", secondStageParam);
        save.put("payloadParam", payloadParam);
        save.put("missionParam", missionParam);

        logger.trace("Object map has been created and filled.");
        ObjectOutputStream oos;

        if (!outputPath.endsWith(".launch")) {
            outputPath += ".launch";
        }
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
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            logger.trace("ObjectInputStream created");
            save = (HashMap<String, HashMap<String, String>>) ois.readObject();
            ois.close();
            logger.trace("map successfully read");
        } catch (EOFException e) {
            System.err.println(e);
            logger.error(e);

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());

        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Simulation data could not be retrieved, perhaps the file may be corrupted?");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        SimulationBuilder simulationBuilder = new SimulationBuilder(celestialObjectBuilder, spaceCenterBuilder);

        // payload
        String payloadMass = save.get("payloadParam").get("mass");

        // mission
        String missionName = save.get("missionParam").get("name");
        String missionDescription = save.get("missionParam").get("description");
        String spaceCenterName = save.get("missionParam").get("spaceCenterName");
        String destinationName = save.get("missionParam").get("destinationName");
        String orbit = save.get("missionParam").get("orbitAltitude");

        SimulationManager manager = simulationBuilder.buildSimulation(save.get("firstStageParam"), save.get("secondStageParam"), payloadMass, missionName, missionDescription, spaceCenterName, destinationName, orbit);


        return manager;
    }
}
