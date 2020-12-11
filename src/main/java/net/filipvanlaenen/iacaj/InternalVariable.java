package net.filipvanlaenen.iacaj;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an internal variable.
 */
public class InternalVariable extends Variable {
    /**
     * A map with all the instances.
     */
    private static Map<String, InternalVariable> instances = new HashMap<String, InternalVariable>();

    /**
     * Returns the internal variable with the given name if it already exists, or
     * creates a new one otherwise.
     *
     * @param name Name of the internal variable.
     * @return The internal variable with that name, or a new instance.
     */
    public static InternalVariable get(final String name) {
        if (instances.containsKey(name)) {
            return instances.get(name);
        }
        InternalVariable newInstance = new InternalVariable(name);
        instances.put(name, newInstance);
        return newInstance;
    }

    /**
     * Constructor using the internal variable's name as parameter.
     *
     * @param name The name of the internal variable.
     */
    protected InternalVariable(final String name) {
        super(name);
    }
}
