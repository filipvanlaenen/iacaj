package net.filipvanlaenen.iacaj;

import java.util.Comparator;

import net.filipvanlaenen.iacaj.ComplexityReport.Metric;
import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.SortedCollection;

/**
 * Class representing all the archive information needed from an attack.
 */
class AttackRecord {
    /**
     * The input parameters.
     */
    private final Collection<InputParameter> inputParameters;
    /**
     * The Boolean constraints.
     */
    private final BooleanConstraints constraints;
    /**
     * The complexity report.
     */
    private final ComplexityReport complexityReport;

    /**
     * Creates a new attack record for a Boolean function. The Boolean function is assumed to have been resolved.
     *
     * @param booleanFunction The Boolean function for which to make an attack record.
     */
    AttackRecord(final BooleanFunction booleanFunction) {
        this.inputParameters = booleanFunction.getInputParameters();
        this.constraints = booleanFunction.getConstraints();
        this.complexityReport = new ComplexityReport(booleanFunction);
    }

    /**
     * Returns the complexity report.
     *
     * @return The complexity report.
     */
    ComplexityReport getComplexityReport() {
        return complexityReport;
    }

    /**
     * Returns the Boolean constraints.
     *
     * @return The Boolean constraints.
     */
    BooleanConstraints getConstraints() {
        return constraints;
    }

    /**
     * Returns a set with the input parameters.
     *
     * @return A set with the input parameters.
     */
    Collection<InputParameter> getInputParameters() {
        return inputParameters;
    }

    /**
     * Returns a prioritized list of the input parameters.
     *
     * @return A prioritized list of the input parameters.
     */
    SortedCollection<InputParameter> getPrioritizedInputParameters() {
        return SortedCollection.<InputParameter>of(new Comparator<InputParameter>() {
            @Override
            public int compare(final InputParameter ip0, final InputParameter ip1) {
                Long m0 = complexityReport.getInputParameterValue(Metric.NumberOfExpressions, ip0);
                Long m1 = complexityReport.getInputParameterValue(Metric.NumberOfExpressions, ip1);
                return m1.intValue() - m0.intValue();
            }
        }, inputParameters);
    }

    /**
     * Returns a prioritized list of the input parameter pairs.
     *
     * @return A prioritized list of the input parameter pairs.
     */
    SortedCollection<InputParameterPair> getPrioritizedInputParameterPairs() {
        return SortedCollection.<InputParameterPair>of(new Comparator<InputParameterPair>() {
            @Override
            public int compare(final InputParameterPair ipp0, final InputParameterPair ipp1) {
                Long m0 = complexityReport.getInputParameterPairValue(Metric.NumberOfExpressions, ipp0);
                Long m1 = complexityReport.getInputParameterPairValue(Metric.NumberOfExpressions, ipp1);
                return m1.intValue() - m0.intValue();
            }
        }, complexityReport.getInputParameterPairs());
    }
}
