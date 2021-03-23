package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>InputParameterPair</code> class.
 */
public class InputParameterPairTest {
    /**
     * The input parameter <code>i1</code>.
     */
    private static final InputParameter INPUT_PARAMETER_1 = InputParameter.get("i1");
    /**
     * The input parameter <code>i2</code>.
     */
    private static final InputParameter INPUT_PARAMETER_2 = InputParameter.get("i2");
    /**
     * The input parameter <code>i3</code>.
     */
    private static final InputParameter INPUT_PARAMETER_3 = InputParameter.get("i3");
    /**
     * The input parameter pair with input parameters <code>i1</code> and
     * <code>i2</code>.
     */
    private static final InputParameterPair INPUT_PARAMETER_PAIR_1_2 = new InputParameterPair(INPUT_PARAMETER_1,
            INPUT_PARAMETER_2);
    /**
     * The input parameter pair with input parameters <code>i1</code> and
     * <code>i2</code>, provided in reverse order to the constructor.
     */
    private static final InputParameterPair INPUT_PARAMETER_PAIR_2_1 = new InputParameterPair(INPUT_PARAMETER_2,
            INPUT_PARAMETER_1);

    /**
     * Verifies that <code>getFirst</code> returns the input parameter with the
     * lowest number.
     */
    @Test
    public void getFirstShouldReturnTheInputParameterWithTheLowestNumber() {
        assertEquals(INPUT_PARAMETER_1, INPUT_PARAMETER_PAIR_1_2.getFirst());
    }

    /**
     * Verifies that <code>getFirst</code> returns the input parameter with the
     * lowest number even if they are provided in the other other to the
     * constructor.
     */
    @Test
    public void getFirstShouldReturnTheInputParameterWithTheLowestNumberRegardlessOfParameterOrderInConstructor() {
        assertEquals(INPUT_PARAMETER_1, INPUT_PARAMETER_PAIR_2_1.getFirst());
    }

    /**
     * Verifies that <code>getLast</code> returns the input parameter with the
     * lowest number.
     */
    @Test
    public void getLastShouldReturnTheInputParameterWithTheHighestNumber() {
        assertEquals(INPUT_PARAMETER_2, INPUT_PARAMETER_PAIR_1_2.getLast());
    }

    /**
     * Verifies that an input parameter pair is not equal to <code>null</code>.
     */
    @Test
    public void shouldNotBeEqualToNull() {
        assertFalse(INPUT_PARAMETER_PAIR_1_2.equals(null));
    }

    /**
     * Verifies that an input parameter pair is not equal to an empty string.
     */
    @Test
    public void shouldNotBeEqualToAnEmptyString() {
        assertFalse(INPUT_PARAMETER_PAIR_1_2.equals(""));
    }

    /**
     * Verifies that an input parameter pair is equal to itself.
     */
    @Test
    public void shouldBeEqualToItself() {
        assertEquals(INPUT_PARAMETER_PAIR_1_2, INPUT_PARAMETER_PAIR_1_2);
    }

    /**
     * Verifies that an input parameter pair is equal to an identical input
     * parameter pair.
     */
    @Test
    public void shouldBeEqualToIdenticalInputParameterPair() {
        assertEquals(INPUT_PARAMETER_PAIR_1_2, new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_2));
    }

    /**
     * Verifies that an input parameter pair is equal to an identical input
     * parameter pair with the input parameters provided in reversed order.
     */
    @Test
    public void shouldBeEqualToReversedInputParameterPair() {
        assertEquals(INPUT_PARAMETER_PAIR_1_2, INPUT_PARAMETER_PAIR_2_1);
    }

    /**
     * Verifies that an input parameter pair is not equal to an input parameter pair
     * with a different input parameter.
     */
    @Test
    public void shouldNotEqualToInputParameterPairWithAnotherInputParameter() {
        assertFalse(INPUT_PARAMETER_PAIR_1_2.equals(new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_3)));
    }

    /**
     * Verifies that an input parameter pair has the same hash code as an indentical
     * input parameter pair.
     */
    @Test
    public void shouldHaveSameHashcodeAsIdenticalInputParameterPair() {
        assertEquals(INPUT_PARAMETER_PAIR_1_2.hashCode(),
                new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_2).hashCode());
    }

    /**
     * Verifies that an input parameter pair has the same hash code as an identical
     * input parameter pair with the input parameters provided in reversed order.
     */
    @Test
    public void shouldHaveSameHashcodeAsReversedInputParameterPair() {
        assertEquals(INPUT_PARAMETER_PAIR_1_2.hashCode(), INPUT_PARAMETER_PAIR_2_1.hashCode());
    }

    /**
     * Verifies that an input parameter pair does not have the same hashcode as an
     * input parameter pair with a different input parameter. Note that this is not
     * a requirement for the hashcode method, though highly recommended for hashing
     * to work better.
     */
    @Test
    public void shouldNotHaveSameHashcodeAsInputParameterPairWithAnotherInputParameter() {
        assertFalse(INPUT_PARAMETER_PAIR_1_2.hashCode() == new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_3)
                .hashCode());
    }
}
