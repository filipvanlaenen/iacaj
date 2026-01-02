package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;
import net.filipvanlaenen.kolektoj.Map.Entry;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.nombrajkolektoj.integers.UpdatableIntegerMap;

/**
 * An xor expression.
 *
 * @param variables The variables occurring in the xor expression.
 * @param negated   True if the xor expression is negated.
 */
public record XorExpression(ValueCollection<Variable> variables, boolean negated) implements CompositeExpression {
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
            return new XorExpression(ValueCollection.of(direct), negated);
        }
    }
}
