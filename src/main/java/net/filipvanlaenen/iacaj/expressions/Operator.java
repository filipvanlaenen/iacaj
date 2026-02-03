package net.filipvanlaenen.iacaj.expressions;

/**
 * Enumeration with the operators.
 */
public enum Operator {
    /**
     * Logical and.
     */
    AND("∧"),
    /**
     * Logical not.
     */
    NOT("¬"),
    /**
     * Logical or.
     */
    OR("∨"),
    /**
     * Logical xor.
     */
    XOR("⊻");

    /**
     * The symbol for the operator.
     */
    private final String symbol;

    /**
     * Creates an operator with a symbol.
     *
     * @param symbol The symbol for the operator.
     */
    Operator(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol for the operator.
     *
     * @return The symbol for the operaror.
     */
    String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
