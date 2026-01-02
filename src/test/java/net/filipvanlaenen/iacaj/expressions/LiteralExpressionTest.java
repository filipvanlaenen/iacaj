package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>LiteralExpression</code> class.
 */
public class LiteralExpressionTest {
    private void verify(String original, String expected) {
        assertEquals(Parser.parse(expected), Parser.parse(original).simplify());
    }

    /**
     * Verifies that <code>true</code> is simplified into <code>true</code>.
     */
    @Test
    public void simplifyShouldReduceTrueToTrue() {
        verify("true", "true");
    }

    /**
     * Verifies that <code>false</code> is simplified into <code>false</code>.
     */
    @Test
    public void simplifyShouldReduceFalseToFalse() {
        verify("false", "false");
    }
}
