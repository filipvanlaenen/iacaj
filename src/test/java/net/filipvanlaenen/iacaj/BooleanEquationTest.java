package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

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
}
