package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AttackRecords</code> class.
 */
public class AttackRecordsTest {
    /**
     * Verifies that the size of the attack records is 0 after creation.
     */
    @Test
    public void sizeIsZeroAfterCreation() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecords attackRecords = new AttackRecords(booleanFunction);
        assertEquals(0, attackRecords.size());
    }

    /**
     * Verifies that after adding one attack, the size of the attack records is 1.
     */
    @Test
    public void sizeIsOneAfterAddingOneAttack() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecords attackRecords = new AttackRecords(booleanFunction);
        String[] attackContent = new String[] {"i1 = True", "o1 = i2"};
        BooleanFunction attack = BooleanFunction.parse(attackContent);
        attackRecords.add(attack);
        assertEquals(1, attackRecords.size());
    }

    /**
     * Verifies that it selects the input parameter occurring twice as the input
     * parameter to constrain to False.
     */
    @Test
    public void constrainsInputParameterOccurringTwiceToFalse() {
        String[] content = new String[] {"o1 = i1 ∧ i2", "o2 = i1 ∧ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecords attackRecords = new AttackRecords(booleanFunction);
        BooleanConstraints actualNextCollisionCandidate = attackRecords.findNextCollisionCandidate();
        BooleanConstraint i1False = BooleanConstraint.parse("i1", "False");
        Set<BooleanConstraint> constraintsSet = new HashSet<BooleanConstraint>();
        constraintsSet.add(i1False);
        BooleanConstraints expectedNextCollissionCandidate = new BooleanConstraints(constraintsSet);
        assertEquals(expectedNextCollissionCandidate, actualNextCollisionCandidate);
    }
}
