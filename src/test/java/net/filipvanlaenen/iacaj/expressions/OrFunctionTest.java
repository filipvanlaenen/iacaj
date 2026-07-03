package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.createVariableCollection;
import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Unit tests on the <code>OrExpression</code> class.
 */
public class OrFunctionTest {
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
     * The variable e.
     */
    private static final Variable VARIABLE_E = new Variable("e");

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * d = ¬a ∧ b ∧ c
     * e = ¬a ∨ d       ⇒ e = ¬a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithAndWithSameNegatedVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), ValueCollection.of(VARIABLE_A)), VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_D), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E, new NegationExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * d = ¬a ∧ ¬b ∧ ¬c
     * e = ¬a ∨ d       ⇒ e = ¬a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithAndWithSameNegatedVariableAndNegatedVariables() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.empty(), ValueCollection.of(VARIABLE_A, VARIABLE_B, VARIABLE_C)),
                VARIABLE_E, new OrFunction(ValueCollection.of(VARIABLE_D), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E, new NegationExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * d = ¬a ∧ b ∧ ¬c
     * e = ¬a ∨ d       ⇒ e = ¬a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithAndWithSameNegatedVariableAndSomeNegatedVariables() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A, VARIABLE_C)), VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_D), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E, new NegationExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * d = a ∧ b ∧ c
     * e = a ∨ d        ⇒ e = a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithAndWithSameVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B, VARIABLE_C), ValueCollection.empty()),
                VARIABLE_E, new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_D), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * d = a ∧ ¬b ∧ ¬c
     * e = a ∨ d        ⇒ e = a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithAndWithSameVariableAndNegatedVariables() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B, VARIABLE_C)), VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_D), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * d = a ∧ b ∧ ¬c
     * e = a ∨ d        ⇒ e = a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithAndWithSameVariableAndSomeNegatedVariables() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_D,
                new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.of(VARIABLE_C)), VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_D), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = false
     * c = a ∨ b        ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfOrWithFalseAndFalse() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.FALSE, VARIABLE_C,
                        new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = a ∨ ¬b       ⇒ c = ¬b
     * </pre>
     */
    @Test
    public void simplificationOfOrWithFalseAndNegatedVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = true
     * c = a ∨ b        ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithFalseAndTrue() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.TRUE, VARIABLE_C,
                        new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = a ∨ b        ⇒ c = b
     * </pre>
     */
    @Test
    public void simplificationOfOrWithFalseAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a
     * d = b ∨ c        ⇒ d = a ∨ c
     * </pre>
     */
    @Test
    public void simplificationOfOrWithIdentityAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A),
                VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_C), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∧ b
     * e = ¬c ∨ d       ⇒ e = ¬a ∨ ¬b ∨ d
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedAnd() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                        VARIABLE_E, new OrFunction(ValueCollection.of(VARIABLE_D), ValueCollection.of(VARIABLE_C))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_D), ValueCollection.of(VARIABLE_A, VARIABLE_B))));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = ¬a ∨ a ∨ b   ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedAndOtherVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * b = false
     * c = ¬a ∨ b       ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedFalseAndFalse() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.FALSE,
                        VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = ¬a ∨ ¬b      ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedFalseAndNegatedVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new OrFunction(ValueCollection.empty(), ValueCollection.of(VARIABLE_A, VARIABLE_B))));
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
     * c = ¬a ∨ b       ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedFalseAndTrue() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_B, LiteralExpression.TRUE,
                        VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = false
     * c = ¬a ∨ b       ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedFalseAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.FALSE, VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a
     * d = ¬b ∨ c       ⇒ d = ¬a ∨ c
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedIdentityAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A),
                VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_C), ValueCollection.of(VARIABLE_B))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_C), ValueCollection.of(VARIABLE_A))));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a
     * d = ¬b ∨ c       ⇒ d = a ∨ c
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedNegationAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new NegationExpression(VARIABLE_A),
                VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_C), ValueCollection.of(VARIABLE_B))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_C), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = false
     * c = ¬a ∨ b       ⇒ c = false
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedTrueAndFalse() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.FALSE,
                        VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.FALSE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = ¬a ∨ ¬b      ⇒ c = ¬b
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedTrueAndNegatedVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new OrFunction(ValueCollection.empty(), ValueCollection.of(VARIABLE_A, VARIABLE_B))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new NegationExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = true
     * c = ¬a ∨ b       ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedTrueAndTrue() {
        VectorialFunction original =
                new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.TRUE,
                        VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = ¬a ∨ b       ⇒ c = b
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedTrueAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_B), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, new IdentityExpression(VARIABLE_B)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a ∨ a       ⇒ b = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegatedVariable() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_B, new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_A))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = ¬a
     * d = b ∨ c        ⇒ d = ¬a ∨ c
     * </pre>
     */
    @Test
    public void simplificationOfOrWithNegationAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B, new NegationExpression(VARIABLE_A),
                VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_B, VARIABLE_C), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(
                Map.of(VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_C), ValueCollection.of(VARIABLE_A))));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ b
     * e = c ∨ d        ⇒ e = a ∨ b ∨ d
     * </pre>
     */
    @Test
    public void simplificationOfOrWithOr() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()), VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_C, VARIABLE_D), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B, VARIABLE_D), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ ¬b
     * e = c ∨ d        ⇒ e = a ∨ ¬b ∨ d
     * </pre>
     */
    @Test
    public void simplificationOfOrWithOrWithNegatedVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B)), VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_C, VARIABLE_D), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_E));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_E,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_D), ValueCollection.of(VARIABLE_B))));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ b
     * d = c ∨ b        ⇒ d = a ∨ b
     * </pre>
     */
    @Test
    public void simplificationOfOrWithOrWithSameVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()), VARIABLE_D,
                new OrFunction(ValueCollection.of(VARIABLE_C, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ ¬b
     * d = c ∨ b      ⇒ d = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithOrWithSameVariableNegated() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B)), VARIABLE_D,
                new OrFunction(ValueCollection.of(VARIABLE_C, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ b
     * d = c ∨ ¬b      ⇒ d = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithOrWithSameVariableNotNegated() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_C, new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                        VARIABLE_D, new OrFunction(ValueCollection.of(VARIABLE_C), ValueCollection.of(VARIABLE_B))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_D));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_D, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * c = a ∨ a ∨ b    ⇒ c = a ∨ b
     * </pre>
     */
    @Test
    public void simplificationOfOrWithSameAndOtherVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * b = a ∨ a        ⇒ b = a
     * </pre>
     */
    @Test
    public void simplificationOfOrWithSameVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_B,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_A), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_B));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_B, new IdentityExpression(VARIABLE_A)));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = a ∨ ¬b       ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithTrueAndNegatedVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B))));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * b = true
     * c = a ∨ b        ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithTrueAndTrue() {
        VectorialFunction original = new VectorialFunction(
                Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_B, LiteralExpression.TRUE, VARIABLE_C,
                        new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

    /**
     * Verifies a functional test on the simplify method as described below.
     *
     * <pre>
     * a = true
     * c = a ∨ b        ⇒ c = true
     * </pre>
     */
    @Test
    public void simplificationOfOrWithTrueAndVariable() {
        VectorialFunction original = new VectorialFunction(Map.of(VARIABLE_A, LiteralExpression.TRUE, VARIABLE_C,
                new OrFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty())));
        VectorialFunction actual = original.simplify(new Word(VARIABLE_C));
        VectorialFunction expected = new VectorialFunction(Map.of(VARIABLE_C, LiteralExpression.TRUE));
        assertTrue(actual.containsSame(expected));
    }

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
