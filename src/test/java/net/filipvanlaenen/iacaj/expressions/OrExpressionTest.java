package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>OrExpression</code> class.
 */
public class OrExpressionTest {
    private void verify(String original, String expected) {
        assertEquals(Parser.parse(expected), Parser.parse(original).simplify());
    }

    /**
     * Verifies that <code>a ∨ a</code> is simplified into <code>a</code>.
     */
    @Test
    public void simplifyShouldReduceAOrAToA() {
        verify("a ∨ a", "a");
    }

    /**
     * Verifies that <code>a ∨ a ∨ b</code> is simplified into <code>a ∨ b</code>.
     */
    @Test
    public void simplifyShouldReduceAOrAOrBToAOrB() {
        verify("a ∨ a ∨ b", "a ∨ b");
    }

    /**
     * Verifies that <code>a ∨ ¬a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceAOrNotAToTrue() {
        verify("a ∨ ¬a", "true");
    }

    /**
     * Verifies that <code>a ∨ ¬a ∨ b</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceAOrNotAOrBToTrue() {
        verify("a ∨ ¬a ∨ b", "true");
    }

    /**
     * Verifies that <code>¬a ∨ a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceNotAOrAToTrue() {
        verify("¬a ∨ a", "true");
    }

    /**
     * Verifies that <code>¬a ∨ a ∨ b</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceNotAOrAOrBToTrue() {
        verify("¬a ∨ a ∨ b", "true");
    }

    /**
     * Verifies that <code>¬a ∨ ¬a</code> is simplified into <code>¬a</code>.
     */
    @Test
    public void simplifyShouldReduceNotAOrNotAToNotA() {
        verify("¬a ∨ ¬a", "¬a");
    }

    /**
     * Verifies that <code>¬a ∨ ¬a ∨ b</code> is simplified into <code>¬a ∨ b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAOrNotAOrBToNotAOrB() {
        verify("¬a ∨ ¬a ∨ b", "¬a ∨ b");
    }
}
