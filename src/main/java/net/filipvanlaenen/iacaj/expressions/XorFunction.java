package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;
import net.filipvanlaenen.kolektoj.Map.Entry;
import net.filipvanlaenen.kolektoj.collectors.Collectors;
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
        if (newVariables.isEmpty()) {
            return negated ? LiteralExpression.TRUE : LiteralExpression.FALSE;
        } else if (newVariables.size() == 1) {
            Variable v = newVariables.get();
            return negated ? new NegationExpression(v) : new IdentityExpression(v);
        } else {
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] direct = newVariables.toArray(new Variable[0]);
            return new XorFunction(ValueCollection.of(direct), negated);
        }
    }

    @Override
    public String toString() {
        return (negated ? "¬" : "")
                + String.join(" ⊻ ", variables.stream().map(Variable::name).collect(Collectors.toCollection()));
    }
}
