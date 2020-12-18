package net.filipvanlaenen.iacaj;

/**
 * Class representing an output parameter.
 */
public final class OutputParameter extends Variable {
    /**
     * Constructor using the output parameter's name as parameter.
     *
     * @param name The name of the output parameter.
     */
    private OutputParameter(final String name) {
        super(name);
    }

    /**
     * Returns whether a variable name matches the pattern for an output parameter.
     *
     * @param variableName The variable name to check.
     * @return True if the variable name starts with an o, false otherwise.
     */
    public static boolean isOutputParameter(final String variableName) {
        return variableName.startsWith("o");
    }
}
