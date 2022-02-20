package tests.unit;

import engine.process.builders.StageBuilder;
import data.rocket.Stage;
import data.rocket.Tank;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestStageBuild {
    
	private Stage stage;
    private Stage simpleStage;

    @Before
    public void prepareStage() {
    	StageBuilder stageBuilder = new StageBuilder();
        stage = stageBuilder.buildStage(400000, 0.81, -200, 9, 80000, 70, 31);
        simpleStage = stageBuilder.buildStage(24000, 4, 24000);
    }

    @Test
    public void testStage () {
        double emptyWeight = stage.getEmptyWeight();;

        assertNotNull(emptyWeight);
        assertEquals(22100, emptyWeight, 1000);
        assertTrue(stage.getTank() instanceof Tank);
    }

    @Test
    public void testSimpleStage () {
        double emptyWeight = simpleStage.getEmptyWeight();;

        assertNotNull(emptyWeight);
        assertEquals(2100, emptyWeight, 200);
    }
}
