package net.filipvanlaenen.iacaj;

/**
 * Enumeration representing the operators.
 */
@Deprecated
public enum BooleanOperator {
    /**
     * The logical AND operator.
     */
    And("∧", "&") {
        @Override
        BooleanRightHandSide createBooleanCalculation(final String rightHandSideString) {
            return new BooleanAndCalculation(rightHandSideString);
        }
    },
    /**
     * The logical OR operator.
     */
    Or("∨", "|") {
        @Override
        BooleanRightHandSide createBooleanCalculation(final String rightHandSideString) {
            return new BooleanOrCalculation(rightHandSideString);
        }
    },
    /**
     * The logical XOR operator.
     */
    Xor("⊻", "^") {
        @Override
        BooleanRightHandSide createBooleanCalculation(final String rightHandSideString) {
            return new BooleanXorCalculation(rightHandSideString);
        }
    };

    /**
     * The symbol.
     */
    private final String symbol;
    /**
     * The symbol for Java.
     */
    private final String javaSymbol;

    /**
     * Constructor using the symbol as the parameter.
     *
     * @param symbol     The symbol for the operator.
     * @param javaSymbol The symbol for the operator in Java.
     */
    BooleanOperator(final String symbol, final String javaSymbol) {
        this.symbol = symbol;
        this.javaSymbol = javaSymbol;
    }

    /**
     * Creates a right hand side object based from a string.
     *
     * @param rightHandSideString The string representing a right hand side expression.
     * @return A right hand side object parsed from the string.
     */
    abstract BooleanRightHandSide createBooleanCalculation(String rightHandSideString);

    /**
     * Returns the symbol of the operartor for Java.
     *
     * @return The symbol of the operator for Java.
     */
    public String getJavaSymbol() {
        return javaSymbol;
    }

    /**
     * Returns the symbol of the operartor.
     *
     * @return The symbol of the operator.
     */
    public String getSymbol() {
        return symbol;
    }
}
