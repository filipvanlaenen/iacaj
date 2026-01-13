package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.createVariableCollection;
import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Verifies that <code>toString</code> returns <code>a ∧ a</code> correctly.
     */
    @Test
    public void toStringShouldReturnAAndACorrectly() {
        Expression e = new AndExpression(createVariableCollection("a", "a"), createVariableCollection());
        assertEquals("a ∧ a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>¬a ∧ ¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnNotAAndNotACorrectly() {
        Expression e = new AndExpression(createVariableCollection(), createVariableCollection("a", "a"));
        assertEquals("¬a ∧ ¬a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>a ∧ ¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnAAndNotACorrectly() {
        Expression e = new AndExpression(createVariableCollection("a"), createVariableCollection("a"));
        assertEquals("a ∧ ¬a", e.toString());
    }
}
