package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.ModifiableOrderedCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>BooleanConstraint</code> class.
 */
public class BooleanConstraintTest {
    /**
     * Boolean equality constraint <code>i1 = False</code>.
     */
    private static final BooleanConstraint IP1_FALSE = BooleanConstraint.parse("i1", "False");
    /**
     * Boolean equality constraint <code>i1 = True</code>.
     */
    private static final BooleanConstraint IP1_TRUE = BooleanConstraint.parse("i1", "True");
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
    private OrderedCollection<InputParameter> createInputParameterSet(final String... names) {
        ModifiableOrderedCollection<InputParameter> result = ModifiableOrderedCollection.<InputParameter>empty();
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
        assertTrue(IP1_TRUE.getInputParameters().isEmpty());
    }

    /**
     * Verifies that False is not registered as an input parameter.
     */
    @Test
    public void falseIsNotAnInputParameter() {
        assertTrue(IP1_FALSE.getInputParameters().isEmpty());
    }

    /**
     * Verifies that equality to another input parameter is registered as an input parameter.
     */
    @Test
    public void equalityReturnsAnInputParameter() {
        assertTrue(createInputParameterSet("i1").containsSame(IP2_EQUAL_TO_IP1.getInputParameters()));
    }

    /**
     * Verifies that negation to another input parameter is registered as an input parameter.
     */
    @Test
    public void negationReturnsAnInputParameter() {
        assertTrue(createInputParameterSet("i1").containsSame(IP2_NOT_EQUAL_TO_IP1.getInputParameters()));
    }

    /**
     * A True constraint is exported correctly.
     */
    @Test
    public void trueIsExportedCorrectly() {
        assertEquals("i1 = True", IP1_TRUE.toString());
    }

    /**
     * A True constraint is exported correctly to a Java string.
     */
    @Test
    public void trueIsExportedToJavaCorrectly() {
        assertEquals("assert i1 == true;", IP1_TRUE.toJavaString());
    }

    /**
     * A False constraint is exported correctly.
     */
    @Test
    public void falseIsExportedCorrectly() {
        assertEquals("i1 = False", IP1_FALSE.toString());
    }

    /**
     * A False constraint is exported correctly to a Java string.
     */
    @Test
    public void falseIsExportedToJavaCorrectly() {
        assertEquals("assert i1 == false;", IP1_FALSE.toJavaString());
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
     * False is not true.
     */
    @Test
    public void falseShouldNotBeTrue() {
        assertFalse(IP1_FALSE.isTrue());
    }

    /**
     * True is true.
     */
    @Test
    public void trueShouldBeTrue() {
        assertTrue(IP1_TRUE.isTrue());
    }

    /**
     * Equality is not true.
     */
    @Test
    public void equalityShouldNotBeTrue() {
        assertFalse(IP2_EQUAL_TO_IP1.isTrue());
    }

    /**
     * Opposition is not true.
     */
    @Test
    public void oppositionShouldNotBeTrue() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.isTrue());
    }

    /**
     * False is false.
     */
    @Test
    public void falseShouldBeFalse() {
        assertTrue(IP1_FALSE.isFalse());
    }

    /**
     * True is not false.
     */
    @Test
    public void trueShouldNotBeFalse() {
        assertFalse(IP1_TRUE.isFalse());
    }

    /**
     * Equality is not false.
     */
    @Test
    public void equalityShouldNotBeFalse() {
        assertFalse(IP2_EQUAL_TO_IP1.isFalse());
    }

    /**
     * Opposition is not false.
     */
    @Test
    public void oppositionShouldNotBeFalse() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.isFalse());
    }

    /**
     * A false constraint is not equal to <code>null</code>.
     */
    @Test
    public void falseShouldNotBeEqualToNull() {
        assertFalse(IP1_FALSE.equals(null));
    }

    /**
     * A true constraint is not equal to <code>null</code>.
     */
    @Test
    public void trueShouldNotBeEqualToNull() {
        assertFalse(IP1_TRUE.equals(null));
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
     * A false constraint is not equal to an empty string.
     */
    @Test
    public void falseShouldNotBeEqualToEmptyString() {
        assertFalse(IP1_FALSE.equals(""));
    }

    /**
     * A true constraint is not equal to an empty string.
     */
    @Test
    public void trueShouldNotBeEqualToEmptyString() {
        assertFalse(IP1_TRUE.equals(""));
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
     * A false constraint is equal to itself.
     */
    @Test
    public void falseShouldBeEqualToItself() {
        assertEquals(IP1_FALSE, IP1_FALSE);
    }

    /**
     * A true constraint is equal to itself.
     */
    @Test
    public void trueShouldBeEqualToItself() {
        assertEquals(IP1_TRUE, IP1_TRUE);
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
     * A false constraint is equal to another false constraint with the same input parameter.
     */
    @Test
    public void falseShouldBeEqualToIdenticalFalse() {
        assertEquals(IP1_FALSE, BooleanConstraint.parse("i1", "False"));
    }

    /**
     * A true constraint is equal to another true constraint with the same input parameter.
     */
    @Test
    public void trueShouldBeEqualToIdenticalTrue() {
        assertEquals(IP1_TRUE, BooleanConstraint.parse("i1", "True"));
    }

    /**
     * An Equality is equal to another equality with the same input parameters.
     */
    @Test
    public void equalityShouldBeEqualToIdenticalEquality() {
        assertEquals(IP2_EQUAL_TO_IP1, BooleanConstraint.parse("i2", "i1"));
    }

    /**
     * A false constraint has the same hashcode as another false constraint with the same input parameter.
     */
    @Test
    public void falseShouldHaveSameHashcodeAsIdenticalFalse() {
        assertEquals(IP1_FALSE.hashCode(), BooleanConstraint.parse("i1", "False").hashCode());
    }

    /**
     * A true constraint has the same hashcode as another true constraint with the same input parameter.
     */
    @Test
    public void trueShouldHaveSameHashcodeAsIdenticalTrue() {
        assertEquals(IP1_TRUE.hashCode(), BooleanConstraint.parse("i1", "True").hashCode());
    }

    /**
     * An Equality has the same hashcode as another equality with the same input parameters.
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
     * An Negation has the same hashcode as another Negation with the same input parameters.
     */
    @Test
    public void negationShouldHaveSameHashcodeAsIdenticalNegation() {
        assertEquals(IP2_NOT_EQUAL_TO_IP1.hashCode(), BooleanConstraint.parse("i2", "¬i1").hashCode());
    }

    /**
     * A false constraint is not equal to another false constraint with another input parameter.
     */
    @Test
    public void falseShouldNotBeEqualToFalseWithOtherName() {
        assertFalse(IP1_FALSE.equals(BooleanConstraint.parse("i2", "False")));
    }

    /**
     * A true constraint is not equal to another true constraint with another input parameter.
     */
    @Test
    public void trueShouldNotBeEqualToTrueWithOtherName() {
        assertFalse(IP1_TRUE.equals(BooleanConstraint.parse("i2", "True")));
    }

    /**
     * An Equality is not equal to another equality with another name.
     */
    @Test
    public void equalityShouldNotBeEqualToEqualityWithOtherName() {
        assertFalse(IP2_EQUAL_TO_IP1.equals(BooleanConstraint.parse("i3", "i1")));
    }

    /**
     * A false constraint should not have the same hashcode as another false constraint with another input parameter.
     */
    @Test
    public void falseShouldNotHaveSameHashcodeAsFalseWithOtherName() {
        assertFalse(IP1_FALSE.hashCode() == BooleanConstraint.parse("i2", "False").hashCode());
    }

    /**
     * A true constraint should not have the same hashcode as another true constraint with another input parameter.
     */
    @Test
    public void trueShouldNotHaveSameHashcodeAsTrueWithOtherName() {
        assertFalse(IP1_TRUE.hashCode() == BooleanConstraint.parse("i2", "True").hashCode());
    }

    /**
     * A true constraint should not have the same hashcode as a false constraint with the same input parameter.
     */
    @Test
    public void trueShouldNotHaveSameHashcodeAsFalseWithSameInputParameter() {
        assertFalse(IP1_TRUE.hashCode() == IP1_FALSE.hashCode());
    }

    /**
     * An Equality should not have the same hashcode as another equality with another name.
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
     * An Negation should not have the same hashcode as another Negation with another name.
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
     * An Equality should not have the same hashcode as another equality with another input parameter.
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
     * An Negation should not have the same hashcode as another Negation with another input parameter.
     */
    @Test
    public void negationShouldNotHaveSameHashcodeAsNegationWithOtherInputParameter() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1.hashCode() == BooleanConstraint.parse("i2", "¬i3").hashCode());
    }

    /**
     * Verifies that a deep clone of false is equal to the prototype.
     */
    @Test
    public void falseShouldBeEqualToDeepClone() {
        assertEquals(IP1_FALSE, IP1_FALSE.deepClone());
    }

    /**
     * Verifies that a deep clone of false is not identical to the prototype.
     */
    @Test
    public void falseShouldNotBeIdenticalToDeepClone() {
        assertFalse(IP1_FALSE == IP1_FALSE.deepClone());
    }

    /**
     * Verifies that a deep clone of true is equal to the prototype.
     */
    @Test
    public void trueShouldBeEqualToDeepClone() {
        assertEquals(IP1_TRUE, IP1_TRUE.deepClone());
    }

    /**
     * Verifies that a deep clone of true is not identical to the prototype.
     */
    @Test
    public void trueShouldNotBeIdenticalToDeepClone() {
        assertFalse(IP1_TRUE == IP1_TRUE.deepClone());
    }

    /**
     * Verifies that a deep clone of equality is equal to the prototype.
     */
    @Test
    public void equalityShouldBeEqualToDeepClone() {
        assertEquals(IP2_EQUAL_TO_IP1, IP2_EQUAL_TO_IP1.deepClone());
    }

    /**
     * Verifies that a deep clone of equality is not identical to the prototype.
     */
    @Test
    public void equalityShouldNotBeIdenticalToDeepClone() {
        assertFalse(IP2_EQUAL_TO_IP1 == IP2_EQUAL_TO_IP1.deepClone());
    }

    /**
     * Verifies that a deep clone of opposition is equal to the prototype.
     */
    @Test
    public void oppositionShouldBeEqualToDeepClone() {
        assertEquals(IP2_NOT_EQUAL_TO_IP1, IP2_NOT_EQUAL_TO_IP1.deepClone());
    }

    /**
     * Verifies that a deep clone of opposition is not identical to the prototype.
     */
    @Test
    public void oppositionShouldNotBeIdenticalToDeepClone() {
        assertFalse(IP2_NOT_EQUAL_TO_IP1 == IP2_NOT_EQUAL_TO_IP1.deepClone());
    }

    /**
     * Verifies that the number is wired correctly.
     */
    @Test
    public void getNumberIsWiredCorrectlyFromTheConstructor() {
        assertEquals(1, IP1_FALSE.getNumber());
    }
}
