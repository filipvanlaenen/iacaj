package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>NegationExpression</code> class.
 */
public class NegationExpressionTest {
    private void verify(String original, String expected) {
        assertEquals(Parser.parse(expected), Parser.parse(original).simplify());
    }

    /**
     * Verifies that <code>¬a</code> is simplified into <code>¬a</code>.
     */
    @Test
    public void simplifyShouldReduceNotAToNotA() {
        verify("¬a", "¬a");
    }
}
