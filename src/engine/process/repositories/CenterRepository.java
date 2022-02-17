package engine.process.repositories;

import data.mission.Center;

import java.util.HashMap;

/**
 * Class containing repositories for various elements such as Centers
 *
 * @author Benjamin P
 * @version 22.02.14 - Make it real! (1.0.0)
 * @see data.mission.Center
 * @since 14.02.22
 */
public class CenterRepository {

    private HashMap<String, Center> centers = new HashMap<String, Center>();

    private static CenterRepository instance = new CenterRepository();

    public static CenterRepository getInstance() {
        return instance;
    }

    public void register(Center center) {
        if (!centers.containsKey(center.getName())) {
            centers.put(center.getName(), center);
        }
    }

    public Center getCenter(String name) {
        if (centers.containsKey(name)) {
            return centers.get(name);
        }
        return null;
    }

    public HashMap<String, Center> getCenters() {
        return centers;
    }
}
