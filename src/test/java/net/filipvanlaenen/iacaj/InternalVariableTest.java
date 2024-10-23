package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>InternalVariable</code> class.
 */
public class InternalVariableTest {
    /**
     * Verifies that an internal variable can be retrieved. The name used has a high number to increase the chance that
     * it hasn't een used before.
     */
    @Test
    public void getShouldReturnAnInternalVariableWithTheCorrectName() {
        assertEquals("v100001", InternalVariable.get("v100001").getName());
    }

    /**
     * Verifies that the same internal variable is retrieved when using the same name. The name used has a high number
     * to increase the chance that it hasn't een used before.
     */
    @Test
    public void getShouldReturnTheSameInternalVariableForTheSameName() {
        InternalVariable expected = InternalVariable.get("v100002");
        assertEquals(expected, InternalVariable.get("v100002"));
    }

    /**
     * Verifies that a parameter starting with an i is not recognized as an internal variable.
     */
    @Test
    public void isInternalVariableReturnsTrueWhenStartingWithI() {
        assertFalse(InternalVariable.isInternalVariable("i1"));
    }

    /**
     * Verifies that a parameter starting with a v is recognized as an internal variable.
     */
    @Test
    public void isInternalVariableReturnsFalseWhenStartingWithV() {
        assertTrue(InternalVariable.isInternalVariable("v1"));
    }

    /**
     * Verifies that a parameter starting with an o is not recognized as an internal variable.
     */
    @Test
    public void isInternalVariableReturnsFalseWhenStartingWithO() {
        assertFalse(InternalVariable.isInternalVariable("o1"));
    }
}
