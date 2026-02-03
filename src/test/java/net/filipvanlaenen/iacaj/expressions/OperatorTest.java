package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Operator</code> class.
 */
public class OperatorTest {
    /**
     * Verifies that <code>getSymbol</code> returns the operator's symbol.
     */
    @Test
    public void getSymbolShouldReturnTheSymbol() {
        assertEquals("∧", Operator.AND.getSymbol());
    }

    /**
     * Verifies that <code>toString</code> returns the operator's symbol.
     */
    @Test
    public void toStringShouldReturnTheSymbol() {
        assertEquals("∧", Operator.AND.toString());
    }
}
