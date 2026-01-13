package net.filipvanlaenen.iacaj.expressions;

/**
 * An expression negating a variable.
 *
 * @param variable The variable in the negation expression.
 */
public record NegationExpression(Variable variable) implements Expression {
    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return Operator.NOT + variable.name();
    }
}
