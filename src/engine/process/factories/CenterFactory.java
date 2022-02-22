package engine.process.factories;

import engine.process.calculations.Calculation;
import data.mission.Center;
import org.apache.log4j.Logger;
import log.LoggerUtility;

public class CenterFactory {

    private static Logger logger = LoggerUtility.getLogger(CenterFactory.class, "html");
    private static Calculation calculation = new Calculation();

    public static Center createCenter(String name, int meridian) throws IllegalArgumentException {
        if ((meridian >= -180) && (meridian <= 180)) {
            if (meridian < 0) {
                meridian = 359 + meridian;
            }
            Center center = new Center(name, calculation.degreeToRadian(meridian));
            logger.info("Center creation: " + center);
            return center;
        }
        else throw new IllegalArgumentException("meridian must be between -180 and 180. Given meridian is: " + meridian);
    }
}
