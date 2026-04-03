package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.createVariableCollection;
import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Unit tests on the <code>AndExpression</code> class.
 */
public class AndFunctionTest {
    /**
     * The variable a.
     */
    private static final Variable VARIABLE_A = new Variable("a");
    /**
     * The variable b.
     */
    private static final Variable VARIABLE_B = new Variable("b");
    /**
     * The variable c.
     */
    private static final Variable VARIABLE_C = new Variable("c");

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
        Expression e = new AndFunction(createVariableCollection("a", "a"), createVariableCollection());
        assertEquals("a ∧ a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>¬a ∧ ¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnNotAAndNotACorrectly() {
        Expression e = new AndFunction(createVariableCollection(), createVariableCollection("a", "a"));
        assertEquals("¬a ∧ ¬a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>a ∧ ¬a</code> correctly.
     */
    @Test
    public void toStringShouldReturnAAndNotACorrectly() {
        Expression e = new AndFunction(createVariableCollection("a"), createVariableCollection("a"));
        assertEquals("a ∧ ¬a", e.toString());
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = false
     * c = a ∧ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithFalseAndFalse() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.FALSE, VARIABLE_C,
                        new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = true
     * c = a ∧ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithFalseAndTrue() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.TRUE, VARIABLE_C,
                        new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = a ∧ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithFalseAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = ¬a ∧ a ∧ b ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithNegatedAndOtherVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = false
     * c = ¬a ∧ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithNegatedFalseAndFalse() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.FALSE,
                        VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = true
     * c = ¬a ∧ b      ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfAndWithNegatedFalseAndTrue() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.TRUE,
                        VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = false
     * c = ¬a ∧ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithNegatedTrueAndFalse() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.FALSE,
                        VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = true
     * c = ¬a ∧ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithNegatedTrueAndTrue() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.TRUE,
                        VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a ∧ a     ⇒ b = false
     * </pre>
     */
    @Test
    public void simplificationOfAndWithNegatedVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new AndFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∧ a ∧ b  ⇒ c = a ∧ b
     * </pre>
     */
    @Test
    public void simplificationOfAndWithSameAndOtherVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a ∧ a      ⇒ b = a
     * </pre>
     */
    @Test
    public void simplificationOfAndWithSameVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = true
     * c = a ∧ b      ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfAndWithTrueAndTrue() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.TRUE, VARIABLE_C,
                        new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = a ∧ b      ⇒ c = b
     * </pre>
     */
    @Test
    public void simplificationOfAndWithTrueAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }
}
