package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Map;

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
}
