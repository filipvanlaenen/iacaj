package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>OrProducer</code> class.
 */
public class OrProducerTest {
    /**
     * Verifies that the producer can produce a Boolean function implementing OR for words of length 1.
     */
    @Test
    public void shouldProduceOrFunctionWithWordLength1() {
        Producer producer = new OrProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ∨ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that the producer can produce a Boolean function implementing OR for words of length 2.
     */
    @Test
    public void shouldProduceOrFunctionWithWordLength2() {
        Producer producer = new OrProducer(OrderedCollection.of(2));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ∨ i3\n");
        sb.append("v2 = i2 ∨ i4\n");
        sb.append("o1 = v1\n");
        sb.append("o2 = v2");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }
}
