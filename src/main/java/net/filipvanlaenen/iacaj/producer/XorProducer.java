package net.filipvanlaenen.iacaj.producer;

import java.util.List;

import net.filipvanlaenen.iacaj.BooleanFunction;

/**
 * Class producing a Boolean function XORing the first half of the input
 * parameters with the second half.
 */
public class XorProducer extends Producer {
    /**
     * The default word length.
     */
    private static final int DEFAULT_WORD_LENGTH = 32;
    /**
     * The word length.
     */
    private int wordLength;

    /**
     * Creates a producer for XOR. If parameters are provided, the first parameter
     * is used as the word length.
     *
     * @param parameters A list with parameters.
     */
    public XorProducer(final List<Integer> parameters) {
        if (parameters.isEmpty()) {
            this.wordLength = DEFAULT_WORD_LENGTH;
        } else {
            this.wordLength = parameters.get(0);
        }
    }

    @Override
    protected int getWordLength() {
        return wordLength;
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
