package net.filipvanlaenen.iacaj;

/**
 * Class representing an output parameter.
 */
public class OutputParameter {
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
