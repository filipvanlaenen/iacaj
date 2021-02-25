package net.filipvanlaenen.iacaj;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a complexity report for a Boolean function.
 */
public final class ComplexityReport {

    /**
     * The metrics against which a Boolean function can be measured.
     */
    enum Metric {
        /**
         * A metric counting the number of expressions in a Boolean function.
         */
        NumberOfExpressions("Number of expressions") {
            @Override
            long measureIncrement(final BooleanExpression booleanExpression) {
                return booleanExpression instanceof BooleanConstraint ? 0L : 1L;
            }

            @Override
            long measureIncrement(final BooleanExpression booleanExpression, final InputParameter inputParameter) {
                return booleanExpression instanceof BooleanConstraint ? 0L : 1L;
            }
        };

        /**
         * A human readable name for the metric.
         */
        private final String humandReadableName;

        /**
         * A constructor taking the human readable name for the metric as its parameter.
         *
         * @param humandReadableName The human readable name for the metric.
         */
        Metric(final String humandReadableName) {
            this.humandReadableName = humandReadableName;
        }

        /**
         * Returns the human readable name of the metric.
         *
         * @return The human readable name of the metric.
         */
        protected String getHumanReadableName() {
            return humandReadableName;
        }

        /**
         * Measures the increment in the metric for the Boolean expression provided.
         *
         * @param booleanExpression The Boolean expression to measure the increment.
         * @return The increment in the metric for the Boolean expression.
         */
        abstract long measureIncrement(BooleanExpression booleanExpression);

        /**
         * Measures the increment in the metric for an input parameter in a Boolean
         * expression.
         *
         * @param booleanExpression The Boolean expression to measure the increment.
         * @param inputParameter    The input parameter for which to measure the
         *                          increment.
         * @return The increment in the metric for the input parameter in the Boolean
         *         expression.
         */
        abstract long measureIncrement(BooleanExpression booleanExpression, InputParameter inputParameter);
    }

    /**
     * Map holding the aggregated values per metric.
     */
    private final Map<Metric, Long> aggregatedValues;
    /**
     * Map holding the input parameter values per metric.
     */
    private final Map<Metric, Map<InputParameter, Long>> inputParameterValues;

    /**
     * Constructor with the Boolean function for which to produce a complexity
     * report as its parameter.
     *
     * @param booleanFunction The Boolean function for which to produce a complexity
     *                        report.
     */
    public ComplexityReport(final BooleanFunction booleanFunction) {
        aggregatedValues = new HashMap<Metric, Long>();
        inputParameterValues = new HashMap<Metric, Map<InputParameter, Long>>();
        measure(booleanFunction);
    }

    /**
     * Returns the aggregated value for a metric.
     *
     * @param metric The metric for which to return the aggregated value.
     * @return The aggregated value for the metric.
     */
    Long getAggregatedValue(final Metric metric) {
        return aggregatedValues.get(metric);
    }

    /**
     * Returns the value for a metric for an input parameter.
     *
     * @param metric         The metric for which to return the input parameter's
     *                       value.
     * @param inputParameter The input parameter for which to return the metric's
     *                       value.
     * @return The value for a metric for an input parameter.
     */
    Long getInputParameterValue(final Metric metric, final InputParameter inputParameter) {
        return inputParameterValues.get(metric).get(inputParameter);
    }

    /**
     * Measures all the metrics for a Boolean function.
     *
     * @param booleanFunction The Boolean function for which to produce a complexity
     *                        report.
     */
    private void measure(final BooleanFunction booleanFunction) {
        for (Metric metric : Metric.values()) {
            aggregatedValues.put(metric, 0L);
            inputParameterValues.put(metric, new HashMap<InputParameter, Long>());
        }
        for (BooleanExpression booleanExpression : booleanFunction.getExpressions()) {
            for (Metric metric : Metric.values()) {
                aggregatedValues.put(metric, aggregatedValues.get(metric) + metric.measureIncrement(booleanExpression));
                for (InputParameter inputParameter : booleanExpression.getInputParameters()) {
                    Map<InputParameter, Long> inputParameterValue = inputParameterValues.get(metric);
                    if (!inputParameterValue.containsKey(inputParameter)) {
                        inputParameterValue.put(inputParameter, 0L);
                    }
                    inputParameterValue.put(inputParameter, inputParameterValue.get(inputParameter)
                            + metric.measureIncrement(booleanExpression, inputParameter));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Metric metric : Metric.values()) {
            sb.append(metric.getHumanReadableName() + ": " + getAggregatedValue(metric) + "\n");
        }
        for (Metric metric : Metric.values()) {
            Map<InputParameter, Long> inputParameterValuesMap = inputParameterValues.get(metric);
            for (InputParameter inputParameter : inputParameterValuesMap.keySet()) {
                sb.append(metric.getHumanReadableName() + ": " + inputParameter.getName() + ": "
                        + inputParameterValuesMap.get(inputParameter) + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns a YAML representation of the complexity report.
     *
     * @return A YAML document representing the complexity report.
     */
    public String toYaml() {
        return null;
    }
}
