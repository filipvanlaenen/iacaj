package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>AttackRecord</code> class.
 */
public class AttackRecordTest {
    /**
     * Input parameter i1.
     */
    private static final InputParameter INPUT_PARAMETER_1 = InputParameter.get("i1");
    /**
     * Input parameter i2.
     */
    private static final InputParameter INPUT_PARAMETER_2 = InputParameter.get("i2");
    /**
     * Input parameter i3.
     */
    private static final InputParameter INPUT_PARAMETER_3 = InputParameter.get("i3");
    /**
     * Input parameter i4.
     */
    private static final InputParameter INPUT_PARAMETER_4 = InputParameter.get("i4");

    /**
     * Verifies that the constructor extracts the input parameters from the Boolean function.
     */
    @Test
    public void constructorExtractsTheInputParameters() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        Collection<InputParameter> expected = Collection.of(INPUT_PARAMETER_1, INPUT_PARAMETER_2);
        assertTrue(expected.containsSame(attackRecord.getInputParameters()));
    }

    /**
     * Verifies that the constructor extracts the Boolean constraints from the Boolean function.
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
     * Verifies that the constructor extracts a complexity report from the Boolean function.
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
     * Verifies that the input parameters are prioritized according to their number of occurrences.
     */
    @Test
    public void inputParametersArePrioritizedAccordingToTheirNumberOfOccurrences() {
        String[] content = new String[] {"o1 = i1 ∧ i2 ∧ i3 ∧ i4", "o2 = i2 ∧ i3 ∧ i4", "o2 = i3 ∧ i4", "o3 = i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        OrderedCollection<InputParameter> expected =
                OrderedCollection.of(INPUT_PARAMETER_4, INPUT_PARAMETER_3, INPUT_PARAMETER_2, INPUT_PARAMETER_1);
        assertTrue(expected.containsSame(attackRecord.getPrioritizedInputParameters()));
    }

    /**
     * Verifies that the input parameter pairs are prioritized according to their number of occurrences.
     */
    @Test
    public void inputParameterPairsArePrioritizedAccordingToTheirNumberOfOccurrences() {
        String[] content = new String[] {"o1 = i1 ∧ i2 ∧ i3", "o2 = i1 ∧ i3", "o2 = i1 ∧ i2", "o3 = i1 ∨ i2",
                "o4 = i1 ∧ i4", "o1 = i1 ∨ i2 ∨ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecord attackRecord = new AttackRecord(booleanFunction);
        Collection<InputParameterPair> expected =
                Collection.of(new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_2),
                        new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_3),
                        new InputParameterPair(INPUT_PARAMETER_2, INPUT_PARAMETER_3),
                        new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_4));
        assertTrue(expected.containsSame(attackRecord.getPrioritizedInputParameterPairs()));
    }
}
