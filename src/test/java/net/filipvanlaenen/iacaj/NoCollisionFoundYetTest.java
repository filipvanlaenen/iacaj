package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>NoCollisionFoundYet</code> class.
 */
public class NoCollisionFoundYetTest {
    /**
     * A BooleanConstraints instance requiring i1 True.
     */
    private static final BooleanConstraints I1_TRUE_BOOLEAN_CONSTRAINTS = createBooleanConstraintsForI1True();

    /**
     * Creates a BooleanConstraints instance with a Boolean constraint for i1 True.
     *
     * @return A BooleanConstraints instance requiring i1 True.
     */
    private static BooleanConstraints createBooleanConstraintsForI1True() {
        Set<BooleanConstraint> constraintsSet = new HashSet<BooleanConstraint>();
        constraintsSet.add(BooleanConstraint.parse("i1", "True"));
        return new BooleanConstraints(constraintsSet);
    }

    /**
     * Verifies that the message in <code>toString()</code> is correct after 1
     * iteration.
     */
    @Test
    public void toStringMessageIsCorrectAfterOneIteration() {
        StringBuffer expected = new StringBuffer();
        expected.append("No collision found in the Boolean function after 1 iteration.\n");
        expected.append("  Best set of constraints so far:\n");
        expected.append("    i1 = True\n");
        expected.append("  Number of expressions: 1");
        assertEquals(expected.toString(), new NoCollisionFoundYet(1, I1_TRUE_BOOLEAN_CONSTRAINTS, 1).toString());
    }

    /**
     * Verifies that the message in <code>toString()</code> is correct after 2
     * iterations.
     */
    @Test
    public void toStringMessageIsCorrectAfterTwoIterations() {
        StringBuffer expected = new StringBuffer();
        expected.append("No collision found in the Boolean function after 2 iterations.\n");
        expected.append("  Best set of constraints so far:\n");
        expected.append("    i1 = True\n");
        expected.append("  Number of expressions: 1");
        assertEquals(expected.toString(), new NoCollisionFoundYet(2, I1_TRUE_BOOLEAN_CONSTRAINTS, 1).toString());
    }

    /**
     * Verifies that the getter for the number of iterations is wired correctly.
     */
    @Test
    public void getterForNumberOfIterationsIsWiredCorrectlyFromConstructor() {
        assertEquals(2, new NoCollisionFoundYet(2, I1_TRUE_BOOLEAN_CONSTRAINTS, 1).getNumberOfIterations());
    }
}
