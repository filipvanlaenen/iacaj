package net.filipvanlaenen.iacaj.expressions;

/**
 * An identity expression for a variable.
 */
public record IdentityExpression(Variable variable) implements Expression {
    @Override
    public Expression simplify() {
        return this;
    }
}
