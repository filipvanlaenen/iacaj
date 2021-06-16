package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanOperation</code> class.
 */
public class BooleanOperationTest {
    /**
     * A Boolean operation setting v2 to i1 and v1.
     */
    private static final BooleanOperation BOOLEAN_OPERATION_I1_AND_V1 = new BooleanOperation("v2", "i1 ∧ v1");
    /**
     * A Boolean operation setting v1 to false.
     */
    private static final BooleanOperation BOOLEAN_OPERATION_V1_FALSE = new BooleanOperation("v1", "False");
    /**
     * A Boolean operation setting v1 to true.
     */
    private static final BooleanOperation BOOLEAN_OPERATION_V1_TRUE = new BooleanOperation("v1", "True");

    /**
     * Verifies that the name can be retrieved.
     */
    @Test
    public void shouldGetNameCorrectly() {
        assertEquals("v1", BOOLEAN_OPERATION_V1_FALSE.getName());
    }

    /**
     * Verifies that the number can be retrieved.
     */
    @Test
    public void shouldGetNumberCorrectly() {
        assertEquals(1, BOOLEAN_OPERATION_V1_FALSE.getNumber());
    }

    /**
     * Verifies that the operator can be retrieved.
     */
    @Test
    public void shouldGetOperatorCorrectly() {
        assertEquals(BooleanOperator.And, BOOLEAN_OPERATION_I1_AND_V1.getOperator());
    }

    /**
     * Verifies that the right hand side is returned.
     */
    @Test
    public void shouldReturnRightHandSide() {
        assertEquals("i1 ∧ v1", BOOLEAN_OPERATION_I1_AND_V1.getRightHandSide().toString());
    }

    /**
     * Verifies that getInternalVariables extracts the input parameter and not the
     * internal variable.
     */
    @Test
    public void shouldExtractInputParameter() {
        List<InputParameter> expected = new ArrayList<InputParameter>();
        expected.add(InputParameter.get("i1"));
        assertEquals(expected, BOOLEAN_OPERATION_I1_AND_V1.getInputParameters());
    }

    /**
     * Verifies that getInternalVariables extracts the internal variable and not the
     * input parameter.
     */
    @Test
    public void shouldExtractInternalVariable() {
        List<InternalVariable> expected = new ArrayList<InternalVariable>();
        expected.add(InternalVariable.get("v1"));
        assertEquals(expected, BOOLEAN_OPERATION_I1_AND_V1.getInternalVariables());
    }

    /**
     * Verifies that a True expression is exported correctly.
     */
    @Test
    public void shouldExportTrueExpression() {
        assertEquals("v1 = True", BOOLEAN_OPERATION_V1_TRUE.toString());
    }

    /**
     * Verifies that a True expression is exported correctly to a Java string.
     */
    @Test
    public void shouldExportTrueExpressionToJava() {
        assertEquals("boolean v1 = true;", BOOLEAN_OPERATION_V1_TRUE.toJavaString());
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

    /**
     * Verifies that isTrue returns true on a Boolean operation that is true.
     */
    @Test
    public void isTrueShouldReturnTrueForAnOperationThatIsTrue() {
        assertTrue(BOOLEAN_OPERATION_V1_TRUE.isTrue());
    }

    /**
     * Verifies that isTrue returns false on a Boolean operation that is false.
     */
    @Test
    public void isTrueShouldReturnFalseForAnOperationThatIsFalse() {
        assertFalse(BOOLEAN_OPERATION_V1_FALSE.isTrue());
    }

    /**
     * Verifies that isFalse returns false on a Boolean operation that is true.
     */
    @Test
    public void isFalseShouldReturnFalseForAnOperationThatIsTrue() {
        assertFalse(BOOLEAN_OPERATION_V1_TRUE.isFalse());
    }

    /**
     * Verifies that isFalse returns true on a Boolean operation that is false.
     */
    @Test
    public void isFalseShouldReturnTrueForAnOperationThatIsFalse() {
        assertTrue(BOOLEAN_OPERATION_V1_FALSE.isFalse());
    }

    /**
     * Verifies that <code>deepClone</code> doesn't return the same object.
     */
    @Test
    public void deepCloneShouldNotReturnSameObject() {
        assertNotSame(BOOLEAN_OPERATION_V1_FALSE, BOOLEAN_OPERATION_V1_FALSE.deepClone());
    }

    /**
     * Verifies that <code>deepClone</code> returns an object that produces the same
     * string.
     */
    @Test
    public void deepCloneShouldReturnAnObjectWithTheSameToStringResult() {
        assertEquals(BOOLEAN_OPERATION_I1_AND_V1.toString(), BOOLEAN_OPERATION_I1_AND_V1.deepClone().toString());
    }
}
