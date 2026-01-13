package net.filipvanlaenen.iacaj.expressions;

public enum Operator {
    AND("∧"), NOT("¬"), OR("∨"), XOR("⊻");

    private final String symbol;

    private Operator(final String symbol) {
        this.symbol = symbol;
    }

    String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
