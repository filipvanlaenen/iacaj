package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanAndCalculation</code> class.
 */
public class BooleanAndCalculationTest {
    /**
     * Verifies that two trues are resolved to true.
     */
    @Test
    public void shouldResolveTwoTruesToTrue() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that two falses are resolved to false.
     */
    @Test
    public void shouldResolveTwoFalsesToFalse() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that and with false is resolved to false.
     */
    @Test
    public void shouldResolveAndWithFalseToFalse() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that and with not true is resolved to false.
     */
    @Test
    public void shouldResolveAndWithNotTrueToFalse() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("¬v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that and with true is resolved to equality.
     */
    @Test
    public void shouldResolveAndWithTrueToEquality() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that and with not false is resolved to equality.
     */
    @Test
    public void shouldResolveAndWithNotFalseToEquality() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("¬v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that and with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveAndWithTwoOpenOperands() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("v1 ∧ v2", resolved.toString());
    }

    /**
     * Verifies that deepClone returns an object with the same content.
     */
    @Test
    public void deepCloneShouldReturnCalculationWithTheSameContent() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        assertEquals("v1 ∧ v2", calculation.deepClone().toString());
    }

    /**
     * Verifies that if the result of resolve is a BooleanEquation, the resulting
     * BooleanEquation should be resolved too.
     */
    @Test
    public void shouldResolveResultingBooleanEquationIfOnlyOneOperandRemainsAfterResolution() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True", "v2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("i1"), resolved);
    }

    /**
     * Verifies that operands are expanded as part of resolving the calculation.
     */
    @Test
    public void resolveShouldExpandOperands() {
        BooleanAndCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ∧ v2", resolved.toString());
    }
}
