package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;

/**
 * An expression negating a variable.
 *
 * @param variable The variable in the negation expression.
 */
public record NegationExpression(Variable variable) implements Expression {
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
            Expression expression = variableToExpressionMap.get(variable);
            if (LiteralExpression.TRUE == expression) {
                return LiteralExpression.FALSE;
            } else if (LiteralExpression.FALSE == expression) {
                return LiteralExpression.TRUE;
            } else if (expression instanceof IdentityExpression) {
                return new NegationExpression(((IdentityExpression) expression).variable())
                        .simplify(variableToExpressionMap);
            } else if (expression instanceof NegationExpression) {
                return new IdentityExpression(((NegationExpression) expression).variable())
                        .simplify(variableToExpressionMap);
            } else if (expression instanceof AndFunction) {
                AndFunction andFunction = (AndFunction) expression;
                return new OrFunction(andFunction.negatedVariables(), andFunction.directVariables())
                        .simplify(variableToExpressionMap);
            } else if (expression instanceof OrFunction) {
                OrFunction orFunction = (OrFunction) expression;
                return new AndFunction(orFunction.negatedVariables(), orFunction.directVariables())
                        .simplify(variableToExpressionMap);
            } else if (expression instanceof XorFunction) {
                XorFunction xorFunction = (XorFunction) expression;
                return new XorFunction(xorFunction.variables(), !xorFunction.negated())
                        .simplify(variableToExpressionMap);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return Operator.NOT + variable.name();
    }
}
