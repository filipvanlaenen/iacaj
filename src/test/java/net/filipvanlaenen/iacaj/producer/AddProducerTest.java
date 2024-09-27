package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>AddProducer</code> class.
 */
public class AddProducerTest {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;

    /**
     * Verifies that the producer can produce a Boolean function implementing ADD for words of length 1.
     */
    @Test
    public void shouldProduceAddFunctionWithWordLength1() {
        Producer producer = new AddProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ⊻ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that the producer can produce a Boolean function implementing ADD for words of length 2.
     */
    @Test
    public void shouldProduceAddFunctionWithWordLength2() {
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
     * Verifies that the producer can produce a Boolean function implementing ADD for words of length 3.
     */
    @Test
    public void shouldProduceAddFunctionWithWordLength3() {
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
}
