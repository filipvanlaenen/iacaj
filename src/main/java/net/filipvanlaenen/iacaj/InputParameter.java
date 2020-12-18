package net.filipvanlaenen.iacaj;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an input parameter.
 */
public final class InputParameter extends Variable {
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
        InputParameter newInstance = new InputParameter(name);
        instances.put(name, newInstance);
        return newInstance;
    }

    /**
     * Constructor using the input parameter's name as parameter.
     *
     * @param name The name of the input parameter.
     */
    private InputParameter(final String name) {
        super(name);
    }

    /**
     * Returns whether a variable name matches the pattern for an input parameter.
     *
     * @param variableName The variable name to check.
     * @return True if the variable name starts with an i, false otherwise.
     */
    public static boolean isInputParameter(final String variableName) {
        return variableName.startsWith("i");
    }
}
