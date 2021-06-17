package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanOperator</code> class.
 */
public class BooleanOperatorTest {
    /**
     * Verifies that the symbol is wired correctly from the constructor to the
     * getter.
     */
    @Test
    public void symbolShouldBeWiredCorrectlyFromConstructorToGetter() {
        assertEquals("∧", BooleanOperator.And.getSymbol());
    }

    /**
     * Verifies that the Java symbol is wired correctly from the constructor to the
     * getter.
     */
    @Test
    public void javaSymbolShouldBeWiredCorrectlyFromConstructorToGetter() {
        assertEquals("&", BooleanOperator.And.getJavaSymbol());
    }

    /**
     * Verifies that the And instance creates a BooleanAndCalculation.
     */
    @Test
    public void andInstanceShouldCreateBooleanAndCalculation() {
        assertTrue(BooleanOperator.And.createBooleanCalculation("i1 ∧ i2") instanceof BooleanAndCalculation);
    }

    /**
     * Verifies that the Or instance creates a BooleanOrCalculation.
     */
    @Test
    public void orInstanceShouldCreateBooleanOrCalculation() {
        assertTrue(BooleanOperator.Or.createBooleanCalculation("i1 ∨ i2") instanceof BooleanOrCalculation);
    }

    /**
     * Verifies that the Xor instance creates a BooleanXorCalculation.
     */
    @Test
    public void xorInstanceShouldCreateBooleanXorCalculation() {
        assertTrue(BooleanOperator.Xor.createBooleanCalculation("i1 ⊻ i2") instanceof BooleanXorCalculation);
    }
}
