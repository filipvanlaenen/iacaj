package net.filipvanlaenen.iacaj;

/**
 * Class representing an operand in a Boolean calculation.
 */
public final class BooleanOperand {
    /**
     * Whether or not the operand is negated in the calculation.
     */
    private final boolean negated;
    /**
     * The name of the operand.
     */
    private final String name;
    /**
     * The number of the operand, extracted from the name.
     */
    private final int number;

    /**
     * Constructor taking a string representing the operand as its parameter.
     *
     * @param operandString The string representing the operand.
     */
    BooleanOperand(final String operandString) {
        negated = operandString.startsWith("¬");
        if (negated) {
            name = operandString.substring(1);
        } else {
            name = operandString;
        }
        number = extractNumberFromName();
    }

    /**
     * Constructor taking the name and whether the operand is negated as its
     * parameters.
     *
     * @param name    The name of the operand.
     * @param negated Whether the operand is negated.
     */
    BooleanOperand(final String name, final boolean negated) {
        this.name = name;
        this.negated = negated;
        number = extractNumberFromName();
    }

    /**
     * Extracts the number from the name.
     *
     * @return Extracts and returns the number from the name.
     */
    private int extractNumberFromName() {
        return Integer.parseInt(name.substring(1));
    }

    /**
     * Returns the name of the operand.
     *
     * @return The name of the operand.
     */
    String getName() {
        return name;
    }

    /**
     * Returns the number of the operand, useful for sorting.
     *
     * @return The number of the operand.
     */
    int getNumber() {
        return number;
    }

    /**
     * Returns whether the operand is an input parameter or not.
     *
     * @return True if the operand is an input parameter, false otherwise.
     */
    boolean isInputParameter() {
        return InputParameter.isInputParameter(name);
    }

    /**
     * Returns whether the operand is negated.
     *
     * @return True if the operand is negated.
     */
    boolean isNegated() {
        return negated;
    }

    /**
     * Returns a negated version of the operand.
     *
     * @return A negated version.
     */
    BooleanOperand negated() {
        return new BooleanOperand(name, !negated);
    }

    /**
     * Returns a representation of the operand as a Java string.
     *
     * @return The operand as a Java string.
     */
    String toJavaString() {
        return (negated ? "!" : "") + name;
    }

    @Override
    public String toString() {
        return (negated ? "¬" : "") + name;
    }
}
