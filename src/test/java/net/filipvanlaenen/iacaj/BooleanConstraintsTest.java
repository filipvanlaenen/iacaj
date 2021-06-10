package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanConstraints</code> class.
 */
public class BooleanConstraintsTest {
    /**
     * A BooleanConstraint requiring that i1 is True.
     */
    private static final BooleanConstraint I1_TRUE = BooleanConstraint.parse("i1", "True");
    /**
     * A BooleanConstraints instance based on an empty set.
     */
    private static final BooleanConstraints EMPTY_BOOLEAN_CONSTRAINTS = new BooleanConstraints(Collections.emptySet());
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
        constraintsSet.add(I1_TRUE);
        return new BooleanConstraints(constraintsSet);
    }

    /**
     * Verifies that an empty BooleanConstraints instance has size zero.
     */
    @Test
    public void sizeIsZeroForAnEmptyBooleanConstraintsInstance() {
        assertEquals(0, EMPTY_BOOLEAN_CONSTRAINTS.size());
    }

    /**
     * Verifies that a BooleanConstraints instance with one Boolean constraint has
     * size one.
     */
    @Test
    public void sizeIsOneForABooleanConstraintsInstanceWithOneBooleanConstraint() {
        assertEquals(1, I1_TRUE_BOOLEAN_CONSTRAINTS.size());
    }

    /**
     * Verifies that getConstraints returns a set with the constraints.
     */
    @Test
    public void getConstraintsReturnsASetWithTheConstraints() {
        Set<BooleanConstraint> expected = new HashSet<BooleanConstraint>();
        expected.add(I1_TRUE);
        assertEquals(expected, I1_TRUE_BOOLEAN_CONSTRAINTS.getConstraints());
    }

    /**
     * Verifies that getSortedConstraints returns a sorted list with the
     * constraints.
     */
    @Test
    public void getSortedConstraintsReturnsASortedListWithTheConstraints() {
        Set<BooleanConstraint> constraintsSet = new HashSet<BooleanConstraint>();
        List<BooleanConstraint> expected = new ArrayList<BooleanConstraint>();
        expected.add(I1_TRUE);
        BooleanConstraint i2False = BooleanConstraint.parse("i2", "False");
        expected.add(i2False);
        BooleanConstraint i3True = BooleanConstraint.parse("i3", "True");
        expected.add(i3True);
        BooleanConstraint i4False = BooleanConstraint.parse("i4", "False");
        expected.add(i4False);
        constraintsSet.add(i4False);
        constraintsSet.add(i3True);
        constraintsSet.add(i2False);
        constraintsSet.add(I1_TRUE);
        BooleanConstraints constraints = new BooleanConstraints(constraintsSet);
        assertEquals(expected, constraints.getSortedConstraints());
    }

    /**
     * Verifies that an empty BooleanConstraints instance extended with a Boolean
     * constraint is equal to a BooleanConstraints instance created with that
     * Boolean constraint.
     */
    @Test
    public void extensionFromEmptyBooleanConstraintsIsEqualToBooleanConstraintsCreatedWithBooleanConstraint() {
        BooleanConstraints extendedConstraints = EMPTY_BOOLEAN_CONSTRAINTS.extend(InputParameter.get("i1"), "True");
        assertEquals(I1_TRUE_BOOLEAN_CONSTRAINTS, extendedConstraints);
    }

    /**
     * Verifies that an empty BooleanConstraints instance extended with a Boolean
     * constraint has the same hash code as a BooleanConstraints instance created
     * with that Boolean constraint.
     */
    @Test
    public void extensionFromEmptyBooleanConstraintsHasSameHashCodeAsBooleanConstraintsCreatedWithBooleanConstraint() {
        BooleanConstraints extendedConstraints = EMPTY_BOOLEAN_CONSTRAINTS.extend(InputParameter.get("i1"), "True");
        assertEquals(I1_TRUE_BOOLEAN_CONSTRAINTS.hashCode(), extendedConstraints.hashCode());
    }

    /**
     * Verifies that the empty BooleanConstraints is not equal to null.
     */
    @Test
    public void emptyBooleanConstraintsIsNotEqualToNull() {
        assertFalse(EMPTY_BOOLEAN_CONSTRAINTS.equals(null));
    }

    /**
     * Verifies that the empty BooleanConstraints is not equal to an empty string.
     */
    @Test
    public void emptyBooleanConstraintsIsNotEqualToEmptyString() {
        assertFalse(EMPTY_BOOLEAN_CONSTRAINTS.equals(""));
    }

    /**
     * Verifies that the empty BooleanConstraints is not equal to a
     * BooleanConstraints instance with one Boolean constraint.
     */
    @Test
    public void emptyBooleanConstraintsIsNotEqualToBooleanConstraintsWithI1True() {
        assertFalse(EMPTY_BOOLEAN_CONSTRAINTS.equals(I1_TRUE_BOOLEAN_CONSTRAINTS));
    }

    /**
     * Verifies that the empty BooleanConstraints has not the same hash code as a
     * BooleanConstraints instance with one Boolean constraint.
     */
    @Test
    public void emptyBooleanConstraintsHasNotSameHashCodeAsBooleanConstraintsWithI1True() {
        assertNotEquals(EMPTY_BOOLEAN_CONSTRAINTS.hashCode(), I1_TRUE_BOOLEAN_CONSTRAINTS.hashCode());
    }

    /**
     * Verifies that BooleanConstraints with i1 True is not equal to
     * BooleanConstraints with i1 False.
     */
    @Test
    public void booleanConstraintsForI1TrueIsNotEqualToBooleanConstraintsForI1False() {
        BooleanConstraints i1FalseBooleanConstraints = EMPTY_BOOLEAN_CONSTRAINTS.extend(InputParameter.get("i1"),
                "False");
        assertFalse(I1_TRUE_BOOLEAN_CONSTRAINTS.equals(i1FalseBooleanConstraints));
    }

    /**
     * Verifies that BooleanConstraints with i1 True has not the same hash code as
     * BooleanConstraints with i1 False.
     */
    @Test
    public void booleanConstraintsForI1TrueHasNotSameHashCodeAsBooleanConstraintsForI1False() {
        BooleanConstraints i1FalseBooleanConstraints = EMPTY_BOOLEAN_CONSTRAINTS.extend(InputParameter.get("i1"),
                "False");
        assertNotEquals(I1_TRUE_BOOLEAN_CONSTRAINTS.hashCode(), i1FalseBooleanConstraints.hashCode());
    }

    /**
     * Verifies that two transitive equalities are resolved into equality with the
     * lowest input parameter.
     */
    @Test
    public void twoTransitiveEqualitiesShouldResolveToTwoEqualities() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "i1"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "i1"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "i1"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that equality to opposition constraint is resolved into two
     * oppositions to the lowest input parameter.
     */
    @Test
    public void equalityToOppositionShouldResolveToTwoOppositionConstraints() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "¬i1"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "¬i1"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "¬i1"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that two transitive oppositions are resolved into equality and
     * opposition to the lowest input parameter.
     */
    @Test
    public void twoTransitiveOppositionsShouldResolveToEqualityAndOppisition() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "¬i1"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "¬i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "¬i1"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "i1"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that opposition to equality constraint is resolved into opposition
     * and equality to the lowest input parameter.
     */
    @Test
    public void oppositionToEqualityShouldResolveToOppositionAndEqualityConstraint() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "i1"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "¬i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "i1"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "¬i1"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that equality to a true constraint is resolved into two true
     * constraints.
     */
    @Test
    public void equalityToTrueConstraintShouldResolveToTwoTrueConstraints() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "True"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "True"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "True"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that equality to a false constraint is resolved into two true
     * constraints.
     */
    @Test
    public void equalityToFalseConstraintShouldResolveToTwoFalseConstraints() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "False"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "False"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "False"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that opposition to a true constraint is resolved into a false and a
     * true constraint.
     */
    @Test
    public void oppositionToTrueConstraintShouldResolveFalseAndTrueConstraint() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "True"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "¬i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "True"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "False"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }

    /**
     * Verifies that opposition to a false constraint is resolved into true and
     * false constraint.
     */
    @Test
    public void oppositionToFalseConstraintShouldResolveToFalseAndTrueConstraint() {
        Set<BooleanConstraint> actualConstraintsSet = new HashSet<BooleanConstraint>();
        actualConstraintsSet.add(BooleanConstraint.parse("i2", "False"));
        actualConstraintsSet.add(BooleanConstraint.parse("i3", "¬i2"));
        BooleanConstraints actual = new BooleanConstraints(actualConstraintsSet);
        Set<BooleanConstraint> expectedConstraintsSet = new HashSet<BooleanConstraint>();
        expectedConstraintsSet.add(BooleanConstraint.parse("i2", "False"));
        expectedConstraintsSet.add(BooleanConstraint.parse("i3", "True"));
        BooleanConstraints expected = new BooleanConstraints(expectedConstraintsSet);
        assertEquals(expected, actual);
    }
}
