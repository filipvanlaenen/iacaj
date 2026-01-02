package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>XorExpression</code> class.
 */
public class XorExpressionTest {
    /**
     * Verifies that <code>a ⊻ a</code> is simplified into <code>false</code>.
     */
    @Test
    public void simplifyShouldReduceAXorAToFalse() {
        parseSimplifyAndAssertEquals("a ⊻ a", "false");
    }

    /**
     * Verifies that <code>a ⊻ a ⊻ b</code> is simplified into <code>¬b</code>.
     */
    @Test
    public void simplifyShouldReduceAXorAXorBToNotB() {
        parseSimplifyAndAssertEquals("a ⊻ a ⊻ b", "b");
    }

    /**
     * Verifies that <code>a ⊻ a ⊻ a ⊻ b</code> is simplified into <code>¬b</code>.
     */
    @Test
    public void simplifyShouldReduceAXorAXorAXorBToNotB() {
        parseSimplifyAndAssertEquals("a ⊻ a ⊻ a ⊻ b", "a ⊻ b");
    }

    /**
     * Verifies that <code>a ⊻ ¬a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceAXorNotAToTrue() {
        parseSimplifyAndAssertEquals("a ⊻ ¬a", "true");
    }

    /**
     * Verifies that <code>a ⊻ ¬a ⊻ b</code> is simplified into <code>b</code>.
     */
    @Test
    public void simplifyShouldReduceAXorNotAXorBToB() {
        parseSimplifyAndAssertEquals("a ⊻ ¬a ⊻ b", "¬b");
    }

    /**
     * Verifies that <code>¬a ⊻ a</code> is simplified into true.
     */
    @Test
    public void simplifyShouldReduceNotAXorAToTrue() {
        parseSimplifyAndAssertEquals("¬a ⊻ a", "true");
    }

    /**
     * Verifies that <code>¬a ⊻ a ⊻ b</code> is simplified into <code>b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAXorAXorBToB() {
        parseSimplifyAndAssertEquals("¬a ⊻ a ⊻ b", "¬b");
    }

    /**
     * Verifies that <code>¬a ⊻ ¬a</code> is simplified into false.
     */
    @Test
    public void simplifyShouldReduceNotAXorNotAToFalse() {
        parseSimplifyAndAssertEquals("¬a ⊻ ¬a", "false");
    }

    /**
     * Verifies that <code>¬a ⊻ ¬a ⊻ b</code> is simplified into <code>¬b</code>.
     */
    @Test
    public void simplifyShouldReduceNotAXorNotAXorBToNotB() {
        parseSimplifyAndAssertEquals("¬a ⊻ ¬a ⊻ b", "b");
    }
}
