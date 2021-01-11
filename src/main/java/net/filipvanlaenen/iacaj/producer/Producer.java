package net.filipvanlaenen.iacaj.producer;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;
import net.filipvanlaenen.iacaj.BooleanOperation.Operator;

public abstract class Producer {
    /**
     * Counter for the internal variables added to the Boolean function.
     */
    protected long vCounter = 0;

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
