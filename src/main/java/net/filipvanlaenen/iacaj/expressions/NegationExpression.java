package net.filipvanlaenen.iacaj.expressions;

/**
 * An expression negating a variable.
 */
public record NegationExpression(Variable variable) implements Expression {
    @Override
    public Expression simplify() {
        return this;
    }
}
