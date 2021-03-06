package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanOperation</code> class.
 */
public class BooleanOperationTest {
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
     * Verifies that a True expression is exported correctly.
     */
    @Test
    public void shouldExportTrueExpression() {
        BooleanOperation operation = new BooleanOperation("v1", "True");
        assertEquals("v1 = True", operation.toString());
    }

    /**
     * Verifies that a True expression is exported correctly to a Java string.
     */
    @Test
    public void shouldExportTrueExpressionToJava() {
        BooleanOperation operation = new BooleanOperation("v1", "True");
        assertEquals("boolean v1 = true;", operation.toJavaString());
    }

    /**
     * Verifies that a False expression is exported correctly.
     */
    @Test
    public void shouldExportFalseExpression() {
        BooleanOperation operation = new BooleanOperation("v1", "False");
        assertEquals("v1 = False", operation.toString());
    }

    /**
     * Verifies that a False expression is exported correctly to a Java string.
     */
    @Test
    public void shouldExportFalseExpressionToJava() {
        BooleanOperation operation = new BooleanOperation("v1", "False");
        assertEquals("boolean v1 = false;", operation.toJavaString());
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
     * Verifies that a simple AND expression with two input parameters is exported
     * correctly to a Java string.
     */
    @Test
    public void shouldExportSimpleAndExpressionWithTwoInputParametersToJava() {
        BooleanOperation operation = new BooleanOperation("v1", "i1 ∧ i2");
        assertEquals("boolean v1 = i1 & i2;", operation.toJavaString());
    }

    /**
     * Verifies the export of a simple expression to a Java string is done
     * correctly.
     */
    @Test
    public void shouldExportAnExpressionWithANegationToJava() {
        BooleanOperation operation = new BooleanOperation("v1", "i1 ∧ ¬i2");
        assertEquals("boolean v1 = i1 & !i2;", operation.toJavaString());
    }

    /**
     * Verifies that a simple AND expression with two input parameters is exported
     * correctly, with correct sorting.
     */
    @Test
    public void shouldExportSortedSimpleAndExpressionWithTwoInputParameters() {
        BooleanOperation operation = new BooleanOperation("v1", "i10 ∧ i2");
        assertEquals("v1 = i2 ∧ i10", operation.toString());
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

    /**
     * Verifies that a simple AND expression with two internal variables is exported
     * correctly.
     */
    @Test
    public void shouldExportSimpleAndExpressionWithTwoVariables() {
        BooleanOperation operation = new BooleanOperation("v3", "v1 ∧ v2");
        assertEquals("v3 = v1 ∧ v2", operation.toString());
    }

    /**
     * Verifies that a simple AND expression with an input parameter and a negated
     * variable is exported correctly.
     */
    @Test
    public void shouldExportSimpleAndExpressionWithInputParameterAndNegatedVariable() {
        BooleanOperation operation = new BooleanOperation("v2", "i1 ∧ ¬v1");
        assertEquals("v2 = i1 ∧ ¬v1", operation.toString());
    }

    /**
     * Verifies that an XOR expression with the same variable twice directly and
     * twice negated is exported correctly.
     */
    @Test
    public void shouldExportMultipleOccurrencesOfVariableAndNegations() {
        BooleanOperation operation = new BooleanOperation("v2", "v1 ⊻ v1 ⊻ ¬v1 ⊻ ¬v1");
        assertEquals("v2 = v1 ⊻ v1 ⊻ ¬v1 ⊻ ¬v1", operation.toString());
    }

    /**
     * Verifies that a Boolean operation can detect that it doesn't represent an
     * output parameter.
     */
    @Test
    public void shouldDetectInternalVariable() {
        BooleanOperation operation = new BooleanOperation("v1", "i1 ∧ v2");
        assertFalse(operation.isOutputParameter());
    }

    /**
     * Verifies that a Boolean operation can detect that it represents an output
     * parameter.
     */
    @Test
    public void shouldDetectOutputParameter() {
        BooleanOperation operation = new BooleanOperation("o1", "i1 ∧ v1");
        assertTrue(operation.isOutputParameter());
    }
}
