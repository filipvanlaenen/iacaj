package net.filipvanlaenen.iacaj.producer;

import java.util.List;

import net.filipvanlaenen.iacaj.BooleanFunction;

/**
 * Class producing a Boolean function shifting the input parameters with a
 * number of positions to the right.
 */
public final class ShiftProducer extends Producer {
    /**
     * The number of positions to shift to the right.
     */
    private final int numberOfPositions;

    /**
     * Creates a producer for SHIFT. The first parameter is used as the word length,
     * the second as the number of positions the word should be shifted to the
     * right.
     *
     * @param parameters A list with parameters.
     */
    public ShiftProducer(final List<Integer> parameters) {
        super(getItemOrNull(parameters, 0));
        if (parameters.size() > 1) {
            this.numberOfPositions = parameters.get(1);
        } else {
            this.numberOfPositions = 0;
        }
    }

    @Override
    public BooleanFunction produce() {
        BooleanFunction bf = new BooleanFunction();
        Word word = extractWordFromInput(0);
        Word result = rightShift(bf, word, numberOfPositions);
        appendWordToOutput(bf, result, 0);
        return bf;
    }
}
