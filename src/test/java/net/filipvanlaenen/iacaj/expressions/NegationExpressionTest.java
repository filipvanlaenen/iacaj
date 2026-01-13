package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Verifies that <code>toString</code> returns <code>¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnNotACorrectly() {
        assertEquals("¬a", new NegationExpression(new Variable("a")).toString());
    }
}
