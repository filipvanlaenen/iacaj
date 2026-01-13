package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>LiteralExpression</code> class.
 */
public class LiteralExpressionTest {
    /**
     * Verifies that <code>true</code> is simplified into <code>true</code>.
     */
    @Test
    public void simplifyShouldReduceTrueToTrue() {
        parseSimplifyAndAssertEquals("true", "true");
    }

    /**
     * Verifies that <code>false</code> is simplified into <code>false</code>.
     */
    @Test
    public void simplifyShouldReduceFalseToFalse() {
        parseSimplifyAndAssertEquals("false", "false");
    }

    /**
     * Verifies that <code>toString</code> returns <code>true</code> correctly.
     */
    @Test
    public void toStringShouldReturnTrueCorrectly() {
        assertEquals("true", LiteralExpression.TRUE.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>false</code> correctly.
     */
    @Test
    public void toStringShouldReturnFalseCorrectly() {
        assertEquals("false", LiteralExpression.FALSE.toString());
    }
}
