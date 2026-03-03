package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;
import net.filipvanlaenen.kolektoj.Map.Entry;
import net.filipvanlaenen.kolektoj.collectors.Collectors;
import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.nombrajkolektoj.integers.UpdatableIntegerMap;

/**
 * A function using <code>xor</code>.
 *
 * @param variables The variables occurring in the xor function.
 * @param negated   True if the xor function is negated.
 */
public record XorFunction(ValueCollection<Variable> variables, boolean negated) implements Function {
    private static final String XOR_WITH_SPACES = " " + Operator.XOR + " ";

    @Override
    public Collection<Variable> getVariables() {
        return variables;
    }

    @Override
    public Expression simplify() {
        // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/109
        ModifiableCollection<Variable> newVariables = ModifiableCollection.of(ElementCardinality.DISTINCT_ELEMENTS);
        newVariables.addAll(variables);
        UpdatableIntegerMap<Variable> numberOfOccurrences =
                UpdatableIntegerMap.of(0, newVariables.toArray(new Variable[0]));
        for (Variable v : variables) {
            numberOfOccurrences.augment(v, 1);
        }
        for (Entry<Variable, Integer> o : numberOfOccurrences) {
            if (o.value() % 2 == 0) {
                newVariables.remove(o.key());
            }
        }
        return createExpression(newVariables, negated);
    }

    private Expression createExpression(ModifiableCollection<Variable> newVariables, boolean newNegated) {
        if (newVariables.isEmpty()) {
            return newNegated ? LiteralExpression.TRUE : LiteralExpression.FALSE;
        } else if (newVariables.size() == 1) {
            Variable v = newVariables.get();
            return newNegated ? new NegationExpression(v) : new IdentityExpression(v);
        } else {
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] direct = newVariables.toArray(new Variable[0]);
            return new XorFunction(ValueCollection.of(direct), newNegated);
        }
    }

    @Override
    public Expression simplify(final Map<Variable, Expression> variableToExpressionMap) {
        ModifiableCollection<Variable> newVariables = ModifiableCollection.empty();
        boolean newNegated = negated;
        for (Variable variable : variables) {
            if (variableToExpressionMap.containsKey(variable)) {
                Expression expression = variableToExpressionMap.get(variable);
                if (LiteralExpression.TRUE == expression) {
                    newNegated = !newNegated;
                } else if (LiteralExpression.FALSE != expression) {
                    newVariables.add(variable);
                }
            } else {
                newVariables.add(variable);
            }
        }
        return createExpression(newVariables, newNegated);
    }

    @Override
    public String toString() {
        return (negated ? Operator.NOT : "") + String.join(XOR_WITH_SPACES,
                variables.stream().map(Variable::name).collect(Collectors.toCollection()));
    }
}
