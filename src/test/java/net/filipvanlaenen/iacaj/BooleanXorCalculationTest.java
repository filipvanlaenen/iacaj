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
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that two falses are resolved to false.
     */
    @Test
    public void shouldResolveTwoFalsesToFalse() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that true and false are resolved to true.
     */
    @Test
    public void shouldResolveTrueAndFalseToTrue() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that xor with false is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithFalseToEquality() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that xor with true is resolved to negation.
     */
    @Test
    public void shouldResolveXorWithTrueToNegation() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2", true), resolved);
    }

    /**
     * Verifies that xor with not false is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithNotFalseToNegation() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2", true), resolved);
    }

    /**
     * Verifies that xor with not true is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithNotTrueToEquality() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that xor with two identical operands is resolved to negation.
     */
    @Test
    public void shouldResolveXorWithTwoIdenticalOperandsToNegation() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("¬v2"), resolved);
    }

    /**
     * Verifies that xor with two opposed operands is resolved to equality.
     */
    @Test
    public void shouldResolveXorWithTwoOpposedOperandsToNegation() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ ¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("v2"), resolved);
    }

    /**
     * Verifies that xor between two opposed operands is resolved to true.
     */
    @Test
    public void shouldResolveXorBetweenTwoOpposedOperandsToTrue() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ ¬v1");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that xor between two identical operands is resolved to false.
     */
    @Test
    public void shouldResolveXorBetweenTwoIdenticalOperandsToFalse() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that xor between many identical and opposed operands is resolved correctly.
     */
    @Test
    public void shouldResolveXorBetweenManyIdenticalAndOpposedOperandsCorrectly() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2 ⊻ v2 ⊻ ¬v3 ⊻ ¬v1 ⊻ v2 ⊻ ¬v2 ⊻ v3");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that xor with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveXorWithTwoOpenOperands() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("v1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that deepClone returns an object with the same content.
     */
    @Test
    public void deepCloneShouldReturnCalculationWithTheSameContent() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        assertEquals("v1 ⊻ v2", calculation.deepClone().toString());
    }

    /**
     * Verifies that if the result of resolve is a BooleanEquation, the resulting BooleanEquation should be resolved
     * too.
     */
    @Test
    public void shouldResolveResultingBooleanEquationIfOnlyOneOperandRemainsAfterResolution() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False", "v2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals(new BooleanEquation("i1"), resolved);
    }

    /**
     * Verifies that operands are expanded as part of resolving the calculation.
     */
    @Test
    public void resolveShouldExpandOperands() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v2", resolved.toString());
    }
}
