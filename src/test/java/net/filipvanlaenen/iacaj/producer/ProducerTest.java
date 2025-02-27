package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>Producer</code> class.
 */
public class ProducerTest {
    /**
     * Verifies that when a word length is provided, it is used. Tests the functionality through
     * <code>AndProducer</code>.
     */
    @Test
    public void constructorShouldUseProvidedWordLength() {
        Producer producer = new AndProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ∧ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that when no word length is provided, the default word length is used. Tests the functionality through
     * <code>AndProducer</code>.
     */
    @Test
    public void constructorShouldUseDefaultWordLength() {
        Producer producer = new AndProducer(OrderedCollection.of());
        BooleanFunction booleanFunction = producer.produce();
        String result = booleanFunction.toString();
        assertTrue(result.contains("v32"));
        assertFalse(result.contains("v33"));
    }

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
     * Verifies that words can be ANDed. Tests the functionality through <code>AndProducer</code>.
     */
    @Test
    public void andWordsShouldAndWords() {
        Producer producer = new AndProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ∧ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that words can be ORed. Tests the functionality through <code>OrProducer</code>.
     */
    @Test
    public void orWordsShouldOrWords() {
        Producer producer = new OrProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ∨ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }

    /**
     * Verifies that words can be XORed. Tests the functionality through <code>XorProducer</code>.
     */
    @Test
    public void xorWordsShouldXorWords() {
        Producer producer = new XorProducer(OrderedCollection.of(1));
        BooleanFunction booleanFunction = producer.produce();
        StringBuilder sb = new StringBuilder();
        sb.append("v1 = i1 ⊻ i2\n");
        sb.append("o1 = v1");
        String expected = sb.toString();
        assertEquals(expected, booleanFunction.toString());
    }
}
