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
            long measure(BooleanExpression booleanExpression) {
                if (booleanExpression instanceof BooleanConstraint) {
                    return 0L;
                } else {
                    return 1L;
                }
            }

            @Override
            long measure(BooleanExpression booleanExpression, InputParameter inputParameter) {
                if (booleanExpression instanceof BooleanConstraint) {
                    return 0L;
                } else {
                    return 1L;
                }
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

        abstract long measure(BooleanExpression booleanExpression);

        abstract long measure(BooleanExpression booleanExpression, InputParameter inputParameter);
    }

    /**
     * Map holding the aggregated values per metric.
     */
    private final Map<Metric, Long> aggregatedValues = new HashMap<Metric, Long>();
    private final Map<Metric, Map<InputParameter, Long>> inputParameterValues = new HashMap<Metric, Map<InputParameter, Long>>();
    /**
     * The Boolean function.
     */
    private final BooleanFunction booleanFunction;

    /**
     * Constructor with the Boolean function for which to produce a complexity
     * report as its parameter.
     *
     * @param booleanFunction The Boolean function for which to produce a complexity
     *                        report.
     */
    public ComplexityReport(final BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    /**
     * Returns the aggregated value for a metric.
     *
     * @param metric The metric for which to return the aggregated value.
     * @return The aggregated value for the metric.
     */
    public Long getAggregatedValue(final Metric metric) {
        if (!aggregatedValues.containsKey(metric)) {
            measure();
        }
        return aggregatedValues.get(metric);
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

    public Long getInputParameterValue(Metric metric, InputParameter inputParameter) {
        if (!inputParameterValues.containsKey(metric)) {
            measure();
        }
        return inputParameterValues.get(metric).get(inputParameter);
    }

    private void measure() {
        for (Metric metric : Metric.values()) {
            aggregatedValues.put(metric, 0L);
            inputParameterValues.put(metric, new HashMap<InputParameter, Long>());
        }
        for (BooleanExpression booleanExpression : booleanFunction.getExpressions()) {
            for (Metric metric : Metric.values()) {
                aggregatedValues.put(metric, aggregatedValues.get(metric) + metric.measure(booleanExpression));
                for (InputParameter inputParameter : booleanExpression.getInputParameters()) {
                    if (!inputParameterValues.get(metric).containsKey(inputParameter)) {
                        inputParameterValues.get(metric).put(inputParameter, 0L);
                    }
                    inputParameterValues.get(metric).put(inputParameter,
                            inputParameterValues.get(metric).get(inputParameter)
                                    + metric.measure(booleanExpression, inputParameter));
                }
            }
        }
    }
}
