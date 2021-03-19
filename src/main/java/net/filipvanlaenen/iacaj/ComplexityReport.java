package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

            @Override
            long measureIncrement(BooleanExpression booleanExpression, InputParameter[] inputParameterPair) {
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

        abstract long measureIncrement(BooleanExpression booleanExpression, InputParameter[] inputParameterPair);
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
     * Map holding the input parameter pair values per metric.
     */
    private final Map<Metric, Map<InputParameter[], Long>> inputParameterPairValues;
    private final Set<InputParameter[]> inputParameterPairs;

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
        inputParameterPairs = new HashSet<InputParameter[]>();
        inputParameterPairValues = new HashMap<Metric, Map<InputParameter[], Long>>();
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

    Set<InputParameter[]> getInputParameterPairs() {
        return inputParameterPairs;
    }

    Long getInputParameterPairValue(Metric metric, InputParameter[] inputParameterPair) {
        return inputParameterPairValues.get(metric).get(inputParameterPair);
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
            inputParameterPairValues.put(metric, new HashMap<InputParameter[], Long>());
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
                for (InputParameter[] inputParameterPair : calculateInputParameterPairs(
                        booleanExpression.getInputParameters())) {
                    if (!inputParameterPairs.contains(inputParameterPair)) {
                        inputParameterPairs.add(inputParameterPair);
                    }
                    Map<InputParameter[], Long> inputParameterPairValue = inputParameterPairValues.get(metric);
                    if (!inputParameterPairValue.containsKey(inputParameterPair)) {
                        inputParameterPairValue.put(inputParameterPair, 0L);
                    }
                    inputParameterPairValue.put(inputParameterPair, inputParameterPairValue.get(inputParameterPair)
                            + metric.measureIncrement(booleanExpression, inputParameterPair));
                }
            }
        }
    }

    private Set<InputParameter[]> calculateInputParameterPairs(List<InputParameter> inputParameters) {
        Set<InputParameter[]> result = new HashSet<InputParameter[]>();
        for (int i = 0; i < inputParameters.size(); i++) {
            for (int j = i + 1; j < inputParameters.size(); j++) {
                result.add(new InputParameter[] {inputParameters.get(i), inputParameters.get(j)});
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Metric metric : Metric.values()) {
            sb.append(metric.getHumanReadableName() + ": " + getAggregatedValue(metric) + "\n");
        }
        for (Metric metric : Metric.values()) {
            Map<InputParameter, Long> inputParameterValuesMap = inputParameterValues.get(metric);
            List<InputParameter> sortedInputParameters = new ArrayList<InputParameter>(
                    inputParameterValuesMap.keySet());
            sortedInputParameters.sort(new Comparator<InputParameter>() {
                @Override
                public int compare(final InputParameter ip0, final InputParameter ip1) {
                    return ip0.getNumber() - ip1.getNumber();
                }
            });
            for (int i = 0; i < sortedInputParameters.size(); i++) {
                InputParameter sortedInputParameter = sortedInputParameters.get(i);
                sb.append("  " + sortedInputParameter.getName() + ": "
                        + inputParameterValuesMap.get(sortedInputParameter));
                if (i < sortedInputParameters.size() - 1) {
                    sb.append("\n");
                }
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
