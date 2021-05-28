package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanConstant</code> class.
 */
public class BooleanConstantTest {
    /**
     * Verifies that get returns TRUE when asked for true.
     */
    @Test
    public void getShouldReturnTrue() {
        assertEquals(BooleanConstant.TRUE, BooleanConstant.get(true));
    }

    /**
     * Verifies that get returns FALSE when asked for false.
     */
    @Test
    public void getShouldReturnFalse() {
        assertEquals(BooleanConstant.FALSE, BooleanConstant.get(false));
    }

    /**
     * Verifies that deepClone returns TRUE for TRUE.
     */
    @Test
    public void deepCloneShouldReturnTrueForTrue() {
        assertEquals(BooleanConstant.TRUE, BooleanConstant.TRUE.deepClone());
    }

    /**
     * Verifies that deepClone returns FALSE for FALSE.
     */
    @Test
    public void deepCloneShouldReturnFalseForFalse() {
        assertEquals(BooleanConstant.FALSE, BooleanConstant.FALSE.deepClone());
    }

    /**
     * Verifies that isFalse returns false for TRUE.
     */
    @Test
    public void isFalseShouldReturnFalseForTrue() {
        assertFalse(BooleanConstant.TRUE.isFalse());
    }

    /**
     * Verifies that isFalse returns true for False.
     */
    @Test
    public void isFalseShouldReturnTrueForFalse() {
        assertTrue(BooleanConstant.FALSE.isFalse());
    }

    /**
     * Verifies that isTrue returns true for TRUE.
     */
    @Test
    public void isTrueShouldReturnTrueForTrue() {
        assertTrue(BooleanConstant.TRUE.isTrue());
    }

    /**
     * Verifies that isTrue returns false for False.
     */
    @Test
    public void isTrueShouldReturnFalseForFalse() {
        assertFalse(BooleanConstant.FALSE.isTrue());
    }

    /**
     * Verifies that resolve returns TRUE for TRUE.
     */
    @Test
    public void resolveShouldReturnTrueForTrue() {
        assertEquals(BooleanConstant.TRUE, BooleanConstant.TRUE.resolve(new BooleanFunction()));
    }

    /**
     * Verifies that resolve returns FALSE for FALSE.
     */
    @Test
    public void resolveShouldReturnFalseForFalse() {
        assertEquals(BooleanConstant.FALSE, BooleanConstant.FALSE.resolve(new BooleanFunction()));
    }

    /**
     * Verifies that toJavaString return true for true.
     */
    @Test
    public void toJavaStringShouldReturnTrueForTrue() {
        assertEquals("true", BooleanConstant.TRUE.toJavaString());
    }

    /**
     * Verifies that toJavaString return false for false.
     */
    @Test
    public void toJavaStringShouldReturnFalseForFalse() {
        assertEquals("false", BooleanConstant.FALSE.toJavaString());
    }

    /**
     * Verifies that toString return True for true.
     */
    @Test
    public void toStringShouldReturnTrueForTrue() {
        assertEquals("True", BooleanConstant.TRUE.toString());
    }

    /**
     * Verifies that toString return False for false.
     */
    @Test
    public void toStringShouldReturnFalseForFalse() {
        assertEquals("False", BooleanConstant.FALSE.toString());
    }

    /**
     * Verifies that the list of input parameters is empty.
     */
    @Test
    public void getInputParametersReturnsAnEmptyCollection() {
        assertTrue(BooleanConstant.FALSE.getInputParameters().isEmpty());
    }

    /**
     * Verifies that the list of internal variable is empty.
     */
    @Test
    public void getInternalVariablesReturnsAnEmptyCollection() {
        assertTrue(BooleanConstant.FALSE.getInternalVariables().isEmpty());
    }
    /**
     * Verifies that the operator is null.
     */
    @Test
    public void getOperatorReturnsNull() {
        assertNull(BooleanConstant.FALSE.getOperator());
    }
}
