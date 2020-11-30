package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanFunction</code> class.
 */
public class BooleanFunctionTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;

    /**
     * Verifies that parsing a simple AND expression results in a Boolean function
     * with one output parameter.
     */
    @Test
    public void shouldParseSimpleAndExpressionWithOneOutputParameter() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        assertEquals(1, booleanFunction.getNumberOfOutputParameters());
    }

    /**
     * Verifies that parsing a simple AND expression results in a Boolean function
     * with two input parameters.
     */
    @Test
    public void shouldParseSimpleAndExpressionWithTwoInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        assertEquals(2, booleanFunction.getNumberOfInputParameters());
    }

    /**
     * Verifies that parsing a simple AND expression results in a Boolean function
     * with three input parameters.
     */
    @Test
    public void shouldParseSimpleAndExpressionWithThreeInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2 ∧ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        assertEquals(THREE, booleanFunction.getNumberOfInputParameters());
    }
}
