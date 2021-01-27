package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>NoInputParameters</code> class.
 */
public class NoInputParametersTest {
    /**
     * Verifies that the message in <code>toString()</code> is correct.
     */
    @Test
    public void toStringMessageIsCorrect() {
        assertEquals("No input parameters detected in the Boolean function.", new NoInputParameters().toString());
    }
}
