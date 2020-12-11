package net.filipvanlaenen.iacaj;

/**
 * Class representing a variable.
 */
public class Variable {
    /**
     * The name of the variable.
     */
    private final String name;

    /**
     * Constructor with the variable's name as parameter.
     *
     * @param name The name of the variable.
     */
    protected Variable(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the variable.
     *
     * @return The name of the variable.
     */
    public String getName() {
        return name;
    }
}
