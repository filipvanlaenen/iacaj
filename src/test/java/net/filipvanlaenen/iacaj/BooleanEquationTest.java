package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>BooleanEquation</code> class.
 */
public class BooleanEquationTest {
    /**
     * Boolean equation with the input parameter i1.
     */
    private static final BooleanEquation BOOLEAN_EQUATION_I1 = new BooleanEquation("i1");
    /**
     * Boolean equation with the internal variable v1.
     */
    private static final BooleanEquation BOOLEAN_EQUATION_V1 = new BooleanEquation("v1");
    /**
     * Boolean equation with the negation of the internal variable v1.
     */
    private static final BooleanEquation BOOLEAN_EQUATION_NOT_V1 = new BooleanEquation("¬v1");
    /**
     * Boolean equation with the internal variable v2.
     */
    private static final BooleanEquation BOOLEAN_EQUATION_V2 = new BooleanEquation("v2");

    /**
     * Verifies that equation to False is resolved to False.
     */
    @Test
    public void shouldResolveEquationToFalseToFalse() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_V1.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that equation to True is resolved to True.
     */
    @Test
    public void shouldResolveEquationToTrueToTrue() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_V1.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that opposition to False is resolved to True.
     */
    @Test
    public void shouldResolveOppositionToFalseToTrue() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_NOT_V1.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that opposition to True is resolved to False.
     */
    @Test
    public void shouldResolveOppositionToTrueToFalse() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_NOT_V1.resolve(booleanFunction);
        assertEquals(BooleanConstant.FALSE, resolved);
    }

    /**
     * Verifies that equation to an open operand is not resolved.
     */
    @Test
    public void shouldNotResolveEqualityToOpenOperand() {
        BooleanFunction booleanFunction = BooleanFunction.parse();
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_V1.resolve(booleanFunction);
        assertEquals("v1", resolved.toString());
    }

    /**
     * Verifies that equation to another equation propagates the operand.
     */
    @Test
    public void equationToEquationShouldPropagateOperand() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_V1.resolve(booleanFunction);
        assertEquals("i1", resolved.toString());
    }

    /**
     * Verifies that equation to opposition propagates the operand with negation.
     */
    @Test
    public void equationToOppositionShouldPropagateOperandNegated() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = ¬i1");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_V1.resolve(booleanFunction);
        assertEquals("¬i1", resolved.toString());
    }

    /**
     * Verifies that opposition to equation propagates the operand with negation.
     */
    @Test
    public void oppositionToEquationShouldPropagateOperandNegated() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = i1");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_NOT_V1.resolve(booleanFunction);
        assertEquals("¬i1", resolved.toString());
    }

    /**
     * Verifies that opposition to another opposition propagates the operand.
     */
    @Test
    public void oppositionToOppositionShouldPropagateOperand() {
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = ¬i1");
        BooleanRightHandSide resolved = BOOLEAN_EQUATION_NOT_V1.resolve(booleanFunction);
        assertEquals("i1", resolved.toString());
    }

    /**
     * Verifies that deepClone returns an equation with the same content.
     */
    @Test
    public void deepCloneShouldReturnEquationWithSameContent() {
        assertEquals("v1", BOOLEAN_EQUATION_V1.deepClone().toString());
    }

    /**
     * Verifies that deepClone does not return the same object.
     */
    @Test
    public void deepCloneShouldNotReturnSameObject() {
        assertNotSame(BOOLEAN_EQUATION_V1, BOOLEAN_EQUATION_V1.deepClone());
    }

    /**
     * Verifies that a Boolean equation is equal to itself.
     */
    @Test
    public void booleanEquationShouldBeEqualToItself() {
        assertTrue(BOOLEAN_EQUATION_V1.equals(BOOLEAN_EQUATION_V1));
    }

    /**
     * Verifies that a Boolean equation is not equal to <code>null</code>.
     */
    @Test
    public void booleanEquationShouldNotBeEqualToNull() {
        assertFalse(BOOLEAN_EQUATION_V1.equals(null));
    }

    /**
     * Verifies that a Boolean equation is not equal to an object of another type, like e.g. an empty string.
     */
    @Test
    public void booleanEquationShouldNotBeEqualToEmptyString() {
        assertFalse(BOOLEAN_EQUATION_V1.equals(""));
    }

    /**
     * Verifies that a Boolean equation is equal to another Boolean equation constructed in the same manner.
     */
    @Test
    public void booleanEquationShouldBeEqualToBooleanEquationWithSameParameters() {
        assertTrue(BOOLEAN_EQUATION_V1.equals(new BooleanEquation("v1")));
    }

    /**
     * Verifies that a Boolean equation is not equal to its negation.
     */
    @Test
    public void booleanEquationShouldNotBeEqualToNegatedBooleanEquation() {
        assertFalse(BOOLEAN_EQUATION_V1.equals(BOOLEAN_EQUATION_NOT_V1));
    }

    /**
     * Verifies that a Boolean equation is not equal to a Boolean equation with another name.
     */
    @Test
    public void booleanEquationShouldNotBeEqualToBooleanEquationWithOtherName() {
        assertFalse(BOOLEAN_EQUATION_V1.equals(BOOLEAN_EQUATION_V2));
    }

    /**
     * Verifies that two Boolean equations that are equal have the same hash code.
     */
    @Test
    public void booleanEquationShouldHaveSameHashcodeAsBooleanEquationWithSameParameters() {
        assertEquals(BOOLEAN_EQUATION_V1.hashCode(), new BooleanEquation("v1").hashCode());
    }

    /**
     * Verifies that a Boolean equation doesn't have the same hash code as its negation.
     */
    @Test
    public void booleanEquationShouldNotHaveSameHashCodeAsNegatedBooleanEquation() {
        assertNotEquals(BOOLEAN_EQUATION_V1.hashCode(), BOOLEAN_EQUATION_NOT_V1.hashCode());
    }

    /**
     * Verifies that a Boolean equation doesn't have the same hash code as a Boolean equation with another name.
     */
    @Test
    public void booleanEquationShouldNotHaveSameHashCodeAsBooleanEquationWithOtherName() {
        assertNotEquals(BOOLEAN_EQUATION_V1.hashCode(), BOOLEAN_EQUATION_V2.hashCode());
    }

    /**
     * Verifies that a Boolean equation referring to an internal variable returns an empty list for the input
     * parameters.
     */
    @Test
    public void inputParametersListIsEmptyWhenReferringToInternalVariable() {
        assertTrue(BOOLEAN_EQUATION_V1.getInputParameters().isEmpty());
    }

    /**
     * Verifies that getInputParameters on a Boolean equation referring to an input parameter returns a list with that
     * input parameter.
     */
    @Test
    public void inputParametersListContainsTheInputParameterWhenReferringToInputParameter() {
        OrderedCollection<InputParameter> expected = OrderedCollection.of(InputParameter.get("i1"));
        assertTrue(expected.containsSame(BOOLEAN_EQUATION_I1.getInputParameters()));
    }

    /**
     * Verifies that a Boolean equation referring to an input parameter returns an empty list for the internal
     * variables.
     */
    @Test
    public void internalVariablesEmptyWhenReferringToInputParameter() {
        assertTrue(BOOLEAN_EQUATION_I1.getInternalVariables().isEmpty());
    }

    /**
     * Verifies that getInternalVariables on a Boolean equation referring to an internal variable returns a list with
     * that internal variable.
     */
    @Test
    public void internalVariablesListContainsTheInternalVariableWhenReferringToInternalVariable() {
        List<InternalVariable> expected = new ArrayList<InternalVariable>();
        expected.add(InternalVariable.get("v1"));
        assertEquals(expected, BOOLEAN_EQUATION_V1.getInternalVariables());
    }

    /**
     * Verifies that the operator for a Boolean equation is <code>null</code>.
     */
    @Test
    public void operatorShouldBeNull() {
        assertNull(BOOLEAN_EQUATION_V1.getOperator());
    }

    /**
     * Verifies that a Boolean equation is not false.
     */
    @Test
    public void isFalseShouldReturnFalse() {
        assertFalse(BOOLEAN_EQUATION_V1.isFalse());
    }

    /**
     * Verifies that a Boolean equation is not true.
     */
    @Test
    public void isTrueShouldReturnFalse() {
        assertFalse(BOOLEAN_EQUATION_V1.isTrue());
    }

    /**
     * Verifies that a Boolean equation is exported to Java correctly.
     */
    @Test
    public void shouldExportToJavaCorrectly() {
        assertEquals("v1", BOOLEAN_EQUATION_V1.toJavaString());
    }

    /**
     * Verifies that a negated Boolean equation is exported to Java correctly.
     */
    @Test
    public void shouldExportNegationToJavaCorrectly() {
        assertEquals("!v1", BOOLEAN_EQUATION_NOT_V1.toJavaString());
    }

    /**
     * Verifies that a Boolean equation is exported to a string correctly.
     */
    @Test
    public void shouldExportToStringCorrectly() {
        assertEquals("v1", BOOLEAN_EQUATION_V1.toString());
    }

    /**
     * Verifies that a negated Boolean equation is exported to a string correctly.
     */
    @Test
    public void shouldExportNegationToStringCorrectly() {
        assertEquals("¬v1", BOOLEAN_EQUATION_NOT_V1.toString());
    }
}
