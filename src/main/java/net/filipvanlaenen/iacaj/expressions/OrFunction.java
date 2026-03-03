package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;
import net.filipvanlaenen.kolektoj.collectors.Collectors;

/**
 * A function using <code>or</code>.
 *
 * @param directVariables  The variables occurring directly in the or function.
 * @param negatedVariables The variables occurring negated in the or function.
 */
public record OrFunction(ValueCollection<Variable> directVariables, ValueCollection<Variable> negatedVariables)
        implements Function {
    private static final String OR_WITH_SPACES = " " + Operator.OR + " ";
    private static final String OR_NOT_WITH_SPACES = OR_WITH_SPACES + Operator.NOT;

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
            return LiteralExpression.TRUE;
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
            return new OrFunction(ValueCollection.of(direct), ValueCollection.of(negated));
        }
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        ModifiableCollection<Variable> newDirectVariables = ModifiableCollection.empty();
        for (Variable directVariable : directVariables) {
            if (variableToExpressionMap.containsKey(directVariable)) {
                Expression expression = variableToExpressionMap.get(directVariable);
                if (LiteralExpression.TRUE == expression) {
                    return LiteralExpression.TRUE;
                } else if (LiteralExpression.FALSE != expression) {
                    newDirectVariables.add(directVariable);
                }
            } else {
                newDirectVariables.add(directVariable);
            }
        }
        ModifiableCollection<Variable> newNegatedVariables = ModifiableCollection.empty();
        for (Variable negatedVariable : negatedVariables) {
            if (variableToExpressionMap.containsKey(negatedVariable)) {
                Expression expression = variableToExpressionMap.get(negatedVariable);
                if (LiteralExpression.FALSE == expression) {
                    return LiteralExpression.TRUE;
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
