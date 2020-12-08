package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OutputParameterTest {
    /**
     * Verifies that a parameter starting with an i is not recognized as an output
     * parameter.
     */
    @Test
    public void isOutputParameterReturnsFalseWhenStartingWithI() {
        assertFalse(OutputParameter.isOutputParameter("i1"));
    }

    /**
     * Verifies that a parameter starting with a v is not recognized as an output
     * parameter.
     */
    @Test
    public void isOutputParameterReturnsFalseWhenStartingWithV() {
        assertFalse(OutputParameter.isOutputParameter("v1"));
    }

    /**
     * Verifies that a parameter starting with an o is recognized as an output
     * parameter.
     */
    @Test
    public void isOutputParameterReturnsTrueWhenStartingWithO() {
        assertTrue(OutputParameter.isOutputParameter("o1"));
    }
}
