package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AndExpression</code> class.
 */
public class AndExpressionTest {
    /**
     * Verifies that <code>a ∧ a</code> is simplified into <code>a</code>.
     */
    @Test
    public void simplifyShouldReduceAAndAToA() {
        parseSimplifyAndAssertEquals("a ∧ a", "a");
    }

    /**
     * Verifies that <code>a ∧ a ∧ b</code> is simplified into <code>a ∧ b</code>.
     */
    @Test
    public void simplifyShouldReduceAAndAAndBToAAndB() {
        parseSimplifyAndAssertEquals("a ∧ a ∧ b", "a ∧ b");
    }

    /**
     * Verifies that <code>a ∧ ¬a</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceAAndNotAToFalse() {
        parseSimplifyAndAssertEquals("a ∧ ¬a", "false");
    }

    /**
     * Verifies that <code>a ∧ ¬a ∧ b</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceAAndNotAAndBToFalse() {
        parseSimplifyAndAssertEquals("a ∧ ¬a ∧ b", "false");
    }

    /**
     * Verifies that <code>¬a ∧ a</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceNotAAndAToFalse() {
        parseSimplifyAndAssertEquals("¬a ∧ a", "false");
    }

    /**
     * Verifies that <code>¬a ∧ a ∧ b</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceNotAAndAAndBToFalse() {
        parseSimplifyAndAssertEquals("¬a ∧ a ∧ b", "false");
    }

    /**
     * Verifies that <code>¬a ∧ ¬a</code> is simplified into <code>¬a</code>.
     */
    @Test
    public void simplifyShouldReduceNotAAndNotAToNotA() {
        parseSimplifyAndAssertEquals("¬a ∧ ¬a", "¬a");
    }

    /**
     * Verifies that <code>¬a ∧ ¬a ∧ b</code> is simplified into <code>¬a ∧ b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAAndNotAAndBToNotAAndB() {
        parseSimplifyAndAssertEquals("¬a ∧ ¬a ∧ b", "¬a ∧ b");
    }
}
