package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Variable</code> class.
 */
public class VariableTest {
    /**
     * Verifies that the name of the variable is wired correctly from the constructor to the getter.
     */
    @Test
    public void nameShouldBeWiredCorrectlyFromConstructorToGetter() {
        Variable v = new Variable("a1");
        assertEquals("a1", v.getName());
    }

    /**
     * Verifies that the number of the variable is wired correctly from the constructor to the getter.
     */
    @Test
    public void numberShouldBeWiredCorrectlyFromConstructorToGetter() {
        Variable v = new Variable("a1");
        assertEquals(1, v.getNumber());
    }
}
