package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanCalculation</code> class.
 */
public class BooleanCalculationTest {
    /**
     * Boolean calculation v1 XOR i1.
     */
    private static final BooleanCalculation BOOLEAN_CALCULATION_I1_XOR_V1 = new BooleanXorCalculation("v1 ⊻ i1");

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

    /**
     * Verifies that it can extract the internal variables from a calculation.
     */
    @Test
    public void shouldExtractTheInternalVariables() {
        List<InternalVariable> expected = new ArrayList<InternalVariable>();
        expected.add(InternalVariable.get("v1"));
        assertEquals(expected, BOOLEAN_CALCULATION_I1_XOR_V1.getInternalVariables());
    }

    /**
     * Verifies that it can extract the input parameters from a calculation.
     */
    @Test
    public void shouldExtractTheInputParameters() {
        List<InputParameter> expected = new ArrayList<InputParameter>();
        expected.add(InputParameter.get("i1"));
        assertEquals(expected, BOOLEAN_CALCULATION_I1_XOR_V1.getInputParameters());
    }

    /**
     * Verifies that isFalse returns false.
     */
    @Test
    public void isFalseShouldReturnFalse() {
        assertFalse(BOOLEAN_CALCULATION_I1_XOR_V1.isFalse());
    }

    /**
     * Verifies that isTrue returns false.
     */
    @Test
    public void isTrueShouldReturnFalse() {
        assertFalse(BOOLEAN_CALCULATION_I1_XOR_V1.isTrue());
    }

    /**
     * Verifies export to a Java string.
     */
    @Test
    public void shouldExportToJavaCorrectly() {
        assertEquals("i1 ^ v1", BOOLEAN_CALCULATION_I1_XOR_V1.toJavaString());
    }
}
