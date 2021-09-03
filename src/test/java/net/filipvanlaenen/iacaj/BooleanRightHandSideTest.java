package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanRightHandSide</code> class.
 */
public class BooleanRightHandSideTest {
    /**
     * Verifies that True can be parsed.
     */
    @Test
    public void shouldParseTrue() {
        assertEquals(BooleanConstant.TRUE, BooleanRightHandSide.parse("True"));
    }

    /**
     * Verifies that False can be parsed.
     */
    @Test
    public void shouldParseFalse() {
        assertEquals(BooleanConstant.FALSE, BooleanRightHandSide.parse("False"));
    }

    /**
     * Verifies that equality can be parsed.
     */
    @Test
    public void shouldParseEquality() {
        assertEquals(new BooleanEquation("i1"), BooleanRightHandSide.parse("i1"));
    }

    /**
     * Verifies that a calculation can be parsed.
     */
    @Test
    public void shouldParseACalculation() {
        assertEquals("i1 ∧ i2", BooleanRightHandSide.parse("i1 ∧ i2").toString());
    }
}
