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
        for (int i = 0; i < getWordLength(); i++) {
            bf.addExpression(new BooleanOperation("o" + (getWordLength() * wordIndex + i + 1), word.get(i)));
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
        Word result = new Word(getWordLength());
        for (int i = 0; i < getWordLength(); i++) {
            String[] operands = new String[words.length];
            for (int j = 0; j < words.length; j++) {
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
        Word first = new Word(getWordLength());
        for (int i = 0; i < getWordLength(); i++) {
            first.put(i, "i" + (wordIndex * getWordLength() + i + 1));
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
