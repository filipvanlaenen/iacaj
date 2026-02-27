package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;

/**
 * An identity expression for a variable.
 *
 * @param variable The variable in the identity expression.
 */
public record IdentityExpression(Variable variable) implements Expression {
    @Override
    public Collection<Variable> getVariables() {
        return Collection.of(variable);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        if (variableToExpressionMap.containsKey(variable)) {
            return variableToExpressionMap.get(variable);
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return variable.name();
    }
}
