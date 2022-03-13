package process.repositories;

import java.util.HashMap;
import java.util.Set;

/**
 * Class containing repository for space centers information.
 *
 * @author Benjamin P
 * @version 22.03.13 (1.0.0)
 * @since 14.02.22
 */
public class SpaceCenterRepository {

    private static SpaceCenterRepository instance = new SpaceCenterRepository();
    private HashMap<String, Integer> values = new HashMap<>();

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
     * @param name {@link String} the name of the space center you want the information about.
     * @return {@link Integer} the degree angle from -180 to 180 where the space center is located.
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
