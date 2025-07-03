package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanCalculation.OperandComparator;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>BooleanCalculation</code> class.
 */
public class BooleanCalculationTest {
    /**
     * Boolean calculation v1 XOR i1.
     */
    private static final BooleanCalculation BOOLEAN_CALCULATION_I1_XOR_V1 = new BooleanXorCalculation("v1 ⊻ i1");

    /**
     * Verifies that xor replaces an operand which is an input parameter constrained to be equal to another one.
     */
    @Test
    public void shouldResolveAnInputParameterConstrainedToEquality() {
        BooleanCalculation calculation = new BooleanXorCalculation("i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is a negated input parameter constrained to be equal to another one.
     */
    @Test
    public void shouldResolveANegatedInputParameterConstrainedToEquality() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is an input parameter constrained to be opposite to another one.
     */
    @Test
    public void shouldResolveAnInputParameterConstrainedToOpposition() {
        BooleanCalculation calculation = new BooleanXorCalculation("i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that xor replaces an operand which is a negated input parameter constrained to be opposite to another
     * one.
     */
    @Test
    public void shouldResolveANegatedInputParameterConstrainedToOpposition() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬i2 ⊻ v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("i2 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v1", resolved.toString());
    }

    /**
     * Verifies that operands that refer to Boolean equations are replaced with the right hand side of the Boolean
     * equation.
     */
    @Test
    public void shouldResolveOperandsReferringToBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that operands that refer to negated Boolean equations are replaced with the negation of the right hand
     * side of the Boolean equation.
     */
    @Test
    public void shouldResolveOperandsReferringToNegatedBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that operands that refer to a calculation with the same operator are replaced with the referred
     * calculation.
     */
    @Test
    public void shouldResolveAndOperandsReferringToAndCalculation() {
        BooleanCalculation calculation = new BooleanAndCalculation("v1 ∧ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1 ∧ i2");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ∧ i2 ∧ v2", resolved.toString());
    }

    /**
     * Verifies that operands that refer to a calculation with the same operator are replaced with the referred
     * calculation.
     */
    @Test
    public void shouldResolveOrOperandsReferringToOrCalculation() {
        BooleanCalculation calculation = new BooleanOrCalculation("v1 ∨ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1 ∨ i2");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ∨ i2 ∨ v2", resolved.toString());
    }

    /**
     * Verifies that operands that refer to a calculation with the same operator are replaced with the referred
     * calculation.
     */
    @Test
    public void shouldResolveXorOperandsReferringToXorCalculation() {
        BooleanCalculation calculation = new BooleanXorCalculation("v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1 ⊻ i2");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ i2 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that negated operands that refer to Boolean equations are replaced with the negation of the right hand
     * side of the Boolean equation.
     */
    @Test
    public void shouldResolveNegatedOperandsReferringToBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("¬i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that negated operands that refer to negated Boolean equations are replaced with the negation of the
     * right hand side of the Boolean equation.
     */
    @Test
    public void shouldResolveNegatedOperandsReferringToNegatedBooleanEquations() {
        BooleanCalculation calculation = new BooleanXorCalculation("¬v1 ⊻ v2");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = ¬i1");
        BooleanRightHandSide resolved = calculation.resolve(booleanFunction);
        assertEquals("i1 ⊻ v2", resolved.toString());
    }

    /**
     * Verifies that input parameters are sorted correctly when exporting a Boolean calculation to a string.
     */
    @Test
    public void shouldSortInputParametersWhenExportingToAString() {
        assertEquals("i1 ⊻ i2 ⊻ i3 ⊻ i4", new BooleanXorCalculation("i4 ⊻ i3 ⊻ i2 ⊻ i1").toString());
    }

    /**
     * Verifies that internal variables are sorted correctly when exporting a Boolean calculation to a string.
     */
    @Test
    public void shouldSortInternalVariablesWhenExportingToAString() {
        assertEquals("v1 ⊻ v2 ⊻ v3 ⊻ v4", new BooleanXorCalculation("v4 ⊻ v3 ⊻ v2 ⊻ v1").toString());
    }

    /**
     * Verifies that input parameters are sorted before internal variables when exporting a Boolean calculation to a
     * string.
     */
    @Test
    public void shouldSortInputParametersBeforeInternalVariablesWhenExportingToAString() {
        assertEquals("i4 ⊻ i5 ⊻ i6 ⊻ v1 ⊻ v2 ⊻ v3",
                new BooleanXorCalculation("v1 ⊻ v2 ⊻ v3 ⊻ i4 ⊻ i5 ⊻ i6").toString());
    }

    /**
     * Verifies that input parameters are sorted according to their number.
     */
    @Test
    public void shouldSortInputParameters() {
        OperandComparator comparator = new OperandComparator();
        BooleanOperand i1 = new BooleanOperand("i1");
        BooleanOperand i1Negated = new BooleanOperand("¬i1");
        BooleanOperand i2 = new BooleanOperand("i2");
        assertTrue(comparator.compare(i1, i2) < 0);
        assertTrue(comparator.compare(i1, i1) == 0);
        assertTrue(comparator.compare(i2, i1) > 0);
        assertTrue(comparator.compare(i1, i1Negated) < 0);
        assertTrue(comparator.compare(i1Negated, i1) > 0);
        assertTrue(comparator.compare(i1Negated, i2) < 0);
    }

    /**
     * Verifies that internal parameters are sorted according to their number.
     */
    @Test
    public void shouldSortInternalParameters() {
        OperandComparator comparator = new OperandComparator();
        BooleanOperand v1 = new BooleanOperand("v1");
        BooleanOperand v1Negated = new BooleanOperand("¬v1");
        BooleanOperand v2 = new BooleanOperand("v2");
        assertTrue(comparator.compare(v1, v2) < 0);
        assertTrue(comparator.compare(v1, v1) == 0);
        assertTrue(comparator.compare(v2, v1) > 0);
        assertTrue(comparator.compare(v1, v1Negated) < 0);
        assertTrue(comparator.compare(v1Negated, v1) > 0);
        assertTrue(comparator.compare(v1Negated, v2) < 0);
    }

    /**
     * Verifies that input parameters are sorted before internal parameters.
     */
    @Test
    public void shouldSortInputParametersBeforeInternalParameters() {
        OperandComparator comparator = new OperandComparator();
        BooleanOperand i1 = new BooleanOperand("i1");
        BooleanOperand v1 = new BooleanOperand("v1");
        assertTrue(comparator.compare(i1, v1) < 0);
        assertTrue(comparator.compare(v1, i1) > 0);
    }

    /**
     * Verifies that it can extract the internal variables from a calculation.
     */
    @Test
    public void shouldExtractTheInternalVariables() {
        OrderedCollection<InternalVariable> expected =
                OrderedCollection.<InternalVariable>of(InternalVariable.get("v1"));
        assertTrue(expected.containsSame(BOOLEAN_CALCULATION_I1_XOR_V1.getInternalVariables()));
    }

    /**
     * Verifies that it can extract the input parameters from a calculation.
     */
    @Test
    public void shouldExtractTheInputParameters() {
        OrderedCollection<InputParameter> expected = OrderedCollection.of(InputParameter.get("i1"));
        assertTrue(expected.containsSame(BOOLEAN_CALCULATION_I1_XOR_V1.getInputParameters()));
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
