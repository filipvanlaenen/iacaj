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
            Number measure(final BooleanFunction bf) {
                return bf.getNumberOfBooleanExpressions();
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
        };

        /**
         * Measures a Boolean function for the metric.
         *
         * @param bf The Boolean function that should be measures against the metric.
         * @return The measurement for the metric.
         */
        abstract Number measure(BooleanFunction bf);
    }

    /**
     * Map holding the aggregated values per metric.
     */
    private final Map<Metric, Number> aggregatedValues = new HashMap<Metric, Number>();
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
    public Number getAggregatedValue(final Metric metric) {
        if (!aggregatedValues.containsKey(metric)) {
            aggregatedValues.put(metric, metric.measure(booleanFunction));
        }
        return aggregatedValues.get(metric);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Metric metric : Metric.values()) {
            sb.append(metric.getHumanReadableName() + ": " + getAggregatedValue(metric) + "\n");
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
