package net.filipvanlaenen.iacaj.producer;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;
import net.filipvanlaenen.iacaj.BooleanOperation.Operator;

public abstract class Producer {
    // TODO: Should be factored out.
    /**
     * Counter for the internal variables added to the Boolean function.
     */
    protected long vCounter = 0;

    /**
     * Appends a word to the output, with a given offset.
     *
     * @param bf        The Boolean function.
     * @param word      The word to be added to the output.
     * @param wordIndex The offset where to add the output.
     */
    protected void appendWordToOutput(final BooleanFunction bf, final Word word, final int wordIndex) {
        int wordLength = getWordLength();
        int bitIndexOffset = wordLength * wordIndex;
        for (int i = 0; i < wordLength; i++) {
            bf.addExpression(new BooleanOperation("o" + (bitIndexOffset + i + 1), word.get(i)));
        }
    }

    /**
     * Performs an atomic Boolean operations on a set of words.
     *
     * @param bf       The Boolean function.
     * @param operator The operator to apply.
     * @param words    The words on which to perform the operation.
     * @return A word holding the result.
     */
    protected Word atomicOperationOnWords(final BooleanFunction bf, final Operator operator, final Word... words) {
        int wordLength = getWordLength();
        int numberOfWords = words.length;
        Word result = new Word(wordLength);
        for (int i = 0; i < wordLength; i++) {
            String[] operands = new String[numberOfWords];
            for (int j = 0; j < numberOfWords; j++) {
                operands[j] = words[j].get(i);
            }
            BooleanOperation bo = new BooleanOperation("v" + (++vCounter),
                    String.join(" " + operator.getSymbol() + " ", operands));
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    protected Word extractWordFromInput(int wordIndex) {
        int wordLength = getWordLength();
        int bitIndexOffset = wordLength * wordIndex;
        Word first = new Word(wordLength);
        for (int i = 0; i < wordLength; i++) {
            first.put(i, "i" + (bitIndexOffset + i + 1));
        }
        return first;
    }

    protected abstract int getWordLength();

    /**
     * Combines to words using OR.
     *
     * @param bf    The Boolean function.
     * @param words The words to be ORed together.
     * @return A word holding the result.
     */
    protected Word orWords(final BooleanFunction bf, final Word... words) {
        return atomicOperationOnWords(bf, Operator.Or, words);
    }

    public abstract BooleanFunction produce();
}
