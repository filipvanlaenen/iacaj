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
     * The number of the variable.
     */
    private final int number;

    /**
     * Constructor with the variable's name as parameter.
     *
     * @param name The name of the variable.
     */
    protected Variable(final String name) {
        this.name = name;
        this.number = Integer.parseInt(name.substring(1));
    }

    /**
     * Returns the name of the variable.
     *
     * @return The name of the variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of the variable.
     *
     * @return The number of the variable.
     */
    public int getNumber() {
        return number;
    }
}
