package tests.unit;
import static org.junit.Assert.assertEquals;

import data.rocket.Stage;
import org.junit.Before;
import org.junit.Test;
import process.builders.StageBuilder;


public class StageBuildTest {

    private Stage stage;

    @Before
    public void prepareCalculation() {
        StageBuilder stageBuilder = new StageBuilder();

        stage = stageBuilder.buildStage(218750, 0.8, -200, 1, 1350000, 76.50, 434);
    }

    @Test
    public void TestEngineValues() {
        assertEquals(1800, stage.getEngine().getMass(), 10);
        assertEquals(320, stage.getEngine().getPropellantFlow(), 10);
    }

    @Test
    public void TestStageValues() {
        assertEquals(175000, stage.getTank().getRemainingPropellant(), 100);
        assertEquals(10500, stage.getMass(), 300);
    }
}
