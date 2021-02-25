package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
     * Creates the representation of the rich Boolean function as a string.
     *
     * @return A string representing the rich Boolean function.
     */
    private String createRichBooleanFunctionString() {
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
        return expected;
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
     * correctly, i.e. input parameters occurring in the constraints should not be
     * included.
     */
    @Test
    public void parsingRichFunctionShouldRecognizeTheInputParameters() {
        BooleanFunction booleanFunction = createRichBooleanFunction();
        Set<InputParameter> expected = createInputParameterSet("i3", "i5", "i7");
        assertEquals(expected, booleanFunction.getInputParameters());
    }

    /**
     * Verifies that the input parameters are cleared and recalculated after
     * resolving.
     */
    @Test
    public void resolvingShouldRecalculateTheInputParameters() {
        String[] content = new String[] {"i1 = True", "o1 = i1 ∧ i2", "o2 = i2 ∧ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        Set<InputParameter> expected = createInputParameterSet("i2", "i3");
        assertEquals(expected, booleanFunction.getInputParameters());
    }

    /**
     * Verifies export of a Boolean function to a string.
     */
    @Test
    public void shouldExportARichBooleanFunctionCorrectly() {
        BooleanFunction booleanFunction = createRichBooleanFunction();
        String expected = createRichBooleanFunctionString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies export of a Boolean function to a string after cloning it.
     */
    @Test
    public void shouldExportARichBooleanFunctionCorrectlyAfterCloning() {
        BooleanFunction booleanFunction = new BooleanFunction(createRichBooleanFunction());
        String expected = createRichBooleanFunctionString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies export of a Boolean function to a string after cloning it from
     * another Boolean function together with constraints.
     */
    @Test
    public void shoudlExportARichBooleanFunctionCorrectlyAfterCloningFromAnotherBooleanFunctionAndConstraints() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<BooleanConstraint> constraintsSet = new HashSet<BooleanConstraint>();
        constraintsSet.add(BooleanConstraint.parse("i1", "True"));
        BooleanConstraints constraints = new BooleanConstraints(constraintsSet);
        BooleanFunction constrainedBooleanFunction = new BooleanFunction(booleanFunction, constraints);
        StringBuilder sb = new StringBuilder();
        sb.append("i1 = True\n");
        sb.append("o1 = i1 ∧ i2");
        String expected = sb.toString();
        assertEquals(expected, constrainedBooleanFunction.toString());
    }

    /**
     * Verifies export of a Boolean function to a Java string.
     */
    @Test
    public void shouldExportARichBooleanFunctionToJavaCorrectly() {
        BooleanFunction booleanFunction = createRichBooleanFunction();
        StringBuilder sb = new StringBuilder();
        sb.append("assert i1 == true;\n");
        sb.append("assert i2 == false;\n");
        sb.append("assert i4 == i3;\n");
        sb.append("assert i6 == !i5;\n");
        sb.append("boolean v1 = i3 & i5 & !i7;\n");
        sb.append("boolean v2 = i3 | i5 | !i7;\n");
        sb.append("boolean v3 = i3 ^ i5;\n");
        sb.append("boolean v4 = !i3 ^ i5;\n");
        sb.append("boolean v5 = i3 & v2;\n");
        sb.append("boolean v6 = i3 | v1;\n");
        sb.append("boolean v7 = i3 ^ v1;\n");
        sb.append("boolean o1 = i3 ^ v6;\n");
        sb.append("boolean o2 = i3 ^ v7;");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toJavaString());
    }

    /**
     * Verifies that input parameter 1 is exported before input paramater 2.
     */
    @Test
    public void shouldSortInputParamatersCorrectly() {
        BooleanFunction.BooleanExpressionComparator comparator = new BooleanFunction.BooleanExpressionComparator();
        BooleanExpression expression0 = BooleanConstraint.parse("i1 = True");
        BooleanExpression expression1 = BooleanConstraint.parse("i2 = True");
        assertTrue(comparator.compare(expression0, expression1) < 0);
        assertTrue(comparator.compare(expression1, expression0) > 0);
    }

    /**
     * Verifies that internal variable 1 is exported before internal variable 2.
     */
    @Test
    public void shouldSortInternalVariablesCorrectly() {
        BooleanFunction.BooleanExpressionComparator comparator = new BooleanFunction.BooleanExpressionComparator();
        BooleanExpression expression0 = BooleanConstraint.parse("v1 = True");
        BooleanExpression expression1 = BooleanConstraint.parse("v2 = True");
        assertTrue(comparator.compare(expression0, expression1) < 0);
        assertTrue(comparator.compare(expression1, expression0) > 0);
    }

    /**
     * Verifies that output parameter 1 is exported before output paramater 2.
     */
    @Test
    public void shouldSortOutputParamatersCorrectly() {
        BooleanFunction.BooleanExpressionComparator comparator = new BooleanFunction.BooleanExpressionComparator();
        BooleanExpression expression0 = BooleanConstraint.parse("o1 = True");
        BooleanExpression expression1 = BooleanConstraint.parse("o2 = True");
        assertTrue(comparator.compare(expression0, expression1) < 0);
        assertTrue(comparator.compare(expression1, expression0) > 0);
    }

    /**
     * Verifies that input parameter 2 is exported before internal variable 1.
     */
    @Test
    public void shouldSortInputParamatersBeforeInternalVariables() {
        BooleanFunction.BooleanExpressionComparator comparator = new BooleanFunction.BooleanExpressionComparator();
        BooleanExpression expression0 = BooleanConstraint.parse("i2 = True");
        BooleanExpression expression1 = BooleanConstraint.parse("v1 = True");
        assertTrue(comparator.compare(expression0, expression1) < 0);
        assertTrue(comparator.compare(expression1, expression0) > 0);
    }

    /**
     * Verifies that input parameter 2 is exported before output parameter 1.
     */
    @Test
    public void shouldSortInputParamatersBeforeOutputParameters() {
        BooleanFunction.BooleanExpressionComparator comparator = new BooleanFunction.BooleanExpressionComparator();
        BooleanExpression expression0 = BooleanConstraint.parse("i2 = True");
        BooleanExpression expression1 = BooleanConstraint.parse("o1 = True");
        assertTrue(comparator.compare(expression0, expression1) < 0);
        assertTrue(comparator.compare(expression1, expression0) > 0);
    }

    /**
     * Verifies that internal variable 2 is exported before output parameter 1.
     */
    @Test
    public void shouldSortInternalVariablesBeforeOutputParameters() {
        BooleanFunction.BooleanExpressionComparator comparator = new BooleanFunction.BooleanExpressionComparator();
        BooleanExpression expression0 = BooleanConstraint.parse("v2 = True");
        BooleanExpression expression1 = BooleanConstraint.parse("o1 = True");
        assertTrue(comparator.compare(expression0, expression1) < 0);
        assertTrue(comparator.compare(expression1, expression0) > 0);
    }

    /**
     * Verifies that equality to True is resolved to True.
     */
    @Test
    public void shouldResolveEqualityToTrueToTrue() {
        String[] content = new String[] {"v1 = True", "o1 = v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = True", booleanFunction.toString());
    }

    /**
     * Verifies that equality to False is resolved to False.
     */
    @Test
    public void shouldResolveEqualityToFalseToFalse() {
        String[] content = new String[] {"v1 = False", "o1 = v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a negation of True is resolved to False.
     */
    @Test
    public void shouldResolveNegatedTrueToFalse() {
        String[] content = new String[] {"v1 = True", "o1 = ¬v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a negation of False is resolved to True.
     */
    @Test
    public void shouldResolveNegatedFalseToTrue() {
        String[] content = new String[] {"v1 = False", "o1 = ¬v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = True", booleanFunction.toString());
    }

    /**
     * Verifies that a binary and expression with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveBinaryAndWithOpenOperands() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = i1 ∧ i2", booleanFunction.toString());
    }

    /**
     * Verifies that a binary and expression with true is resolved to equality.
     */
    @Test
    public void shouldResolveBinaryAndWithTrueToEquality() {
        String[] content = new String[] {"v1 = True", "o1 = i1 ∧ v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = i1", booleanFunction.toString());
    }

    /**
     * Verifies that a binary and expression with two trues is resolved to true.
     */
    @Test
    public void shouldResolveBinaryAndWithTwoTruesToTrue() {
        String[] content = new String[] {"v1 = True", "v2 = True", "o1 = v1 ∧ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = True", booleanFunction.toString());
    }

    /**
     * Verifies that a binary and expression with false is resolved to false.
     */
    @Test
    public void shouldResolveBinaryAndWithFalseToFalse() {
        String[] content = new String[] {"v1 = False", "o1 = i1 ∧ v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a binary and expression with two falses is resolved to false.
     */
    @Test
    public void shouldResolveBinaryAndWithTwoFalsesToFalse() {
        String[] content = new String[] {"v1 = False", "v2 = False", "o1 = v1 ∧ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a binary and expression with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveBinaryOrWithOpenOperands() {
        String[] content = new String[] {"o1 = i1 ∨ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = i1 ∨ i2", booleanFunction.toString());
    }

    /**
     * Verifies that a binary or expression with false is resolved to equality.
     */
    @Test
    public void shouldResolveBinaryOrWithFalseToEquality() {
        String[] content = new String[] {"v1 = False", "o1 = i1 ∨ v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = i1", booleanFunction.toString());
    }

    /**
     * Verifies that a binary or expression with two falses is resolved to false.
     */
    @Test
    public void shouldResolveBinaryOrWithTwoFalsesToFalse() {
        String[] content = new String[] {"v1 = False", "v2 = False", "o1 = v1 ∨ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a binary or expression with true is resolved to true.
     */
    @Test
    public void shouldResolveBinaryOrWithTrueToTrue() {
        String[] content = new String[] {"v1 = True", "o1 = i1 ∨ v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = True", booleanFunction.toString());
    }

    /**
     * Verifies that a binary or expression with two trues is resolved to true.
     */
    @Test
    public void shouldResolveBinaryOrWithTwoTruesToTrue() {
        String[] content = new String[] {"v1 = True", "v2 = True", "o1 = v1 ∨ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = True", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with two open operands is not resolved.
     */
    @Test
    public void shouldNotResolveBinaryXorWithOpenOperands() {
        String[] content = new String[] {"o1 = i1 ⊻ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = i1 ⊻ i2", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with true is resolved to negation.
     */
    @Test
    public void shouldResolveBinaryXorWithTrueToNegation() {
        String[] content = new String[] {"v1 = True", "o1 = i1 ⊻ v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = ¬i1", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with not true is resolved to equality.
     */
    @Test
    public void shouldResolveBinaryXorWithNotTrueToEquality() {
        String[] content = new String[] {"v1 = True", "o1 = ¬v1 ⊻ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = v2", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with two trues is resolved to false.
     */
    @Test
    public void shouldResolveBinaryXorWithTwoTruesToFalse() {
        String[] content = new String[] {"v1 = True", "v2 = True", "o1 = v1 ⊻ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with false is resolved to equality.
     */
    @Test
    public void shouldResolveBinaryXorWithFalseToEquality() {
        String[] content = new String[] {"v1 = False", "o1 = i1 ⊻ v1"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = i1", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with not false is resolved to negation.
     */
    @Test
    public void shouldResolveBinaryXorWithNotFalseToNegation() {
        String[] content = new String[] {"v1 = False", "o1 = ¬v1 ⊻ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = ¬v2", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with two falses is resolved to false.
     */
    @Test
    public void shouldResolveBinaryXorWithTwoFalsesToFalse() {
        String[] content = new String[] {"v1 = False", "v2 = False", "o1 = v1 ⊻ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = False", booleanFunction.toString());
    }

    /**
     * Verifies that a binary xor expression with true and false is resolved to
     * true.
     */
    @Test
    public void shouldResolveBinaryXorWithTrueAndFalseToTrue() {
        String[] content = new String[] {"v1 = True", "v2 = False", "o1 = v1 ⊻ v2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        booleanFunction.resolve();
        assertEquals("o1 = True", booleanFunction.toString());
    }
}
