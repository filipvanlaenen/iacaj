package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>NoCollisionFound</code> class.
 */
public class NoCollisionFoundTest {
    /**
     * Verifies that the message in <code>toString()</code> is correct.
     */
    @Test
    public void toStringMessageIsCorrect() {
        assertEquals("No collision found in the Boolean function.", new NoCollisionFound().toString());
    }
}
