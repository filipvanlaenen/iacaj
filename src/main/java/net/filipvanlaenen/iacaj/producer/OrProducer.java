package net.filipvanlaenen.iacaj.producer;

import java.util.List;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;

public class OrProducer extends Producer {
    /**
     * The default word length.
     */
    private static final int DEFAULT_WORD_LENGTH = 32;
    /**
     * The word length.
     */
    private int wordLength;

    /**
     * Creates a producer for OR. If parameters are provided, the first parameter is
     * used as the word length.
     *
     * @param parameters A list with parameters.
     */
    public OrProducer(final List<Integer> parameters) {
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
        Word first = new Word(wordLength);
        for (int j = 0; j < wordLength; j++) {
            first.put(j, "i" + (j + 1));
        }
        Word second = new Word(wordLength);
        for (int j = 0; j < wordLength; j++) {
            second.put(j, "i" + (wordLength + j + 1));
        }
        Word result = orWords(bf, first, second);
        for (int i = 0; i < wordLength; i++) {
            bf.addExpression(new BooleanOperation("o" + (i + 1), result.get(i)));
        }
        return bf;
    }
}
