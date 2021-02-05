package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>NoCollisionFoundYet</code> class.
 */
public class NoCollisionFoundYetTest {
    /**
     * Verifies that the message in <code>toString()</code> is correct after 1
     * iteration.
     */
    @Test
    public void toStringMessageIsCorrectAfterOneIteration() {
        assertEquals("No collision found in the Boolean function after 1 iteration.",
                new NoCollisionFoundYet(1).toString());
    }

    /**
     * Verifies that the message in <code>toString()</code> is correct after 2
     * iterations.
     */
    @Test
    public void toStringMessageIsCorrectAfterTwoIterations() {
        assertEquals("No collision found in the Boolean function after 2 iterations.",
                new NoCollisionFoundYet(2).toString());
    }
}
