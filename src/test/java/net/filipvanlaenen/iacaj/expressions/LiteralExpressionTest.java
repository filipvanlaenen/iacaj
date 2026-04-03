package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Assertions.parseSimplifyAndAssertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Map;

/**
 * Unit tests on the <code>LiteralExpression</code> class.
 */
public class LiteralExpressionTest {
    /**
     * The variable a.
     */
    private static final Variable VARIABLE_A = new Variable("a");

    /**
     * Verifies that <code>true</code> is simplified into <code>true</code>.
     */
    @Test
    public void simplifyShouldReduceTrueToTrue() {
        parseSimplifyAndAssertEquals("true", "true");
    }

    /**
     * Verifies that <code>false</code> is simplified into <code>false</code>.
     */
    @Test
    public void simplifyShouldReduceFalseToFalse() {
        parseSimplifyAndAssertEquals("false", "false");
    }

    /**
     * Verifies that <code>toString</code> returns <code>true</code> correctly.
     */
    @Test
    public void toStringShouldReturnTrueCorrectly() {
        assertEquals("true", LiteralExpression.TRUE.toString());
    }

    /**
     * Verifies that <code>toString</code> returns <code>false</code> correctly.
     */
    @Test
    public void toStringShouldReturnFalseCorrectly() {
        assertEquals("false", LiteralExpression.FALSE.toString());
    }

    /**
     * Verifies a functional test of the simplify as described below.
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
     * Verifies a functional test of the simplify as described below.
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
}
