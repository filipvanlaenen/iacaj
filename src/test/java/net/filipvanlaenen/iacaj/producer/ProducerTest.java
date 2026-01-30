package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>Producer</code> class.
 */
public class ProducerTest {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;

    /**
     * Verifies that words of length one can be ADDed. Tests the functionality through <code>AddProducer</code>.
     */
    @Test
    public void addWordsShouldAddWordsOfLengthOne() {
        Producer producer = new AddProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ⊻ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that words of length two can be ADDed. Tests the functionality through <code>AddProducer</code>.
     */
    @Test
    public void addWordsShouldAddWordsOfLengthTwo() {
        Producer producer = new AddProducer(OrderedCollection.of(2));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i2 ⊻ i4\n");
        sb.append("v2 = i2 ∧ i4\n");
        sb.append("v3 = i1 ⊻ i3 ⊻ v2\n");
        sb.append("o1 = v3\n");
        sb.append("o2 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that words of length three can be ADDed. Tests the functionality through <code>AddProducer</code>.
     */
    @Test
    public void addWordsShouldAddWordsOfLengthThree() {
        Producer producer = new AddProducer(OrderedCollection.of(THREE));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i3 ⊻ i6\n");
        sb.append("v2 = i3 ∧ i6\n");
        sb.append("v3 = i2 ⊻ i5 ⊻ v2\n");
        sb.append("v4 = i2 ⊻ i5\n");
        sb.append("v5 = v2 ∧ v4\n");
        sb.append("v6 = i2 ∧ i5\n");
        sb.append("v7 = v5 ⊻ v6\n");
        sb.append("v8 = i1 ⊻ i4 ⊻ v7\n");
        sb.append("o1 = v8\n");
        sb.append("o2 = v3\n");
        sb.append("o3 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that words can be right shifted. Tests the functionality through <code>ShiftProducer</code>.
     */
    @Test
    public void rightShiftWordsShouldRightShiftWords() {
        Producer producer = new ShiftProducer(OrderedCollection.of(2, 1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = False\n");
        sb.append("o1 = v1\n");
        sb.append("o2 = i1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that words can be left shifted. Tests the functionality through <code>ShiftProducer</code>.
     */
    @Test
    public void rightShiftWordsWithNegativeNumberOfPositionsShouldLeftShiftWords() {
        Producer producer = new ShiftProducer(OrderedCollection.of(2, -1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = False\n");
        sb.append("o1 = i2\n");
        sb.append("o2 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }
}
