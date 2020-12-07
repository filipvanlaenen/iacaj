package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BooleanOperationTest {
    /**
     * Creates a set with input parameters with the names provided.
     *
     * @param names The names of the input parameters to be added to the set.
     * @return A set with input parameters having the provided names.
     */
    private Set<InputParameter> createInputParameterSet(final String... names) {
        Set<InputParameter> result = new HashSet<InputParameter>();
        for (String name : names) {
            result.add(InputParameter.get(name));
        }
        return result;
    }

    /**
     * Verifies that a Boolean operation with to input parameters recognizes both.
     */
    @Test
    public void shouldExtractBothInputParameters() {
        BooleanOperation operation = new BooleanOperation("v1", "i1 ∧ i2");
        assertEquals(createInputParameterSet("i1", "i2"), operation.getInputParameters());
    }

    /**
     * Verifies that a Boolean operation with an input parameter and a variable
     * recognizes only one input parameter.
     */
    @Test
    public void shouldExtractOneInputParameter() {
        BooleanOperation operation = new BooleanOperation("v2", "i1 ∧ v1");
        assertEquals(createInputParameterSet("i1"), operation.getInputParameters());
    }

    /**
     * Verifies that a Boolean operation with a negated input parameter and a
     * variable recognizes only one input parameter.
     */
    @Test
    public void shouldExtractNegatedInputParameter() {
        BooleanOperation operation = new BooleanOperation("v2", "¬i1 ∧ v1");
        assertEquals(createInputParameterSet("i1"), operation.getInputParameters());
    }

    /**
     * Verifies that a simple AND expression with two input parameters is exported
     * correctly.
     */
    @Test
    public void shouldExportSimpleAndExpressionWithTwoInputParameters() {
        BooleanOperation operation = new BooleanOperation("v1", "i1 ∧ i2");
        assertEquals("v1 = i1 ∧ i2", operation.toString());
    }

    /**
     * Verifies that a simple AND expression with an input parameter and a variable
     * is exported correctly.
     */
    @Test
    public void shouldExportSimpleAndExpressionWithInputParameterAndVariable() {
        BooleanOperation operation = new BooleanOperation("v2", "i1 ∧ v1");
        assertEquals("v2 = i1 ∧ v1", operation.toString());
    }
}
