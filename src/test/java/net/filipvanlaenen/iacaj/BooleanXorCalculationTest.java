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
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that two falses are resolved to false.
     */
    @Test
    public void shouldResolveTwoFalsesToFalse() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that true and false are resolved to true.
     */
    @Test
    public void shouldResolveTrueAndFalseToTrue() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that xor with false is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithFalseToEquality() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that xor with true is resolved to negation.
     */
    @Test
    public void shouldResolveXorWithTrueToNegation() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2", true), resolved);
    }

    /**
     * Verifies that xor with not false is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithNotFalseToNegation() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2", true), resolved);
    }

    /**
     * Verifies that xor with not true is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithNotTrueToEquality() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that xor with two identical operands is resolved to negation.
     */
    @Test
    public void shouldResolveXorWithTwoIdenticalOperandsToNegation() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("¬v2"), resolved);
    }

    /**
     * Verifies that xor with two opposed operands is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithTwoOpposedOperandsToNegation() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ ¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that xor between two opposed operands is resolved to true.
     */
    @Test
    public void shouldResolveXorBetweenTwoOpposedOperandsToTrue() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ ¬v1");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that xor between two identical operands is resolved to false.
     */
    @Test
    public void shouldResolveXorBetweenTwoIdenticalOperandsToFalse() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that xor with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveXorWithTwoOpenOperands() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("v1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is an input parameter constrained
     * to be equal to another one.
     */
    @Test
    public void shouldResolveAnInputParameterConstrainedToEquality() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is a negated input parameter
     * constrained to be equal to another one.
     */
    @Test
    public void shouldResolveANegatedInputParameterConstrainedToEquality() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("¬i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is an input parameter constrained
     * to be opposite to another one.
     */
    @Test
    public void shouldResolveAnInputParameterConstrainedToOpposition() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is a negated input parameter
     * constrained to be opposite to another one.
     */
    @Test
    public void shouldResolveANegatedInputParameterConstrainedToOpposition() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("¬i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that deepClone returns an object with the same content.
     */
    @Test
    public void deepCloneShouldReturnCalculationWithTheSameContent() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        assertEquals("v1 ⊻ v2", calculation.deepClone().toString());
    }

    /**
     * Verifies that if the result of resolve is a BooleanEquation, the resulting
     * BooleanEquation should be resolved too.
     */
    @Test
    public void shouldResolveResultingBooleanEquationIfOnlyOneOperandRemainsAfterResolution() {
        BooleanXorCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("i1"), resolved);
    }
}
