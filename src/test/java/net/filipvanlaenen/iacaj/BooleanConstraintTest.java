package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanConstraint</code> class.
 */
public class BooleanConstraintTest {
    /**
     * Creates a set with input parameters with the names provided.
     *
     * @param names The names of the input parameters to be added to the set.
     * @return A set with input parameters having the provided names.
     */
    private List<InputParameter> createInputParameterSet(final String... names) {
        List<InputParameter> result = new ArrayList<InputParameter>();
        for (String name : names) {
            result.add(InputParameter.get(name));
        }
        return result;
    }

    /**
     * Verifies that True is not registered as an input parameter.
     */
    @Test
    public void trueIsNotAnInputParameter() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "True");
        assertTrue(constraint.getInputParameters().isEmpty());
    }

    /**
     * Verifies that False is not registered as an input parameter.
     */
    @Test
    public void falseIsNotAnInputParameter() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "False");
        assertTrue(constraint.getInputParameters().isEmpty());
    }

    /**
     * Verifies that equality to another input parameter is registered as an input
     * parameter.
     */
    @Test
    public void equalityReturnsAnInputParameter() {
        BooleanConstraint constraint = BooleanConstraint.parse("i2", "i1");
        assertEquals(createInputParameterSet("i1"), constraint.getInputParameters());
    }

    /**
     * Verifies that negation to another input parameter is registered as an input
     * parameter.
     */
    @Test
    public void negationReturnsAnInputParameter() {
        BooleanConstraint constraint = BooleanConstraint.parse("i2", "¬i1");
        assertEquals(createInputParameterSet("i1"), constraint.getInputParameters());
    }

    /**
     * A True constraint is exported correctly.
     */
    @Test
    public void trueIsExportedCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "True");
        assertEquals("i1 = True", constraint.toString());
    }

    /**
     * A False constraint is exported correctly.
     */
    @Test
    public void falseIsExportedCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "False");
        assertEquals("i1 = False", constraint.toString());
    }

    /**
     * An Equality constraint is exported correctly.
     */
    @Test
    public void equalityIsExportedCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i2", "i1");
        assertEquals("i2 = i1", constraint.toString());
    }

    /**
     * An Negation constraint is exported correctly.
     */
    @Test
    public void negationIsExportedCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i2", "¬i1");
        assertEquals("i2 = ¬i1", constraint.toString());
    }
}
