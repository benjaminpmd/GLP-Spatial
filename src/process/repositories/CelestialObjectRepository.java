package process.repositories;

import java.util.HashMap;
import java.util.Set;

/**
 * Class containing repository for celestials objects information.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.14
 */
public class CelestialObjectRepository {

    private static final CelestialObjectRepository instance = new CelestialObjectRepository();
    private final HashMap<String, String[]> values = new HashMap<>();

    private CelestialObjectRepository() {
    }

    /**
     * Method to get the only instance of this repository.
     *
     * @return {@link CelestialObjectRepository}
     */
    public static CelestialObjectRepository getInstance() {
        return instance;
    }

    /**
     * Register a value in the repository.
     *
     * @param name  {@link String} the name of the celestial object.
     * @param value {@link String}[] with the first value as the radius of the object, and the second one, the weight of the object.
     */
    public void register(String name, String[] value) {
        if (!values.containsKey(name)) {
            values.put(name, value);
        }
    }

    /**
     * Get the information about a specific celestial object.
     *
     * @param name {@link String} the name of the celestial object you want the information about.
     * @return {@link String}[] with the first value as the radius of the object, and the second one, the weight of the object.
     * @throws IllegalArgumentException if the name is not in the repository.
     */
    public String[] getValue(String name) throws IllegalArgumentException {
        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("The following name: " + name + " does not exist");
        }
        return values.get(name);
    }

    /**
     * Method to get all the names present in the repository.
     *
     * @return {@link Set} of String.
     */
    public Set<String> getKeys() {
        return values.keySet();
    }
}
