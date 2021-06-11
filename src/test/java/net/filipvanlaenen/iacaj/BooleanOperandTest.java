package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanOperand</code> class.
 */
public class BooleanOperandTest {
    /**
     * The Boolean operand <code>a1</code> to run tests on.
     */
    private static final BooleanOperand BOOLEAN_OPERAND_A1 = new BooleanOperand("a1");
    /**
     * The Boolean operand <code>not a1</code> to run tests on.
     */
    private static final BooleanOperand BOOLEAN_OPERAND_NOT_A1 = new BooleanOperand("¬a1");

    /**
     * Verifies that getName is wired correctly from the one parameter constructor.
     */
    @Test
    public void shouldGetNameFromOneParameterConstructor() {
        assertEquals("a1", BOOLEAN_OPERAND_A1.getName());
    }

    /**
     * Verifies that getName is wired correctly from the one parameter constructor
     * if the operand is negated.
     */
    @Test
    public void shouldGetNameFromOneParameterConstructorWithNegation() {
        assertEquals("a1", BOOLEAN_OPERAND_NOT_A1.getName());
    }

    /**
     * Verifies that getNumber is wired correctly from the one parameter
     * constructor.
     */
    @Test
    public void shouldGetNumberFromOneParameterConstructor() {
        assertEquals(1, BOOLEAN_OPERAND_A1.getNumber());
    }

    /**
     * Verifies that getNumber is extracted correctly.
     */
    @Test
    public void shouldExtractNumberCorrectly() {
        assertEquals(2, new BooleanOperand("a2").getNumber());
    }

    /**
     * Verifies that isInputParameter returns false for an operand that doesn't use
     * an input parameter.
     */
    @Test
    public void isInputParameterShouldReturnFalseOnOtherParameter() {
        assertFalse(BOOLEAN_OPERAND_A1.isInputParameter());
    }

    /**
     * Verifies that isInputParameter returns true for an input parameter.
     */
    @Test
    public void isInputParameterShouldReturnTrueForAnInputParameter() {
        assertTrue(new BooleanOperand("i1").isInputParameter());
    }

    /**
     * Verifies that for a positive operand, isNegated returns false.
     */
    @Test
    public void isNegatedShouldReturnFalseWhenNotNegated() {
        assertFalse(BOOLEAN_OPERAND_A1.isNegated());
    }

    /**
     * Verifies that for a negated operand, isNegated returns true.
     */
    @Test
    public void isNegatedShouldReturnTrueWhenNegated() {
        assertTrue(BOOLEAN_OPERAND_NOT_A1.isNegated());
    }

    /**
     * Verifies that the Java string is correct for a regular operand.
     */
    @Test
    public void toJavaStringReturnsRegularOperandCorrectly() {
        assertEquals("a1", BOOLEAN_OPERAND_A1.toJavaString());
    }

    /**
     * Verifies that the Java string is correct for a negated operand.
     */
    @Test
    public void toJavaStringReturnsNegatedOperandCorrectly() {
        assertEquals("!a1", BOOLEAN_OPERAND_NOT_A1.toJavaString());
    }

    /**
     * Verifies that toString is correct for a regular operand.
     */
    @Test
    public void toStringReturnsRegularOperandCorrectly() {
        assertEquals("a1", BOOLEAN_OPERAND_A1.toString());
    }

    /**
     * Verifies that toString is correct for a negated operand.
     */
    @Test
    public void toStringReturnsNegatedOperandCorrectly() {
        assertEquals("¬a1", BOOLEAN_OPERAND_NOT_A1.toString());
    }

    /**
     * Verifies negation negates the operand correctly.
     */
    @Test
    public void negationShouldNegate() {
        assertEquals("¬a1", BOOLEAN_OPERAND_A1.negated().toString());
    }
}
