package process.management;

import data.mission.Mission;
import data.rocket.Rocket;
import data.rocket.Stage;
import data.rocket.Tank;
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
 * the mission itself. Information are stored in different HashMaps that are stored in a main HashMap. We divide the information
 * in 4 Maps, the first one contains all the infos about the first stage, another one for the second stage, a third for the
 * payload and the last one is used to store data for the mission itself. Each key of the maps store the data of an attribute.
 * To simplify operations, keys and related attributes have the exact same name.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @see SimulationManager
 * @see CelestialObjectBuilder
 * @see SpaceCenterBuilder
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

    /**
     * Method that export a simulation settings into a file.
     *
     * @param manager {@link SimulationManager} the simulation to export.
     * @param outputPath {@link String} the path of the file to create, a ".launch" extension will be added to the path.
     */
    public void exportSimulation(SimulationManager manager, String outputPath) {
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
        firstStageParam.put("density", String.valueOf(tank.getPropellant().getDensity()));
        firstStageParam.put("thrust", String.valueOf(stage.getEngine().getThrust()));
        firstStageParam.put("propellantFlow", String.valueOf(stage.getEngine().getPropellantFlow()));
        firstStageParam.put("mass", String.valueOf(stage.getEngine().getMass()));
        firstStageParam.put("exhaustVelocity", String.valueOf(stage.getEngine().getExhaustVelocity()));
        firstStageParam.put("isp", String.valueOf(stage.getEngine().getIsp()));
        firstStageParam.put("thrustRatio", String.valueOf(stage.getEngine().getThrustRatio()));
        firstStageParam.put("capacity", String.valueOf(tank.getCapacity()));
        firstStageParam.put("engineNb", String.valueOf(stage.getEngineNb()));

        // second stage
        if (rocket.getSecondStage() != null) {
            stage = rocket.getSecondStage();
        } else {
            stage = releasedStages.get(1);
        }
        HashMap<String, String> secondStageParam = new HashMap<>();
        secondStageParam.put("density", String.valueOf(tank.getPropellant().getDensity()));
        secondStageParam.put("thrust", String.valueOf(stage.getEngine().getThrust()));
        secondStageParam.put("propellantFlow", String.valueOf(stage.getEngine().getPropellantFlow()));
        secondStageParam.put("mass", String.valueOf(stage.getEngine().getMass()));
        secondStageParam.put("exhaustVelocity", String.valueOf(stage.getEngine().getExhaustVelocity()));
        secondStageParam.put("isp", String.valueOf(stage.getEngine().getIsp()));
        secondStageParam.put("thrustRatio", String.valueOf(stage.getEngine().getThrustRatio()));
        secondStageParam.put("capacity", String.valueOf(tank.getCapacity()));
        secondStageParam.put("engineNb", String.valueOf(stage.getEngineNb()));

        // payload
        HashMap<String, String> payloadParam = new HashMap<>();
        payloadParam.put("mass", String.valueOf((int) rocket.getPayload().getMass()));

        // mission
        int orbit = mission.getOrbitAltitude() / 1000;
        HashMap<String, String> missionParam = new HashMap<>();
        missionParam.put("name", mission.getName());
        missionParam.put("description", mission.getDescription());
        missionParam.put("spaceCenterName", mission.getSpaceCenterName());
        missionParam.put("destinationName", mission.getDestinationName());
        missionParam.put("orbitAltitude", String.valueOf(orbit));

        save.put("firstStageParam", firstStageParam);
        save.put("secondStageParam", secondStageParam);
        save.put("payloadParam", payloadParam);
        save.put("missionParam", missionParam);

        if (!outputPath.endsWith(".launch")) {
            outputPath += ".launch";
        }

        try {
            ObjectOutputStream oos;
            new File(outputPath).createNewFile();
            logger.trace("File created.");
            oos = new ObjectOutputStream(new FileOutputStream(outputPath));
            oos.writeObject(save);
            logger.trace("Map saved.");
            oos.close();
            logger.trace("File closed.");
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
    public SimulationManager importSimulation(String filePath) throws IllegalArgumentException {

        HashMap<String, HashMap<String, String>> save;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            logger.trace("InputStream created");
            save = (HashMap<String, HashMap<String, String>>) ois.readObject();
            ois.close();
            logger.trace("Map successfully read");
        } catch (EOFException e) {
            logger.error(e);
            throw new IllegalArgumentException("Simulation data could not be retrieved, perhaps the file may be corrupted?");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("Simulation data could not be retrieved, perhaps the file may be corrupted?");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("Simulation data could not be retrieved, perhaps the file may be corrupted?");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("Simulation data could not be retrieved, perhaps the file may be corrupted?");
        }

        SimulationBuilder simulationBuilder = new SimulationBuilder(celestialObjectBuilder, spaceCenterBuilder);
        // payload
        String payloadMass = save.get("payloadParam").get("mass");
        SimulationManager manager = simulationBuilder.buildSimulation(save.get("firstStageParam"), save.get("secondStageParam"), payloadMass, save.get("missionParam"));

        return manager;
    }
}
