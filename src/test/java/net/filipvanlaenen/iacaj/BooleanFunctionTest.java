package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanFunction</code> class.
 */
public class BooleanFunctionTest {
    /**
     * Creates a rich Boolean function, i.e. a Boolean function with all the
     * features needed for unit testing.
     *
     * @return A rich Boolean function.
     */
    private BooleanFunction createRichBooleanFunction() {
        String[] content = new String[] {"# A comment", "", "i1 = True", "i2 = False", "i4 = i3", "i6 = ¬i5",
                "v1 = i3 ∧ i5 ∧ ¬i7", "v2 = i3 ∨ i5 ∨ ¬i7", "v3 = i3 ⊻ i5", "v4 = ¬i3 ⊻ i5", "v5 = i3 ∧ v2",
                "v6 = i3 ∨ v1", "v7 = i3 ⊻ v1", "o1 = i3 ⊻ v6", "o2 = i3 ⊻ v7"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        return booleanFunction;
    }

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
     * Verifies that parsing a simple AND expression results in a Boolean function
     * with two input parameters.
     */
    @Test
    public void shouldParseSimpleAndExpressionWithTwoInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> expected = createInputParameterSet("i1", "i2");
        assertEquals(expected, booleanFunction.getInputParameters());
    }

    /**
     * Verifies that parsing a simple AND expression results in a Boolean function
     * with three input parameters.
     */
    @Test
    public void shouldParseSimpleAndExpressionWithThreeInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2 ∧ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> expected = createInputParameterSet("i1", "i2", "i3");
        assertEquals(expected, booleanFunction.getInputParameters());
    }

    /**
     * Verifies that parsing two simple AND expressions joins the input parameters
     * correctly.
     */
    @Test
    public void shouldParseTwoSimpleAndExpressionWithThreeInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2", "o2 = i2 ∧ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> expected = createInputParameterSet("i1", "i2", "i3");
        assertEquals(expected, booleanFunction.getInputParameters());
    }

    /**
     * Verifies that parsing a rich Boolean function recognizes the input parameters
     * correctly.
     */
    @Test
    public void parsingRichFunctionShouldRecognizeTheInputParameters() {
        BooleanFunction booleanFunction = createRichBooleanFunction();
        Set<InputParameter> expected = createInputParameterSet("i3", "i5", "i7");
        assertEquals(expected, booleanFunction.getInputParameters());
    }

    /**
     * Verifies export of a Boolean function to a string.
     */
    @Test
    public void shouldExportARichBooleanFunctionCorrectly() {
        BooleanFunction booleanFunction = createRichBooleanFunction();
        StringBuilder sb = new StringBuilder();
        sb.append("i1 = True\n");
        sb.append("i2 = False\n");
        sb.append("i4 = i3\n");
        sb.append("i6 = ¬i5\n");
        sb.append("v1 = i3 ∧ i5 ∧ ¬i7\n");
        sb.append("v2 = i3 ∨ i5 ∨ ¬i7\n");
        sb.append("v3 = i3 ⊻ i5\n");
        sb.append("v4 = ¬i3 ⊻ i5\n");
        sb.append("v5 = i3 ∧ v2\n");
        sb.append("v6 = i3 ∨ v1\n");
        sb.append("v7 = i3 ⊻ v1\n");
        sb.append("o1 = i3 ⊻ v6\n");
        sb.append("o2 = i3 ⊻ v7");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }
}
