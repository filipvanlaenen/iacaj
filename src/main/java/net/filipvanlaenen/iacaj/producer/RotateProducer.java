package net.filipvanlaenen.iacaj.producer;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Class producing a Boolean function rotating the input parameters with a number of positions to the right.
 */
public final class RotateProducer extends Producer {
    /**
     * The number of positions to rotate to the right.
     */
    private final int numberOfPositions;

    /**
     * Creates a producer for ROTATE. The first parameter is used as the word length, the second as the number of
     * positions the word should be rotated to the right.
     *
     * @param parameters A list with parameters.
     */
    public RotateProducer(final OrderedCollection<Integer> parameters) {
        super(getItemOrNull(parameters, 0));
        if (parameters.size() > 1) {
            this.numberOfPositions = parameters.getAt(1);
        } else {
            this.numberOfPositions = 0;
        }
    }

    @Override
    public BooleanFunction produce() {
        BooleanFunction bf = new BooleanFunction();
        Word word = extractWordFromInput(0);
        Word result = word.rightRotate(numberOfPositions);
        appendWordToOutput(bf, result, 0);
        return bf;
    }
}
