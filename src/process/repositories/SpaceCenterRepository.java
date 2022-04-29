package process.repositories;

import java.util.HashMap;
import java.util.Set;

/**
 * Class containing repository for space centers information.
 *
 * @author Benjamin P
 * @version 22.04.28 (1.0.0)
 * @since 22.02.14
 */
public class SpaceCenterRepository {

    private static final SpaceCenterRepository instance = new SpaceCenterRepository();
    private final HashMap<String, Integer> values = new HashMap<>();

    private SpaceCenterRepository() {
    }

    /**
     * Method to get the only instance of this repository.
     *
     * @return {@link SpaceCenterRepository}
     */
    public static SpaceCenterRepository getInstance() {
        return instance;
    }

    /**
     * Register a value in the repository.
     *
     * @param name  {@link String} the name of the space center.
     * @param value {@link Integer} the degree angle from -180 to 180 where the space center is located.
     */
    public void register(String name, int value) {
        if (!values.containsKey(name)) {
            values.put(name, value);
        }
    }

    /**
     * Get the information about a specific space center.
     *
     * @param name The name of the space center you want the information about.
     * @return The degree angle from -180 to 180 where the space center is located.
     * @throws IllegalArgumentException if the name is not in the repository.
     */
    public int getValue(String name) throws IllegalArgumentException {
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
