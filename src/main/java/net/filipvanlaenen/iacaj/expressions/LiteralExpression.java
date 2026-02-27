package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;

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
    public Collection<Variable> getVariables() {
        return Collection.empty();
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        return this;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
