package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ComplexityReport</code> class.
 */
public class ComplexityReportTest {
    /**
     * Verifies that it reports one Boolean expression correctly.
     */
    @Test
    public void shouldReportOnOneBooleanExpression() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        ComplexityReport complexityReport = new ComplexityReport(booleanFunction);
        assertEquals(1, complexityReport.getAggregatedValue(ComplexityReport.Metric.NumberOfExpressions));
    }

    /**
     * Verifies that it reports one Boolean expression for an input parameter
     * correctly.
     */
    @Test
    public void shouldReportOnOneBooleanExpressionForAnInputParameter() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        ComplexityReport complexityReport = new ComplexityReport(booleanFunction);
        assertEquals(1, complexityReport.getInputParameterValue(ComplexityReport.Metric.NumberOfExpressions,
                InputParameter.get("i1")));
    }

    /**
     * Verifies that constraints are not included in the number of Boolean
     * expressions.
     */
    @Test
    public void numberOfExpressionsShouldNotIncludeConstraints() {
        String[] content = new String[] {"i1 = True", "o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        ComplexityReport complexityReport = new ComplexityReport(booleanFunction);
        assertEquals(1, complexityReport.getAggregatedValue(ComplexityReport.Metric.NumberOfExpressions));
    }

    /**
     * Verifies that constraints are not included in the number of Boolean
     * expressions for an input parameter.
     */
    @Test
    public void numberOfExpressionsForAnInputParameterShouldNotIncludeConstraints() {
        String[] content = new String[] {"i1 = True", "o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        ComplexityReport complexityReport = new ComplexityReport(booleanFunction);
        assertEquals(1, complexityReport.getInputParameterValue(ComplexityReport.Metric.NumberOfExpressions,
                InputParameter.get("i1")));
    }

    /**
     * Verifies that <code>toString</code> produces a correct human readable string
     * representation.
     */
    @Test
    public void toStringProducesCorrectHumanReadableRepresentation() {
        String[] content = new String[] {"o1 = i1 ∧ i2", "o2 = i2 ∧ i3 ∧ i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        ComplexityReport complexityReport = new ComplexityReport(booleanFunction);
        StringBuffer sb = new StringBuffer();
        sb.append("Number of expressions: 2\n");
        sb.append("  i1: 1\n");
        sb.append("  i2: 2\n");
        sb.append("  i3: 1\n");
        sb.append("  i4: 1");
        assertEquals(sb.toString(), complexityReport.toString());
    }
}
