package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;
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
    private static final String AND_WITH_SPACES = " " + Operator.AND + " ";
    private static final String AND_NOT_WITH_SPACES = AND_WITH_SPACES + Operator.NOT;

    @Override
    public Collection<Variable> getVariables() {
        // TODO
        ModifiableCollection<Variable> variables = ModifiableCollection.of(directVariables);
        variables.addAll(negatedVariables);
        return Collection.of(variables);
    }

    @Override
    public Expression simplify() {
        // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/109
        ModifiableCollection<Variable> newDirectVariables =
                ModifiableCollection.of(ElementCardinality.DISTINCT_ELEMENTS);
        newDirectVariables.addAll(directVariables);
        ModifiableCollection<Variable> newNegatedVariables =
                ModifiableCollection.of(ElementCardinality.DISTINCT_ELEMENTS);
        newNegatedVariables.addAll(negatedVariables);
        // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/110
        ModifiableCollection<Variable> commonVariables = ModifiableCollection.of(newDirectVariables);
        commonVariables.retainAll(newNegatedVariables);
        if (commonVariables.isEmpty()) {
            return createExpression(newDirectVariables, newNegatedVariables);
        } else {
            return LiteralExpression.FALSE;
        }
    }

    private Expression createExpression(ModifiableCollection<Variable> newDirectVariables,
            ModifiableCollection<Variable> newNegatedVariables) {
        if (newDirectVariables.size() + newNegatedVariables.size() == 1) {
            if (newDirectVariables.isEmpty()) {
                return new NegationExpression(newNegatedVariables.get());
            } else {
                return new IdentityExpression(newDirectVariables.get());
            }
        } else {
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] direct = newDirectVariables.toArray(new Variable[0]);
            Variable[] negated = newNegatedVariables.toArray(new Variable[0]);
            return new AndFunction(ValueCollection.of(direct), ValueCollection.of(negated));
        }
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        ModifiableCollection<Variable> newDirectVariables = ModifiableCollection.empty();
        for (Variable directVariable : directVariables) {
            if (variableToExpressionMap.containsKey(directVariable)) {
                Expression expression = variableToExpressionMap.get(directVariable);
                if (LiteralExpression.FALSE == expression) {
                    return LiteralExpression.FALSE;
                } else if (LiteralExpression.TRUE != expression) {
                    newDirectVariables.add(directVariable);
                }
            }
        }
        ModifiableCollection<Variable> newNegatedVariables = ModifiableCollection.empty();
        for (Variable negatedVariable : negatedVariables) {
            if (variableToExpressionMap.containsKey(negatedVariable)) {
                Expression expression = variableToExpressionMap.get(negatedVariable);
                if (LiteralExpression.TRUE == expression) {
                    return LiteralExpression.FALSE;
                } else if (LiteralExpression.FALSE != expression) {
                    newNegatedVariables.add(negatedVariable);
                }
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
