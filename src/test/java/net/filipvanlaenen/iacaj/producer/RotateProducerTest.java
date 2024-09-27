package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>RotateProducer</code> class.
 */
public class RotateProducerTest {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;

    /**
     * Verifies that the producer can produce a Boolean function implementing ROTATE 0 for words of length 3.
     */
    @Test
    public void shouldProduceNoRotateFunctionWithWordLength3() {
        Producer producer = new RotateProducer(OrderedCollection.of(THREE));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("o1 = i1\n");
        sb.append("o2 = i2\n");
        sb.append("o3 = i3");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that the producer can produce a Boolean function implementing ROTATE +1 for words of length 3.
     */
    @Test
    public void shouldProduceRotateOneRightFunctionWithWordLength3() {
        Producer producer = new RotateProducer(OrderedCollection.of(THREE, 1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("o1 = i3\n");
        sb.append("o2 = i1\n");
        sb.append("o3 = i2");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }
}
