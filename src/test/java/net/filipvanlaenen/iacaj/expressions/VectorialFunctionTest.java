package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Unit tests on the <code>VectorialFunction</code> class.
 */
public class VectorialFunctionTest {
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
     * Verifies that <code>toString</code> returns an empty string for an empty vectorial function.
     */
    @Test
    public void toStringShouldReturnAnEmptyStringForAnEmptyVectoralFunction() {
        assertEquals("", new VectorialFunction(Map.empty()).toString());
    }

    /**
     * Verifies that <code>toString</code> returns a line with a variable and an expression for a vectorial function of
     * dimension one.
     */
    @Test
    public void toStringShouldReturnOneLineForAVectorialFunctionOfDimensionOne() {
        VectorialFunction vf = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A)));
        assertEquals("b = a", vf.toString());
    }

    /**
     * Verifies that <code>toString</code> returns two lines for a vectorial function of dimension two.
     */
    @Test
    public void toStringShouldReturnTwoLinesForAVectorialFunctionOfDimensionTwo() {
        VectorialFunction vf = new VectorialFunction(
                Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A), VARIABLE_C, new NegationExpression(VARIABLE_B)));
        String actual = vf.toString();
        String expected1 = "b = a\nc = ¬b";
        String expected2 = "c = ¬b\nb = a";
        assertTrue(Collection.of(expected1, expected2).contains(actual));
    }

    /**
     * Verifies the following test case:
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
     * Verifies the following test case:
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
     * Verifies the following test case:
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
     * Verifies the following test case:
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
     * Verifies the following test case:
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
     * Verifies the following test case:
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
     * Verifies the following test case:
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

    /**
     * Verifies the following test case:
     *
     * <pre>
     * a = false ⇒ a = false
     * </pre>
     */
    @Test
    public void simplificationOfLiteralFalse() {
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE));
        VectorialFunction actual = expected.simplify(new Word(VARIABLE_A));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * a = true ⇒ a = true
     * </pre>
     */
    @Test
    public void simplificationOfLiteralTrue() {
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE));
        VectorialFunction actual = expected.simplify(new Word(VARIABLE_A));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * c = a ∧ b
     * d = ¬c     ⇒ d = ¬a ∨ ¬b
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithAnd() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                        VARIABLE_D, new NegationExpression(VARIABLE_C)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new OrFunction(ValueCollection.empty(), ValueCollection.of(VARIABLE_A, VARIABLE_B))));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * a = false
     * b = ¬a     ⇒ b = true
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithFalse() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, new NegationExpression(VARIABLE_A)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * b = ¬a   
     * c = ¬b     ⇒ c = a
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithNegatedVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new NegationExpression(VARIABLE_A), VARIABLE_C, new NegationExpression(VARIABLE_B)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * c = a ∨ b
     * d = ¬c     ⇒ d = ¬a ∧ ¬b
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithOr() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                        VARIABLE_D, new NegationExpression(VARIABLE_C)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.empty(), ValueCollection.of(VARIABLE_A, VARIABLE_B))));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * a = true
     * b = ¬a     ⇒ b = false
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithTrue() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, new NegationExpression(VARIABLE_A)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * b = a   
     * c = ¬b     ⇒ c = ¬a
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A), VARIABLE_C, new NegationExpression(VARIABLE_B)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies the following test case:
     *
     * <pre>
     * c = a ⊻ b
     * d = ¬c     ⇒ d = ¬a ⊻ b
     * </pre>
     */
    @Test
    public void simplificationOfNegationWithXor() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), false), VARIABLE_D,
                        new NegationExpression(VARIABLE_C)));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true)));
        assertTrue(actual.containsSame(expected));
    }
}
