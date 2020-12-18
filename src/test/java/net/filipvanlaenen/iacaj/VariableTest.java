package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Variable</code> class.
 */
public class VariableTest {
    /**
     * Verifies that the name of the variable is wired correctly from the
     * constructor to the getter.
     */
    @Test
    public void nameShouldBeWiredCorrectlyFromConstructorToGetter() {
        Variable v = new Variable("foo");
        assertEquals("foo", v.getName());
    }
}
