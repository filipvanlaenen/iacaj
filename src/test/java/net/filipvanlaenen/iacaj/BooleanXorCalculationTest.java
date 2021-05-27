package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanXorCalculation</code> class.
 */
public class BooleanXorCalculationTest {
    /**
     * Verifies that two trues are resolved to false.
     */
    @Test
    public void shouldResolveTwoTruesToFalse() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("False", resolved.toString()); // TODO: Should compare resolved with BooleanConstant.FALSE
    }

    /**
     * Verifies that two falses are resolved to false.
     */
    @Test
    public void shouldResolveTwoFalsesToFalse() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("False", resolved.toString()); // TODO: Should compare resolved with BooleanConstant.FALSE
    }

    /**
     * Verifies that true and false are resolved to true.
     */
    @Test
    public void shouldResolveTrueAndFalseToTrue() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("True", resolved.toString()); // TODO: Should compare resolved with BooleanConstant.TRUE
    }
}
