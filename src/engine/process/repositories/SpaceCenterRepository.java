package engine.process.repositories;

import java.util.HashMap;
import java.util.Set;

/**
 * Class containing repositories for various elements such as Centers.
 *
 * @author Benjamin P
 * @version 22.03.06
 * @since 14.02.22
 */
public class SpaceCenterRepository {

    private HashMap<String, Integer> values = new HashMap<String, Integer>();
    private static SpaceCenterRepository instance = new SpaceCenterRepository();

    private SpaceCenterRepository() {}

    public static SpaceCenterRepository getInstance() {
        return instance;
    }

    public void register(String name, int value) {
        if (!values.containsKey(name)) {
            values.put(name, value);
        }
    }

    public int getValue(String name) throws IllegalArgumentException {
        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("The following name: " + name + " does not exist");
        }
        return values.get(name);
    }

    public Set<String> getNames() {
        return values.keySet();
    }
}
