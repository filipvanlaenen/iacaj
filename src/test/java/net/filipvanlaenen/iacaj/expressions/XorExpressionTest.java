package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>XorExpression</code> class.
 */
public class XorExpressionTest {
    private void verify(String original, String expected) {
        assertEquals(Parser.parse(expected), Parser.parse(original).simplify());
    }

    /**
     * Verifies that <code>a ⊻ a</code> is simplified into <code>false</code>.
     */
    @Test
    public void simplifyShouldReduceAXorAToFalse() {
        verify("a ⊻ a", "false");
    }

    /**
     * Verifies that <code>a ⊻ a ⊻ b</code> is simplified into <code>¬b</code>.
     */
    @Test
    public void simplifyShouldReduceAXorAXorBToNotB() {
        verify("a ⊻ a ⊻ b", "b");
    }
    
    /**
     * Verifies that <code>a ⊻ a ⊻ a ⊻ b</code> is simplified into <code>¬b</code>.
     */
    @Test
    public void simplifyShouldReduceAXorAXorAXorBToNotB() {
        verify("a ⊻ a ⊻ a ⊻ b", "a ⊻ b");
    }

    /**
     * Verifies that <code>a ⊻ ¬a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceAXorNotAToTrue() {
        verify("a ⊻ ¬a", "true");
    }

    /**
     * Verifies that <code>a ⊻ ¬a ⊻ b</code> is simplified into <code>b</code>.
     */
    @Test
    public void simplifyShouldReduceAXorNotAXorBToB() {
        verify("a ⊻ ¬a ⊻ b", "¬b");
    }

    /**
     * Verifies that <code>¬a ⊻ a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceNotAXorAToTrue() {
        verify("¬a ⊻ a", "true");
    }

    /**
     * Verifies that <code>¬a ⊻ a ⊻ b</code> is simplified into <code>b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAXorAXorBToB() {
        verify("¬a ⊻ a ⊻ b", "¬b");
    }

    /**
     * Verifies that <code>¬a ⊻ ¬a</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceNotAXorNotAToFalse() {
        verify("¬a ⊻ ¬a", "false");
    }

    /**
     * Verifies that <code>¬a ⊻ ¬a ⊻ b</code> is simplified into <code>¬b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAXorNotAXorBToNotB() {
        verify("¬a ⊻ ¬a ⊻ b", "b");
    }
}
