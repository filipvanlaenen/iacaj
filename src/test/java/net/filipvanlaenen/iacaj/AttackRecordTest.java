package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AttackRecord</code> class.
 */
public class AttackRecordTest {
    /**
     * Verifies that the constructor extracts the input parameters from the Boolean
     * function.
     */
    @Test
    public void constructorExtractsTheInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        Set<InputParameter> expected = new HashSet<InputParameter>();
        expected.add(InputParameter.get("i1"));
        expected.add(InputParameter.get("i2"));
        assertEquals(expected, attackRecord.getInputParameters());
    }

    /**
     * Verifies that the constructor extracts the Boolean constraints from the
     * Boolean function.
     */
    @Test
    public void constructorExtractsTheBooleanConstraints() {
        String[] content = new String[] {"i1 = True", "o1 = i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        Set<BooleanConstraint> constraintsSet = new HashSet<BooleanConstraint>();
        constraintsSet.add(BooleanConstraint.parse("i1", "True"));
        BooleanConstraints expected = new BooleanConstraints(constraintsSet);
        assertEquals(expected, attackRecord.getConstraints());
    }

    /**
     * Verifies that the constructor extracts a complexity report from the Boolean
     * function.
     */
    @Test
    public void constructorExtractsAComplexityReport() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        ComplexityReport complexityReport = attackRecord.getComplexityReport();
        assertNotNull(complexityReport);
        assertEquals(1, complexityReport.getAggregatedValue(ComplexityReport.Metric.NumberOfExpressions));
    }

    /**
     * Verifies that the input parameters are prioritized according to their number
     * of occurrences.
     */
    @Test
    public void inputParametersArePrioritizedAccordingToTheirNumberOfOccurrences() {
        String[] content = new String[] {"o1 = i1 ∧ i2 ∧ i3", "o2 = i2 ∧ i3", "o2 = i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        List<InputParameter> expected = new ArrayList<InputParameter>();
        expected.add(InputParameter.get("i3"));
        expected.add(InputParameter.get("i2"));
        expected.add(InputParameter.get("i1"));
        assertEquals(expected, attackRecord.getPrioritizedInputParameters());
    }
}
