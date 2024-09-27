package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanExpression;
import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Unit tests on the <code>Sha256Producer</code> class.
 */
public class Sha256ProducerTest {
    /**
     * The magic number 4.
     */
    private static final int FOUR = 4;
    /**
     * The magic number 16.
     */
    private static final int SIXTEEN = 16;
    /**
     * A hexadecimal string representing a padded empty message for SHA-256.
     */
    private static final String EMPTY_MESSAGE = "8000000000000000000000000000000000000000000000000000000000000000"
            + "0000000000000000000000000000000000000000000000000000000000000000";

    /**
     * Verifies that when an input vector is applied representing an empty message, resolving the produced Boolean
     * function produces the correct result.
     */
    @Test
    public void shouldProduceTheTestVectorForTheEmptyMessage() {
        Producer producer = new Sha256Producer(OrderedCollection.of());
        BooleanFunction booleanFunction = producer.produce();
        booleanFunction.addExpressions(createInputParameters(EMPTY_MESSAGE));
        booleanFunction.resolve();
        String expected = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String actual = extractOutputParameters(booleanFunction);
        assertEquals(expected, actual);
    }

    /**
     * Converts a string with a hexadecimal value into a set of input parameters.
     *
     * @param hexValue The string with the hexadecimal value.
     * @return A set with the input parameters corresponding to the hexadecimal value of the string.
     */
    private Set<BooleanExpression> createInputParameters(final String hexValue) {
        Set<BooleanExpression> result = new HashSet<BooleanExpression>();
        BigInteger value = new BigInteger(hexValue, SIXTEEN);
        int wordLength = hexValue.length() * FOUR;
        for (int i = 0; i < wordLength; i++) {
            String rightHandSide;
            if (value.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
                rightHandSide = "False";
            } else {
                rightHandSide = "True";
            }
            BooleanOperation bo = new BooleanOperation("i" + (wordLength - i), rightHandSide);
            result.add(bo);
            value = value.divide(new BigInteger("2"));
        }
        return result;
    }

    /**
     * Extracts the output parameters as a hexadecimal string from a Boolean function.
     *
     * @param booleanFunction The Boolean function from which to extract the output parameters.
     * @return A hexadecimal string representing the output parameters.
     */
    private String extractOutputParameters(final BooleanFunction booleanFunction) {
        int wordLength = 0;
        while (booleanFunction.getExpression("o" + (wordLength + 1)) != null) {
            wordLength++;
        }
        BigInteger value = BigInteger.ZERO;
        BigInteger bitValue = BigInteger.ONE;
        for (int i = 0; i < wordLength; i++) {
            BooleanExpression be = booleanFunction.getExpression("o" + (wordLength - i));
            if (be.toString().contains("True")) {
                value = value.add(bitValue);
            }
            bitValue = bitValue.multiply(BigInteger.TWO);
        }
        return value.toString(SIXTEEN);
    }
}
