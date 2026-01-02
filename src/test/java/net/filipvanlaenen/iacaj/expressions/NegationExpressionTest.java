package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>NegationExpression</code> class.
 */
public class NegationExpressionTest {
    /**
     * Verifies that <code>¬a</code> is simplified into <code>¬a</code>.
     */
    @Test
    public void simplifyShouldReduceNotAToNotA() {
        parseSimplifyAndAssertEquals("¬a", "¬a");
    }
}
