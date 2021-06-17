package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanCalculation</code> class.
 */
public class BooleanCalculationTest {
    /**
     * Verifies that xor replaces an operand which is an input parameter constrained
     * to be equal to another one.
     */
    @Test
    public void shouldResolveAnInputParameterConstrainedToEquality() {
        BooleanCalculation calculation = new BooleanXorCalculation("i2 ⊻ v1");
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
        BooleanCalculation calculation = new BooleanXorCalculation("¬i2 ⊻ v1");
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
        BooleanCalculation calculation = new BooleanXorCalculation("i2 ⊻ v1");
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
        BooleanCalculation calculation = new BooleanXorCalculation("¬i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that operands that refer to Boolean equations are replaced with the
     * right hand side of the Boolean equation.
     */
    @Test
    public void shouldResolveOperandsReferringToBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that operands that refer to negated Boolean equations are replaced
     * with the negation of the right hand side of the Boolean equation.
     */
    @Test
    public void shouldResolveOperandsReferringToNegatedBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that negated operands that refer to Boolean equations are replaced
     * with the negation of the right hand side of the Boolean equation.
     */
    @Test
    public void shouldResolveNegatedOperandsReferringToBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that negated operands that refer to negated Boolean equations are
     * replaced with the negation of the right hand side of the Boolean equation.
     */
    @Test
    public void shouldResolveNegatedOperandsReferringToNegatedBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that input parameters are sorted correctly when exporting a Boolean
     * calculation to a string.
     */
    @Test
    public void shouldSortInputParametersWhenExportingToAString() {
        assertEquals("i1 ⊻ i2 ⊻ i3 ⊻ i4", new BooleanXorCalculation("i4 ⊻ i3 ⊻ i2 ⊻ i1").toString());
    }

    /**
     * Verifies that internal variables are sorted correctly when exporting a
     * Boolean calculation to a string.
     */
    @Test
    public void shouldSortInternalVariablesWhenExportingToAString() {
        assertEquals("v1 ⊻ v2 ⊻ v3 ⊻ v4", new BooleanXorCalculation("v4 ⊻ v3 ⊻ v2 ⊻ v1").toString());
    }

    /**
     * Verifies that input parameters are sorted before internal variables when
     * exporting a Boolean calculation to a string.
     */
    @Test
    public void shouldSortInputParametersBeforeInternalVariablesWhenExportingToAString() {
        assertEquals("i4 ⊻ i5 ⊻ i6 ⊻ v1 ⊻ v2 ⊻ v3",
                new BooleanXorCalculation("v1 ⊻ v2 ⊻ v3 ⊻ i4 ⊻ i5 ⊻ i6").toString());
    }
}
