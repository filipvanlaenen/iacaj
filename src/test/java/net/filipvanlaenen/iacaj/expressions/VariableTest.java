package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Variable</code> class.
 */
public class VariableTest {
    /**
     * Verifies that <code>toString</code> returns the name of the variable.
     */
    @Test
    public void toStringShouldReturnTheName() {
        assertEquals("foo", new Variable("foo").toString());
    }
}
