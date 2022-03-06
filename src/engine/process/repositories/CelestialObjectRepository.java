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
public class CelestialObjectRepository {

    private HashMap<String, String[]> values = new HashMap<String, String[]>();

    private static CelestialObjectRepository instance = new CelestialObjectRepository();

    private CelestialObjectRepository() {}

    public static CelestialObjectRepository getInstance() {
        return instance;
    }

    public void register(String name, String value[]) {
        if (!values.containsKey(name)) {
            values.put(name, value);
        }
    }

    public String[] getValue(String name) throws IllegalArgumentException {
        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("The following name: " + name + " does not exist");
        }
        return values.get(name);
    }

    public Set<String> getNames() {
        return values.keySet();
    }
}
