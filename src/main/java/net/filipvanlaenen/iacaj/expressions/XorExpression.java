package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;

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
        boolean newNegated = negated ^ (variables.size() - newVariables.size() % 2 == 1);
        if (newVariables.size() == 1) {
            if (newNegated) {
                return new NegationExpression(newVariables.get());
            } else {
                return new IdentityExpression(newVariables.get());
            }
        } else {
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] direct = newVariables.toArray(new Variable[0]);
            return new XorExpression(ValueCollection.of(direct), newNegated);
        }
    }
}
