package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AndExpression</code> class.
 */
public class AndExpressionTest {
    private void verify(String original, String expected) {
        assertEquals(Parser.parse(expected), Parser.parse(original).simplify());
    }

    /**
     * Verifies that <code>a ∧ a</code> is simplified into <code>a</code>.
     */
    @Test
    public void simplifyShouldReduceAAndAToA() {
        verify("a ∧ a", "a");
    }

    /**
     * Verifies that <code>a ∧ a ∧ b</code> is simplified into <code>a ∧ b</code>.
     */
    @Test
    public void simplifyShouldReduceAAndAAndBToAAndB() {
        verify("a ∧ a ∧ b", "a ∧ b");
    }

    /**
     * Verifies that <code>a ∧ ¬a</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceAAndNotAToFalse() {
        verify("a ∧ ¬a", "false");
    }

    /**
     * Verifies that <code>a ∧ ¬a ∧ b</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceAAndNotAAndBToFalse() {
        verify("a ∧ ¬a ∧ b", "false");
    }

    /**
     * Verifies that <code>¬a ∧ a</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceNotAAndAToFalse() {
        verify("¬a ∧ a", "false");
    }

    /**
     * Verifies that <code>¬a ∧ a ∧ b</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceNotAAndAAndBToFalse() {
        verify("¬a ∧ a ∧ b", "false");
    }

    /**
     * Verifies that <code>¬a ∧ ¬a</code> is simplified into <code>¬a</code>.
     */
    @Test
    public void simplifyShouldReduceNotAAndNotAToNotA() {
        verify("¬a ∧ ¬a", "¬a");
    }

    /**
     * Verifies that <code>¬a ∧ ¬a ∧ b</code> is simplified into <code>¬a ∧ b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAAndNotAAndBToNotAAndB() {
        verify("¬a ∧ ¬a ∧ b", "¬a ∧ b");
    }
}
