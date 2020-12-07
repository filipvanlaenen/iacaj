package net.filipvanlaenen.iacaj;

public class Variable {
    private final String name;

    protected Variable(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the variable.
     */
    public String getName() {
        return name;
    }
}
