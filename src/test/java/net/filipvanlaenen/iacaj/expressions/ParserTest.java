package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Unit tests on the <code>Parser</code> class.
 */
public class ParserTest {
    /**
     * Variable a.
     */
    private static final Variable VARIABLE_A = new Variable("a");
    /**
     * Variable b.
     */
    private static final Variable VARIABLE_B = new Variable("b");

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>true</code> into the literal expression for true.
     */
    @Test
    public void parseExpressionShouldParseTrue() {
        assertEquals(LiteralExpression.TRUE, Parser.parseExpression("true"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>false</code> into the literal expression for false.
     */
    @Test
    public void parseExpressionShouldParseFalse() {
        assertEquals(LiteralExpression.FALSE, Parser.parseExpression("false"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>a</code> into an identity expression.
     */
    @Test
    public void parseExpressionShouldParseVariableA() {
        assertEquals(new IdentityExpression(VARIABLE_A), Parser.parseExpression("a"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>¬a</code> into an identity expression.
     */
    @Test
    public void parseExpressionShouldParseVariableNotA() {
        assertEquals(new NegationExpression(VARIABLE_A), Parser.parseExpression("¬a"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>a ∧ b</code> into an and expression.
     */
    @Test
    public void parseExpressionShouldParseVariableAAndB() {
        assertEquals(new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                Parser.parseExpression("a ∧ b"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>a ∧ ¬b</code> into an and expression.
     */
    @Test
    public void parseExpressionShouldParseVariableAAndNotB() {
        assertEquals(new AndFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B)),
                Parser.parseExpression("a ∧ ¬b"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>a ∨ ¬b</code> into an or expression.
     */
    @Test
    public void parseExpressionShouldParseVariableAOrNotB() {
        assertEquals(new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B)),
                Parser.parseExpression("a ∨ ¬b"));
    }

    /**
     * Verifies that <code>parseExpressions</code> can parse <code>a ⊻ ¬b</code> into an xor expression.
     */
    @Test
    public void parseExpressionShouldParseVariableAXorNotB() {
        assertEquals(new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true),
                Parser.parseExpression("a ⊻ ¬b"));
    }

    /**
     * Verifies that <code>parseVectorialFunction</code> can parse an empty string.
     *
     * Note: this unit test relies on the <code>toString</code> method of <code>VectorialFunction</code> working
     * correctly.
     */
    @Test
    public void parseVectorialFunctionShouldParseEmptyString() {
        VectorialFunction vf = Parser.parseVectorialFunction("");
        assertEquals("", vf.toString());
    }

    /**
     * Verifies that <code>parseVectorialFunction</code> can parse a string with one line.
     *
     * Note: this unit test relies on the <code>toString</code> method of <code>VectorialFunction</code> working
     * correctly.
     */
    @Test
    public void parseVectorialFunctionShouldParseStringWithOneLine() {
        VectorialFunction vf = Parser.parseVectorialFunction("b = a");
        assertEquals("b = a", vf.toString());
    }

    /**
     * Verifies that <code>parseVectorialFunction</code> can parse a string with two lines.
     *
     * Note: this unit test relies on the <code>toString</code> method of <code>VectorialFunction</code> working
     * correctly.
     */
    @Test
    public void parseVectorialFunctionShouldParseStringWithTwoLines() {
        VectorialFunction vf = Parser.parseVectorialFunction("b = a\nc = ¬b");
        String actual = vf.toString();
        String expected1 = "b = a\nc = ¬b";
        String expected2 = "c = ¬b\nb = a";
        assertTrue(Collection.of(expected1, expected2).contains(actual));
    }

    /**
     * Verifies that <code>parseVectorialFunction</code> skips lines not containing an equals sign.
     *
     * Note: this unit test relies on the <code>toString</code> method of <code>VectorialFunction</code> working
     * correctly.
     */
    @Test
    public void parseVectorialFunctionShouldSkipLinesWithoutEqualsSign() {
        VectorialFunction vf = Parser.parseVectorialFunction("\nFoo\nBar");
        assertEquals("", vf.toString());
    }
}
