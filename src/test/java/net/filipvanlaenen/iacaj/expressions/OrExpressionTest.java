package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>OrExpression</code> class.
 */
public class OrExpressionTest {
    /**
     * Verifies that <code>a ∨ a</code> is simplified into <code>a</code>.
     */
    @Test
    public void simplifyShouldReduceAOrAToA() {
        parseSimplifyAndAssertEquals("a ∨ a", "a");
    }

    /**
     * Verifies that <code>a ∨ a ∨ b</code> is simplified into <code>a ∨ b</code>.
     */
    @Test
    public void simplifyShouldReduceAOrAOrBToAOrB() {
        parseSimplifyAndAssertEquals("a ∨ a ∨ b", "a ∨ b");
    }

    /**
     * Verifies that <code>a ∨ ¬a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceAOrNotAToTrue() {
        parseSimplifyAndAssertEquals("a ∨ ¬a", "true");
    }

    /**
     * Verifies that <code>a ∨ ¬a ∨ b</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceAOrNotAOrBToTrue() {
        parseSimplifyAndAssertEquals("a ∨ ¬a ∨ b", "true");
    }

    /**
     * Verifies that <code>¬a ∨ a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceNotAOrAToTrue() {
        parseSimplifyAndAssertEquals("¬a ∨ a", "true");
    }

    /**
     * Verifies that <code>¬a ∨ a ∨ b</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceNotAOrAOrBToTrue() {
        parseSimplifyAndAssertEquals("¬a ∨ a ∨ b", "true");
    }

    /**
     * Verifies that <code>¬a ∨ ¬a</code> is simplified into <code>¬a</code>.
     */
    @Test
    public void simplifyShouldReduceNotAOrNotAToNotA() {
        parseSimplifyAndAssertEquals("¬a ∨ ¬a", "¬a");
    }

    /**
     * Verifies that <code>¬a ∨ ¬a ∨ b</code> is simplified into <code>¬a ∨ b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAOrNotAOrBToNotAOrB() {
        parseSimplifyAndAssertEquals("¬a ∨ ¬a ∨ b", "¬a ∨ b");
    }
}
