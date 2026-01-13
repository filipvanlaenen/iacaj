package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Verifies that <code>toString</code> returns <code>a</code> correctly.
     */
    @Test
    public void toStringShouldReturnACorrectly() {
        assertEquals("a", new IdentityExpression(new Variable("a")).toString());
    }
}
