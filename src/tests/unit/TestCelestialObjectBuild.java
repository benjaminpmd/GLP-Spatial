package tests.unit;

import engine.process.builders.CelestialObjectBuilder;
import engine.process.repositories.CelestialObjectRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCelestialObjectBuild {
    private CelestialObjectBuilder cob;

    @Before
    public void prepareStage() {
        cob = new CelestialObjectBuilder("./src/config/celestialObjects.csv");
    }

    @Test
    public void testCreation () {
        CelestialObjectRepository cor = CelestialObjectRepository.getInstance();
        assertNotNull(cor.getValue("Earth"));
    }
}
