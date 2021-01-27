package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AttackRecords</code> class.
 */
public class AttackRecordsTest {
    /**
     * Verifies that when line #1 is missing records, and the previous line has a
     * record, the line with the missing records is returned as the next line to
     * attack.
     */
    @Test
    public void findsLineOneMissingRecordsFollowingALineWithRecords() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecords records = new AttackRecords(booleanFunction);
        assertEquals(1, records.findNextLineToAttack());
    }

    /**
     * Verifies that when line #2 is missing records, and the previous line has a
     * record, the line with the missing records is returned as the next line to
     * attack.
     */
    @Test
    public void findsLineTwoMissingRecordsFollowingALineWithRecords() {
        String[] content = new String[] {"o1 = i1 ∧ i2 ∧ i3"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        AttackRecords records = new AttackRecords(booleanFunction);
        String[] content1 = new String[] {"i1 = True", "o1 = i2 ∧ i3"};
        BooleanFunction booleanFunction1 = BooleanFunction.parse(content1);
        records.add(booleanFunction1);
        assertEquals(2, records.findNextLineToAttack());
    }
}
