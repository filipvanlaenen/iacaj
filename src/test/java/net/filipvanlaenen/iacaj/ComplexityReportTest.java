package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
}
