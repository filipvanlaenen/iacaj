package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>ShiftProducer</code> class.
 */
public class ShiftProducerTest {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;

    /**
     * Verifies that the producer can produce a Boolean function implementing SHIFT 0 for words of length 3.
     */
    @Test
    public void shouldProduceNoShiftFunctionWithWordLength3() {
        Producer producer = new ShiftProducer(OrderedCollection.of(THREE));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("o1 = i1\n");
        sb.append("o2 = i2\n");
        sb.append("o3 = i3");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that the producer can produce a Boolean function implementing SHIFT +1 for words of length 3.
     */
    @Test
    public void shouldProduceShiftOneRightFunctionWithWordLength3() {
        Producer producer = new ShiftProducer(OrderedCollection.of(THREE, 1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = False\n");
        sb.append("o1 = v1\n");
        sb.append("o2 = i1\n");
        sb.append("o3 = i2");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that the producer can produce a Boolean function implementing SHIFT -1 for words of length 3.
     */
    @Test
    public void shouldProduceShiftOneLeftFunctionWithWordLength3() {
        Producer producer = new ShiftProducer(OrderedCollection.of(THREE, -1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = False\n");
        sb.append("o1 = i2\n");
        sb.append("o2 = i3\n");
        sb.append("o3 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }
}
