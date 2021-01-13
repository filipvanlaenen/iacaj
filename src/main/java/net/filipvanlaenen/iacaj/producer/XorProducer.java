package net.filipvanlaenen.iacaj.producer;

import java.util.List;

import net.filipvanlaenen.iacaj.BooleanFunction;

/**
 * Class producing a Boolean function XORing the first half of the input
 * parameters with the second half.
 */
public final class XorProducer extends Producer {
    /**
     * Creates a producer for XOR. If parameters are provided, the first parameter
     * is used as the word length.
     *
     * @param parameters A list with parameters.
     */
    public XorProducer(final List<Integer> parameters) {
        super(getItemOrNull(parameters, 0));
    }

    @Override
    public BooleanFunction produce() {
        BooleanFunction bf = new BooleanFunction();
        Word first = extractWordFromInput(0);
        Word second = extractWordFromInput(1);
        Word result = xorWords(bf, first, second);
        appendWordToOutput(bf, result, 0);
        return bf;
    }
}
