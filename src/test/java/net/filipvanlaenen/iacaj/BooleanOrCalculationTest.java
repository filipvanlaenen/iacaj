package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanOrCalculation</code> class.
 */
public class BooleanOrCalculationTest {
    /**
     * Verifies that two trues are resolved to true.
     */
    @Test
    public void shouldResolveTwoTruesToTrue() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that two falses are resolved to false.
     */
    @Test
    public void shouldResolveTwoFalsesToFalse() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that and with false is resolved to equality.
     */
    @Test
    public void shouldResolveOrWithFalseToEquality() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that and with true is resolved to true.
     */
    @Test
    public void shouldResolveOrWithTrueToTrue() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that or with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveOrWithTwoOpenOperands() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("v1 ∨ v2", resolved.toString());
    }

    /**
     * Verifies that deepClone returns an object with the same content.
     */
    @Test
    public void deepCloneShouldReturnCalculationWithTheSameContent() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        assertEquals("v1 ∨ v2", calculation.deepClone().toString());
    }

    /**
     * Verifies that if the result of resolve is a BooleanEquation, the resulting
     * BooleanEquation should be resolved too.
     */
    @Test
    public void shouldResolveResultingBooleanEquationIfOnlyOneOperandRemainsAfterResolution() {
        BooleanOrCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("i1"), resolved);
    }

    /**
     * Verifies that operands are expanded as part of resolving the calculation.
     */
    @Test
    public void resolveShouldExpandOperands() {
        BooleanCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ∨ v2", resolved.toString());
    }
}
