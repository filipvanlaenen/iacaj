package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanEquation</code> class.
 */
public class BooleanEquationTest {
    private static final BooleanEquation BOOLEAN_EQUATION_V1 = new BooleanEquation("v1");

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
        BooleanEquation equation = new BooleanEquation("¬v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = False");
        BooleanRightHandSide resolved = equation.resolve(booleanFunction);
        assertEquals(BooleanConstant.TRUE, resolved);
    }

    /**
     * Verifies that opposition to True is resolved to False.
     */
    @Test
    public void shouldResolveOppositionToTrueToFalse() {
        BooleanEquation equation = new BooleanEquation("¬v1");
        BooleanFunction booleanFunction = BooleanFunction.parse("v1 = True");
        BooleanRightHandSide resolved = equation.resolve(booleanFunction);
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
     * Verifies that a Boolean equation is not equal to an object of another type,
     * like e.g. an empty string.
     */
    @Test
    public void booleanEquationShouldNotBeEqualToEmptyString() {
        assertFalse(BOOLEAN_EQUATION_V1.equals(""));
    }

    /**
     * Verifies that a Boolean equation is equal to another Boolean equation
     * constructed in the same manner.
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
        assertFalse(BOOLEAN_EQUATION_V1.equals(new BooleanEquation("¬v1")));
    }

    /**
     * Verifies that a Boolean equation is not equal to a Boolean equation with
     * another name.
     */
    @Test
    public void booleanEquationShouldNotBeEqualToBooleanEquationWithOtherName() {
        assertFalse(BOOLEAN_EQUATION_V1.equals(new BooleanEquation("v2")));
    }
}
