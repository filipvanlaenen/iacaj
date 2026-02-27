package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;

/**
 * The interface defining what an expression is.
 */
public interface Expression {
    Collection<Variable> getVariables();

    /**
     * Tries to simplify the expression and returns the result.
     *
     * @return An expression, if possible simplified.
     */
    Expression simplify();

    Expression simplify(Map<Variable, Expression> variableToExpressionMap);
}
