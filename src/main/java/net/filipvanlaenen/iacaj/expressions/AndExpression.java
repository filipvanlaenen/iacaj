package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * An and expression.
 *
 * @param directVariables  The variables occurring directly in the and expression.
 * @param negatedVariables The variables occurring negated in the and expression.
 */
public record AndExpression(ValueCollection<Variable> directVariables, ValueCollection<Variable> negatedVariables)
        implements CompositeExpression {
}
