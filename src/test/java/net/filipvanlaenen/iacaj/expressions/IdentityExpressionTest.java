package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>IdentityExpression</code> class.
 */
public class IdentityExpressionTest {
    /**
     * Verifies that <code>a</code> is simplified into <code>a</code>.
     */
    @Test
    public void simplifyShouldReduceAToA() {
        parseSimplifyAndAssertEquals("a", "a");
    }
}
