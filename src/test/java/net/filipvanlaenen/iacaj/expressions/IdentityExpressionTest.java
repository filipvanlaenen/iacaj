package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Unit tests on the <code>IdentityExpression</code> class.
 */
public class IdentityExpressionTest {
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
     * The variable d.
     */
    private static final Variable VARIABLE_D = new Variable("d");

    /**
     * Verifies that <code>a</code> is simplified into <code>a</code>.
     */
    @Test
    public void simplifyShouldReduceAToA() {
        parseSimplifyAndAssertEquals("a", "a");
    }

    /**
     * Verifies that <code>toString</code> returns <code>a</code> correctly.
     */
    @Test
    public void toStringShouldReturnACorrectly() {
        assertEquals("a", new IdentityExpression(new Variable("a")).toString());
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∧ b
     * d = c     ⇒ d = a ∧ b
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithAnd() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                        VARIABLE_D, new IdentityExpression(VARIABLE_C)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = a     ⇒ b = false
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithFalse() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, new IdentityExpression(VARIABLE_A)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a
     * c = b     ⇒ c = ¬a
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithNegatedVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new NegationExpression(VARIABLE_A), VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ b
     * d = c     ⇒ d = a ∨ b
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithOr() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                        VARIABLE_D, new IdentityExpression(VARIABLE_C)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = a     ⇒ b = true
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithTrue() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, new IdentityExpression(VARIABLE_A)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a
     * c = b     ⇒ c = a
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A), VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ⊻ b
     * d = c     ⇒ d = a ⊻ b
     * </pre>
     */
    @Test
    public void simplificationOfIdentityWithXor() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false), VARIABLE_D,
                        new IdentityExpression(VARIABLE_C)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false)));
        assertTrue(actual.containsSame(expected));
    }
}
