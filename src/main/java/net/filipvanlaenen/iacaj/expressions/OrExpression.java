package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;

/**
 * An or expression.
 *
 * @param directVariables  The variables occurring directly in the or expression.
 * @param negatedVariables The variables occurring negated in the or expression.
 */
public record OrExpression(ValueCollection<Variable> directVariables, ValueCollection<Variable> negatedVariables)
        implements CompositeExpression {
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
                return new OrExpression(ValueCollection.of(direct), ValueCollection.of(negated));
            }
        } else {
            return LiteralExpression.TRUE;
        }
    }
}
