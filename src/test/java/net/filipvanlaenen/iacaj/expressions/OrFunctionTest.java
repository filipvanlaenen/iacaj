package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.createVariableCollection;
import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>OrExpression</code> class.
 */
public class OrFunctionTest {
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

    /**
     * Verifies that <code>toString</code> returns <code>a ∨ a</code> correctly.
     */
    @Test
    public void toStringShouldReturnAOrACorrectly() {
        Expression e = new OrFunction(createVariableCollection("a", "a"), createVariableCollection());
        assertEquals("a ∨ a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>¬a ∨ ¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnNotAOrNotACorrectly() {
        Expression e = new OrFunction(createVariableCollection(), createVariableCollection("a", "a"));
        assertEquals("¬a ∨ ¬a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>a ∨ ¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnAOrNotACorrectly() {
        Expression e = new OrFunction(createVariableCollection("a"), createVariableCollection("a"));
        assertEquals("a ∨ ¬a", e.toString());
    }
}
