package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the class <code>InputParameter</code>.
 */
public class InputParameterTest {
    /**
     * Verifies that a parameter starting with an i is recognized as an input
     * parameter.
     */
    @Test
    public void isInputParameterReturnsTrueWhenStartingWithI() {
        assertTrue(InputParameter.isInputParameter("i1"));
    }

    /**
     * Verifies that a parameter starting with a v is not recognized as an input
     * parameter.
     */
    @Test
    public void isInputParameterReturnsFalseWhenStartingWithV() {
        assertFalse(InputParameter.isInputParameter("v1"));
    }

    /**
     * Verifies that a parameter starting with an o is not recognized as an input
     * parameter.
     */
    @Test
    public void isInputParameterReturnsFalseWhenStartingWithO() {
        assertFalse(InputParameter.isInputParameter("o1"));
    }
}
