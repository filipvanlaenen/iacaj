package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * An xor expression.
 *
 * @param variables The variables occurring in the xor expression.
 * @param negated   True if the xor expression is negated.
 */
public record XorExpression(ValueCollection<Variable> variables, boolean negated) implements CompositeExpression {
}
