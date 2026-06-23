package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.kolektoj.Collection.ElementCardinality.DISTINCT_ELEMENTS;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.kolektoj.collectors.Collectors;

/**
 * A function using <code>or</code>.
 *
 * @param directVariables  The variables occurring directly in the or function.
 * @param negatedVariables The variables occurring negated in the or function.
 */
public record OrFunction(ValueCollection<Variable> directVariables, ValueCollection<Variable> negatedVariables)
        implements Function {
    /**
     * The or operator with spaces.
     */
    private static final String OR_WITH_SPACES = " " + Operator.OR + " ";
    /**
     * The negated or operator with spaces.
     */
    private static final String OR_NOT_WITH_SPACES = OR_WITH_SPACES + Operator.NOT;

    /**
     * Creates a new expression based on the provided new direct and negated variables. If only one variable is left, an
     * identity or negation expression is created instead of a new or expression.
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
            return new OrFunction(ValueCollection.of(newDirectVariables), ValueCollection.of(newNegatedVariables));
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
            return LiteralExpression.TRUE;
        }
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        ModifiableCollection<Variable> newDirectVariables = ModifiableCollection.empty();
        ModifiableCollection<Variable> newNegatedVariables = ModifiableCollection.empty();
        for (Variable directVariable : directVariables) {
            if (variableToExpressionMap.containsKey(directVariable)) {
                Expression expression = variableToExpressionMap.get(directVariable);
                if (LiteralExpression.TRUE == expression) {
                    return LiteralExpression.TRUE;
                } else if (expression instanceof IdentityExpression identityExpression) {
                    newDirectVariables.add(identityExpression.variable());
                } else if (expression instanceof NegationExpression negationExpression) {
                    newNegatedVariables.add(negationExpression.variable());
                } else if (expression instanceof OrFunction orFunction) {
                    newDirectVariables.addAll(orFunction.directVariables());
                    newNegatedVariables.addAll(orFunction.negatedVariables());
                } else if (LiteralExpression.FALSE != expression) {
                    newDirectVariables.add(directVariable);
                }
            } else {
                newDirectVariables.add(directVariable);
            }
        }
        for (Variable negatedVariable : negatedVariables) {
            if (variableToExpressionMap.containsKey(negatedVariable)) {
                Expression expression = variableToExpressionMap.get(negatedVariable);
                if (LiteralExpression.FALSE == expression) {
                    return LiteralExpression.TRUE;
                } else if (expression instanceof IdentityExpression identityExpression) {
                    newNegatedVariables.add(identityExpression.variable());
                } else if (expression instanceof NegationExpression negationExpression) {
                    newDirectVariables.add(negationExpression.variable());
                } else if (expression instanceof AndFunction andFunction) {
                    newDirectVariables.addAll(andFunction.negatedVariables());
                    newNegatedVariables.addAll(andFunction.directVariables());
                } else if (LiteralExpression.TRUE != expression) {
                    newNegatedVariables.add(negatedVariable);
                }
            } else {
                newNegatedVariables.add(negatedVariable);
            }
        }
        if (newDirectVariables.size() + newNegatedVariables.size() == 0) {
            return LiteralExpression.FALSE;
        }
        return createExpression(newDirectVariables, newNegatedVariables);
    }

    @Override
    public String toString() {
        String dv = String.join(OR_WITH_SPACES,
                directVariables.stream().map(Variable::name).collect(Collectors.toCollection()));
        String nv = String.join(OR_NOT_WITH_SPACES,
                negatedVariables.stream().map(Variable::name).collect(Collectors.toCollection()));
        if (dv.isEmpty()) {
            return Operator.NOT + nv;
        } else if (nv.isEmpty()) {
            return dv;
        } else {
            return dv + OR_NOT_WITH_SPACES + nv;
        }
    }
}
