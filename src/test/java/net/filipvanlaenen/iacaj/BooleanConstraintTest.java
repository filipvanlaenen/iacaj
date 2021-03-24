package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanConstraint</code> class.
 */
public class BooleanConstraintTest {
    /**
     * Boolean equality constraint <code>i2 = i1</code>.
     */
    private static final BooleanConstraint IP2_EQUAL_TO_IP1 = BooleanConstraint.parse("i2", "i1");
    /**
     * Boolean opposition constraint <code>i2 = ¬i1</code>.
     */
    private static final BooleanConstraint IP2_NOT_EQUAL_TO_IP1 = BooleanConstraint.parse("i2", "¬i1");

    /**
     * Creates a set with input parameters with the names provided.
     *
     * @param names The names of the input parameters to be added to the set.
     * @return A set with input parameters having the provided names.
     */
    private List<InputParameter> createInputParameterSet(final String... names) {
        List<InputParameter> result = new ArrayList<InputParameter>();
        for (String name : names) {
            result.add(InputParameter.get(name));
        }
        return result;
    }

    /**
     * Verifies that True is not registered as an input parameter.
     */
    @Test
    public void trueIsNotAnInputParameter() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "True");
        assertTrue(constraint.getInputParameters().isEmpty());
    }

    /**
     * Verifies that False is not registered as an input parameter.
     */
    @Test
    public void falseIsNotAnInputParameter() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "False");
        assertTrue(constraint.getInputParameters().isEmpty());
    }

    /**
     * Verifies that equality to another input parameter is registered as an input
     * parameter.
     */
    @Test
    public void equalityReturnsAnInputParameter() {
        assertEquals(createInputParameterSet("i1"), IP2_EQUAL_TO_IP1.getInputParameters());
    }

    /**
     * Verifies that negation to another input parameter is registered as an input
     * parameter.
     */
    @Test
    public void negationReturnsAnInputParameter() {
        assertEquals(createInputParameterSet("i1"), IP2_NOT_EQUAL_TO_IP1.getInputParameters());
    }

    /**
     * A True constraint is exported correctly.
     */
    @Test
    public void trueIsExportedCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "True");
        assertEquals("i1 = True", constraint.toString());
    }

    /**
     * A True constraint is exported correctly to a Java string.
     */
    @Test
    public void trueIsExportedToJavaCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "True");
        assertEquals("assert i1 == true;", constraint.toJavaString());
    }

    /**
     * A False constraint is exported correctly.
     */
    @Test
    public void falseIsExportedCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "False");
        assertEquals("i1 = False", constraint.toString());
    }

    /**
     * A False constraint is exported correctly to a Java string.
     */
    @Test
    public void falseIsExportedToJavaCorrectly() {
        BooleanConstraint constraint = BooleanConstraint.parse("i1", "False");
        assertEquals("assert i1 == false;", constraint.toJavaString());
    }

    /**
     * An Equality constraint is exported correctly.
     */
    @Test
    public void equalityIsExportedCorrectly() {
        assertEquals("i2 = i1", IP2_EQUAL_TO_IP1.toString());
    }

    /**
     * An Equality constraint is exported correctly to a Java string.
     */
    @Test
    public void equalityIsExportedToJavaCorrectly() {
        assertEquals("assert i2 == i1;", IP2_EQUAL_TO_IP1.toJavaString());
    }

    /**
     * An Negation constraint is exported correctly.
     */
    @Test
    public void negationIsExportedCorrectly() {
        assertEquals("i2 = ¬i1", IP2_NOT_EQUAL_TO_IP1.toString());
    }

    /**
     * An Negation constraint is exported correctly to a Java string.
     */
    @Test
    public void negationIsExportedToJavaCorrectly() {
        assertEquals("assert i2 == !i1;", IP2_NOT_EQUAL_TO_IP1.toJavaString());
    }

    /**
     * An Equality is not equal to <code>null</code>.
     */
    @Test
    public void equalityShouldNotBeEqualToNull() {
        assertFalse(IP2_EQUAL_TO_IP1.equals(null));
    }

    /**
     * A Negation is not equal to <code>null</code>.
     */
    @Test
    public void negationShouldNotBeEqualToNull() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.equals(null));
    }

    /**
     * An Equality is not equal to an empty string.
     */
    @Test
    public void equalityShouldNotBeEqualToEmptyString() {
        assertFalse(IP2_EQUAL_TO_IP1.equals(""));
    }

    /**
     * A Negation is not equal to an empty string.
     */
    @Test
    public void negationShouldNotBeEqualToEmptyString() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.equals(""));
    }

    /**
     * An Equality is equal to itself.
     */
    @Test
    public void equalityShouldBeEqualToItself() {
        assertEquals(IP2_EQUAL_TO_IP1, IP2_EQUAL_TO_IP1);
    }

    /**
     * A Negation is equal to itself.
     */
    @Test
    public void negationShouldBeEqualToItself() {
        assertEquals(IP2_NOT_EQUAL_TO_IP1, IP2_NOT_EQUAL_TO_IP1);
    }

    /**
     * An Equality is equal to another equality with the same input parameters.
     */
    @Test
    public void equalityShouldBeEqualToIdenticalEquality() {
        assertEquals(IP2_EQUAL_TO_IP1, BooleanConstraint.parse("i2", "i1"));
    }

    /**
     * An Equality has the same hashcode as another equality with the same input
     * parameters.
     */
    @Test
    public void equalityShouldHaveSameHashcodeAsIdenticalEquality() {
        assertEquals(IP2_EQUAL_TO_IP1.hashCode(), BooleanConstraint.parse("i2", "i1").hashCode());
    }

    /**
     * A Negation is equal to another negation with the same input parameters.
     */
    @Test
    public void negationShouldBeEqualToIdenticalNegation() {
        assertEquals(IP2_NOT_EQUAL_TO_IP1, BooleanConstraint.parse("i2", "¬i1"));
    }

    /**
     * An Negation has the same hashcode as another Negation with the same input
     * parameters.
     */
    @Test
    public void negationShouldHaveSameHashcodeAsIdenticalNegation() {
        assertEquals(IP2_NOT_EQUAL_TO_IP1.hashCode(), BooleanConstraint.parse("i2", "¬i1").hashCode());
    }

    /**
     * An Equality is not equal to another equality with another name.
     */
    @Test
    public void equalityShouldNotBeEqualToEqualityWithOtherName() {
        assertFalse(IP2_EQUAL_TO_IP1.equals(BooleanConstraint.parse("i3", "i1")));
    }

    /**
     * An Equality should not have the same hashcode as another equality with
     * another name.
     */
    @Test
    public void equalityShouldNotHaveSameHashcodeAsEqualityWithOtherName() {
        assertFalse(IP2_EQUAL_TO_IP1.hashCode() == BooleanConstraint.parse("i3", "i1").hashCode());
    }

    /**
     * A Negation is not equal to another negation with another name.
     */
    @Test
    public void negationShouldNotBeEqualToNegationWithOtherName() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.equals(BooleanConstraint.parse("i3", "¬i1")));
    }

    /**
     * An Negation should not have the same hashcode as another Negation with
     * another name.
     */
    @Test
    public void negationShouldNotHaveSameHashcodeAsNegationWithOtherName() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.hashCode() == BooleanConstraint.parse("i3", "¬i1").hashCode());
    }

    /**
     * An Equality is not equal to another equality with another input parameter.
     */
    @Test
    public void equalityShouldNotBeEqualToEqualityWithOtherInputParameter() {
        assertFalse(IP2_EQUAL_TO_IP1.equals(BooleanConstraint.parse("i2", "i3")));
    }

    /**
     * An Equality should not have the same hashcode as another equality with
     * another input parameter.
     */
    @Test
    public void equalityShouldNotHaveSameHashcodeAsEqualityWithOtherInputParameter() {
        assertFalse(IP2_EQUAL_TO_IP1.hashCode() == BooleanConstraint.parse("i2", "i3").hashCode());
    }

    /**
     * A Negation is not equal to another negation with another input parameter.
     */
    @Test
    public void negationShouldNotBeEqualToNegationWithOtherInputParameter() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.equals(BooleanConstraint.parse("i2", "¬i3")));
    }

    /**
     * An Negation should not have the same hashcode as another Negation with
     * another input parameter.
     */
    @Test
    public void negationShouldNotHaveSameHashcodeAsNegationWithOtherInputParameter() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.hashCode() == BooleanConstraint.parse("i2", "¬i3").hashCode());
    }
}
