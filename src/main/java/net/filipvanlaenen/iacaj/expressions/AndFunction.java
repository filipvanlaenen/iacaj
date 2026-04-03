package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.kolektoj.Collection.ElementCardinality.DISTINCT_ELEMENTS;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.kolektoj.collectors.Collectors;

/**
 * A function using <code>and</code>.
 *
 * @param directVariables  The variables occurring directly in the and function.
 * @param negatedVariables The variables occurring negated in the and function.
 */
public record AndFunction(ValueCollection<Variable> directVariables, ValueCollection<Variable> negatedVariables)
        implements Function {
    /**
     * The and operator with spaces.
     */
    private static final String AND_WITH_SPACES = " " + Operator.AND + " ";
    /**
     * The negated and operator with spaces.
     */
    private static final String AND_NOT_WITH_SPACES = AND_WITH_SPACES + Operator.NOT;

    /**
     * Creates a new expression based on the provided new direct and negated variables. If only one variable is left, an
     * identity or negation expression is created instead of a new and expression.
     *
     * @param newDirectVariables  The new direct variables.
     * @param newNegatedVariables The new negated variables.
     * @return A new expression based on the provided new direct and negated variables.
     */
    private Expression createExpression(final Collection<Variable> newDirectVariables,
            final Collection<Variable> newNegatedVariables) {
        if (newDirectVariables.size() + newNegatedVariables.size() == 1) {
            if (newDirectVariables.isEmpty()) {
                return new NegationExpression(newNegatedVariables.get());
            } else {
                return new IdentityExpression(newDirectVariables.get());
            }
        } else {
            return new AndFunction(ValueCollection.of(newDirectVariables), ValueCollection.of(newNegatedVariables));
        }
    }

    @Override
    public Collection<Variable> getVariables() {
        return Collection.unionOf(directVariables, negatedVariables);
    }

    @Override
    public Expression simplify() {
        Collection<Variable> distinctDirectVariables = Collection.of(DISTINCT_ELEMENTS, directVariables);
        Collection<Variable> distinctNegatedVariables = Collection.of(DISTINCT_ELEMENTS, negatedVariables);
        Collection<Variable> commonVariables =
                Collection.intersectionOf(distinctDirectVariables, distinctNegatedVariables);
        if (commonVariables.isEmpty()) {
            return createExpression(distinctDirectVariables, distinctNegatedVariables);
        } else {
            return LiteralExpression.FALSE;
        }
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        ModifiableCollection<Variable> newDirectVariables = ModifiableCollection.empty();
        ModifiableCollection<Variable> newNegatedVariables = ModifiableCollection.empty();
        for (Variable directVariable : directVariables) {
            if (variableToExpressionMap.containsKey(directVariable)) {
                Expression expression = variableToExpressionMap.get(directVariable);
                if (LiteralExpression.FALSE == expression) {
                    return LiteralExpression.FALSE;
                } else if (expression instanceof IdentityExpression identityExpression) {
                    newDirectVariables.add(identityExpression.variable());
                } else if (expression instanceof NegationExpression negationExpression) {
                    newNegatedVariables.add(negationExpression.variable());
                } else if (LiteralExpression.TRUE != expression) {
                    newDirectVariables.add(directVariable);
                }
            } else {
                newDirectVariables.add(directVariable);
            }
        }
        for (Variable negatedVariable : negatedVariables) {
            if (variableToExpressionMap.containsKey(negatedVariable)) {
                Expression expression = variableToExpressionMap.get(negatedVariable);
                if (LiteralExpression.TRUE == expression) {
                    return LiteralExpression.FALSE;
                } else if (expression instanceof IdentityExpression identityExpression) {
                    newNegatedVariables.add(identityExpression.variable());
                } else if (expression instanceof NegationExpression negationExpression) {
                    newDirectVariables.add(negationExpression.variable());
                } else if (LiteralExpression.FALSE != expression) {
                    newNegatedVariables.add(negatedVariable);
                }
            } else {
                newNegatedVariables.add(negatedVariable);
            }
        }
        if (newDirectVariables.size() + newNegatedVariables.size() == 0) {
            return LiteralExpression.TRUE;
        }
        return createExpression(newDirectVariables, newNegatedVariables);
    }

    @Override
    public String toString() {
        String dv = String.join(AND_WITH_SPACES,
                directVariables.stream().map(Variable::name).collect(Collectors.toCollection()));
        String nv = String.join(AND_NOT_WITH_SPACES,
                negatedVariables.stream().map(Variable::name).collect(Collectors.toCollection()));
        if (dv.isEmpty()) {
            return Operator.NOT + nv;
        } else if (nv.isEmpty()) {
            return dv;
        } else {
            return dv + AND_NOT_WITH_SPACES + nv;
        }
    }
}
