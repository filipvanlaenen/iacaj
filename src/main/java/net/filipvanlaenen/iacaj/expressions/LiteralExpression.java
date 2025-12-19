package net.filipvanlaenen.iacaj.expressions;

/**
 * A literal expression, i.e. true or false.
 */
public enum LiteralExpression implements Expression {
    FALSE, TRUE;

    @Override
    public Expression simplify() {
        return this;
    }
}
