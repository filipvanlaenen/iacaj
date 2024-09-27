package net.filipvanlaenen.iacaj.producer;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Class producing a Boolean function ANDing the first half of the input
 * parameters with the second half.
 */
public final class AndProducer extends Producer {
    /**
     * Creates a producer for AND. If parameters are provided, the first parameter
     * is used as the word length.
     *
     * @param parameters A list with parameters.
     */
    public AndProducer(final OrderedCollection<Integer> parameters) {
        super(getItemOrNull(parameters, 0));
    }

    @Override
    public BooleanFunction produce() {
        BooleanFunction bf = new BooleanFunction();
        Word first = extractWordFromInput(0);
        Word second = extractWordFromInput(1);
        Word result = andWords(bf, first, second);
        appendWordToOutput(bf, result, 0);
        return bf;
    }
}
