package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;

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
}
