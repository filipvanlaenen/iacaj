package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.createVariableCollection;
import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Unit tests on the <code>XorExpression</code> class.
 */
public class XorFunctionTest {
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
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = false
     * c = ¬a ⊻ b      ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithFalseAndFalse() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.FALSE,
                        VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = true
     * c = ¬a ⊻ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithFalseAndTrue() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B,
                LiteralExpression.TRUE, VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true)));
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
     * c = ¬a ⊻ b      ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithTrueAndTrue() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B,
                LiteralExpression.TRUE, VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a ⊻ a     ⇒ b = true
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithSameVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a
     * d = ¬b ⊻ c      ⇒ d = ¬a ⊻ c
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithIdentityAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A),
                VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_C), true)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a
     * d = ¬b ⊻ c      ⇒ d = a ⊻ c
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithNegationAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new NegationExpression(VARIABLE_A),
                VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_C), false)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = ¬a ⊻ a ⊻ b  ⇒ c = ¬b
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithSameAndOtherVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A, VARIABLE_B), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = false
     * c = a ⊻ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfXorWithFalseAndFalse() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.FALSE,
                        VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false)));
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
     * c = a ⊻ b      ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfXorWithFalseAndTrue() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.TRUE,
                        VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = a ⊻ b      ⇒ c = b
     * </pre>
     */
    @Test
    public void simplificationOfXorWithFalseAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = ¬a ⊻ b      ⇒ c = ¬b
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithFalseAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = ¬a ⊻ b      ⇒ c = b
     * </pre>
     */
    @Test
    public void simplificationOfXorNegatedWithTrueAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = true
     * c = a ⊻ b      ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfXorWithTrueAndTrue() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.TRUE,
                        VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = a ⊻ b      ⇒ c = ¬b
     * </pre>
     */
    @Test
    public void simplificationOfXorWithTrueAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a
     * d = b ⊻ c      ⇒ d = a ⊻ c
     * </pre>
     */
    @Test
    public void simplificationOfXorWithIdentityAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A),
                VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_C), false)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a
     * d = b ⊻ c      ⇒ d = ¬a ⊻ c
     * </pre>
     */
    @Test
    public void simplificationOfXorWithNegationAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new NegationExpression(VARIABLE_A),
                VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_C), true)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ⊻ a ⊻ b  ⇒ c = b
     * </pre>
     */
    @Test
    public void simplificationOfXorWithSameAndOtherVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A, VARIABLE_B), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a ⊻ a      ⇒ b = false
     * </pre>
     */
    @Test
    public void simplificationOfXorWithSameVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A), false)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

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

    /**
     * Verifies that <code>toString</code> returns <code>a ⊻ a</code> correctly.
     */
    @Test
    public void toStringShouldReturnAXorACorrectly() {
        Expression e = new XorFunction(createVariableCollection("a", "a"), false);
        assertEquals("a ⊻ a", e.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>¬a ⊻ a</code> correctly.
     */
    @Test
    public void toStringShouldReturnNotAXorACorrectly() {
        Expression e = new XorFunction(createVariableCollection("a", "a"), true);
        assertEquals("¬a ⊻ a", e.toString());
    }
}
