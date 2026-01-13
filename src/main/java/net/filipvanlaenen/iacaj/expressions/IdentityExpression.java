package net.filipvanlaenen.iacaj.expressions;

/**
 * An identity expression for a variable.
 *
 * @param variable The variable in the identity expression.
 */
public record IdentityExpression(Variable variable) implements Expression {
    @Override
    public Expression simplify() {
        return this;
    }
    
    @Override
    public String toString() {
        return variable.name();
    }
}
