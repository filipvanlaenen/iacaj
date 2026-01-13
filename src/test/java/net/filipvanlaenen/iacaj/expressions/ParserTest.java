package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
     * Verifies that the parser can parse <code>true</code> into the literal expression for true.
     */
    @Test
    public void parseShouldParseTrue() {
        assertEquals(LiteralExpression.TRUE, Parser.parse("true"));
    }

    /**
     * Verifies that the parser can parse <code>false</code> into the literal expression for false.
     */
    @Test
    public void parseShouldParseFalse() {
        assertEquals(LiteralExpression.FALSE, Parser.parse("false"));
    }

    /**
     * Verifies that the parser can parse <code>a</code> into an identity expression.
     */
    @Test
    public void parseShouldParseVariableA() {
        assertEquals(new IdentityExpression(VARIABLE_A), Parser.parse("a"));
    }

    /**
     * Verifies that the parser can parse <code>¬a</code> into an identity expression.
     */
    @Test
    public void parseShouldParseVariableNotA() {
        assertEquals(new NegationExpression(VARIABLE_A), Parser.parse("¬a"));
    }

    /**
     * Verifies that the parser can parse <code>a ∧ b</code> into an and expression.
     */
    @Test
    public void parseShouldParseVariableAAndB() {
        assertEquals(new AndFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), ValueCollection.empty()),
                Parser.parse("a ∧ b"));
    }

    /**
     * Verifies that the parser can parse <code>a ∧ ¬b</code> into an and expression.
     */
    @Test
    public void parseShouldParseVariableAAndNotB() {
        assertEquals(new AndFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B)),
                Parser.parse("a ∧ ¬b"));
    }

    /**
     * Verifies that the parser can parse <code>a ∨ ¬b</code> into an or expression.
     */
    @Test
    public void parseShouldParseVariableAOrNotB() {
        assertEquals(new OrFunction(ValueCollection.of(VARIABLE_A), ValueCollection.of(VARIABLE_B)),
                Parser.parse("a ∨ ¬b"));
    }

    /**
     * Verifies that the parser can parse <code>a ⊻ ¬b</code> into an xor expression.
     */
    @Test
    public void parseShouldParseVariableAXorNotB() {
        assertEquals(new XorFunction(ValueCollection.of(VARIABLE_A, VARIABLE_B), true), Parser.parse("a ⊻ ¬b"));
    }
}
