package net.filipvanlaenen.iacaj.expressions;

/**
 * The interface defining what an expression is.
 */
public interface Expression {
    /**
     * Tries to simplify the expression and returns the result.
     *
     * @return An expression, if possible simplified.
     */
    Expression simplify();
}
