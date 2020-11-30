package net.filipvanlaenen.iacaj;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an input parameter.
 */
public class InputParameter {
    /**
     * A map with all the instances.
     */
    private static Map<String, InputParameter> instances = new HashMap<String, InputParameter>();

    /**
     * Returns the input parameter with the given name if it already exists, or
     * creates a new one otherwise.
     *
     * @param name Name of the input parameter.
     * @return The input parameter with that name, or a new instance.
     */
    public static InputParameter get(final String name) {
        if (instances.containsKey(name)) {
            return instances.get(name);
        }
        InputParameter newInstance = new InputParameter();
        instances.put(name, newInstance);
        return newInstance;
    }
}
