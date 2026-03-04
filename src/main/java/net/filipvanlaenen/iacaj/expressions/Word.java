package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ModifiableOrderedCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * A class modeling a word in a vectorial function.
 */
public final class Word {
    /**
     * The variables of the word.
     */
    private final OrderedCollection<Variable> variables;

    /**
     * Constructor using a base name and a width.
     *
     * @param baseName The base name for the variables in the word.
     * @param width    The width of the word.
     */
    public Word(final String baseName, final Integer width) {
        variables = OrderedCollection.createSequence(i -> new Variable(baseName + (i + 1)), width);
    }

    /**
     * Constructor using a word and the indexes for a window on that word to create a new word.
     *
     * @param word The word to extract the new word from.
     * @param i    The index of the first variable included in the extracted word.
     * @param j    The index of the first variable not included in the extracted word.
     */
    private Word(final Word word, final int i, final int j) {
        variables = OrderedCollection.of(word.variables, i, j);
    }

    public Word(Word word1, Word word2) {
        ModifiableOrderedCollection<Variable> v = ModifiableOrderedCollection.empty();
        v.addAll(word1.getVariables());
        v.addAll(word2.getVariables());
        variables = OrderedCollection.of(v);
    }

    /**
     * Extracts a word representing the first half of this word.
     *
     * @return A word representing the first half of this word.
     */
    public Word firstHalf() {
        return new Word(this, 0, variables.size() / 2);
    }

    /**
     * Returns the variable at provided index.
     *
     * @param i The index for which to return the variable.
     * @return The variable at the provided index in the word.
     */
    public Variable getAt(final int i) {
        return variables.getAt(i);
    }

    public Word getSlice(final int fromIndex, final int toIndex) {
        return new Word(this, fromIndex, toIndex);
    }

    OrderedCollection<Variable> getVariables() {
        return variables;
    }

    /**
     * Extracts a word representing the second half of this word.
     *
     * @return A word representing the second half of this word.
     */
    public Word secondHalf() {
        return new Word(this, variables.size() / 2, variables.size());
    }

    /**
     * Returns the size of the word.
     *
     * @return The size of the word.
     */
    public int size() {
        return variables.size();
    }
}