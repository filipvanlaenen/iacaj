package net.filipvanlaenen.iacaj;

import java.util.HashMap;
import java.util.Map;

public class ComplexityReport {

    enum Metric {
        NumberOfExpressions("Number of expressions") {
            @Override
            Number extractValue(BooleanFunction booleanFunction) {
                return booleanFunction.getNumberOfBooleanExpressions();
            }
        };

        private final String humandReadableName;

        Metric(String humandReadableName) {
            this.humandReadableName = humandReadableName;
        }

        abstract Number extractValue(BooleanFunction booleanFunction);

        protected String getHumanReadableName() {
            return humandReadableName;
        };
    }

    private final Map<Metric, Number> aggregatedValues = new HashMap<Metric, Number>();
    private final BooleanFunction booleanFunction;

    public ComplexityReport(BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    public Number getAggregatedValue(Metric metric) {
        if (!aggregatedValues.containsKey(metric)) {
            aggregatedValues.put(metric, metric.extractValue(booleanFunction));
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

    public String toYaml() {
        return null;
    }
}
