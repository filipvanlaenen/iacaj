package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * An or expression.
 *
 * @param directVariables  The variables occurring directly in the or expression.
 * @param negatedVariables The variables occurring negated in the or expression.
 */
public record OrExpression(ValueCollection<Variable> directVariables, ValueCollection<Variable> negatedVariables)
        implements CompositeExpression {
}
