package net.filipvanlaenen.iacaj.expressions;

/**
 * A literal expression, i.e. true or false.
 */
public enum LiteralExpression implements Expression {
    /**
     * False.
     */
    FALSE,
    /**
     * True.
     */
    TRUE;

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
