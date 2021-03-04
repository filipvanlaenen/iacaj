package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AllInputParametersEliminated</code> class.
 */
public class AllInputParametersEliminatedTest {
    /**
     * Verifies that the message in <code>toString()</code> is correct.
     */
    @Test
    public void toStringMessageIsCorrect() {
        assertEquals("All input parameters were eliminated while resolving the Boolean function.",
                new AllInputParametersEliminated().toString());
    }
}
