package net.filipvanlaenen.iacaj.producer;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;
import net.filipvanlaenen.iacaj.BooleanOperation.Operator;

/**
 * Abstract superclass for producers of Boolean functions.
 */
public abstract class Producer {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;
    /**
     * Counter for the internal variables added to the Boolean function.
     */
    private long vCounter = 0;

    /**
     * Adds two words.
     *
     * @param bf The Boolean function.
     * @param w0 The first word.
     * @param w1 The second word.
     * @return A word holding the addition of the two words.
     */
    protected Word addWords(final BooleanFunction bf, final Word w0, final Word w1) {
        int wordLength = getWordLength();
        Word result = new Word(wordLength);
        BooleanOperation sum = new BooleanOperation(getNextInternalVariableName(),
                w0.get(wordLength - 1) + " ⊻ " + w1.get(wordLength - 1));
        bf.addExpression(sum);
        result.put(wordLength - 1, sum.getName());
        if (wordLength > 1) {
            BooleanOperation carry = new BooleanOperation(getNextInternalVariableName(),
                    w0.get(wordLength - 1) + " ∧ " + w1.get(wordLength - 1));
            bf.addExpression(carry);
            sum = new BooleanOperation(getNextInternalVariableName(),
                    w0.get(wordLength - 2) + " ⊻ " + w1.get(wordLength - 2) + " ⊻ " + carry.getName());
            bf.addExpression(sum);
            result.put(wordLength - 2, sum.getName());
            for (int i = wordLength - THREE; i >= 0; i--) {
                BooleanOperation p1 = new BooleanOperation(getNextInternalVariableName(),
                        w0.get(i + 1) + " ⊻ " + w1.get(i + 1));
                bf.addExpression(p1);
                BooleanOperation p2 = new BooleanOperation(getNextInternalVariableName(),
                        carry.getName() + " ∧ " + p1.getName());
                bf.addExpression(p2);
                BooleanOperation p3 = new BooleanOperation(getNextInternalVariableName(),
                        w0.get(i + 1) + " ∧ " + w1.get(i + 1));
                bf.addExpression(p3);
                carry = new BooleanOperation(getNextInternalVariableName(), p2.getName() + " ⊻ " + p3.getName());
                bf.addExpression(carry);
                sum = new BooleanOperation(getNextInternalVariableName(),
                        w0.get(i) + " ⊻ " + w1.get(i) + " ⊻ " + carry.getName());
                bf.addExpression(sum);
                result.put(i, sum.getName());
            }
        }
        return result;
    }

    /**
     * Combines to words using AND.
     *
     * @param bf    The Boolean function.
     * @param words The words to be ANDed together.
     * @return A word holding the result.
     */
    protected Word andWords(final BooleanFunction bf, final Word... words) {
        return atomicOperationOnWords(bf, Operator.And, words);
    }

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
            BooleanOperation bo = new BooleanOperation(getNextInternalVariableName(),
                    String.join(" " + operator.getSymbol() + " ", operands));
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    /**
     * Extracts a word from the input parameters.
     *
     * @param wordIndex The index of the word to extract from the input parameters,
     *                  starting from 0.
     * @return A word containing the input parameters.
     */
    protected Word extractWordFromInput(final int wordIndex) {
        int wordLength = getWordLength();
        int bitIndexOffset = wordLength * wordIndex;
        Word first = new Word(wordLength);
        for (int i = 0; i < wordLength; i++) {
            first.put(i, "i" + (bitIndexOffset + i + 1));
        }
        return first;
    }

    /**
     * Returns the name for the next internal variable that can be used to add a
     * Boolean expression to the Boolean function.
     *
     * @return The name for the next internal variable.
     */
    protected String getNextInternalVariableName() {
        return "v" + (++vCounter);
    }

    /**
     * Returns the length of the words.
     *
     * @return The length of the words.
     */
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

    /**
     * Produces the producer's Boolean function.
     *
     * @return The Boolean function.
     */
    public abstract BooleanFunction produce();

    /**
     * Combines to words using XOR.
     *
     * @param bf    The Boolean function.
     * @param words The words to be XORed together.
     * @return A word holding the result.
     */
    protected Word xorWords(final BooleanFunction bf, final Word... words) {
        return atomicOperationOnWords(bf, Operator.Xor, words);
    }
}
