package net.filipvanlaenen.iacaj.producer;

/**
 * Class representing a Word, holding references to variables.
 */
public class Word {
    /**
     * The length of the word.
     */
    private final int length;
    /**
     * The references to the variables.
     */
    private final String[] variableNames;

    /**
     * Constructor, creating an empty word of a given length.
     *
     * @param length The length of the word.
     */
    public Word(final int length) {
        this.length = length;
        variableNames = new String[length];
    }

    /**
     * Returns the variable name at position i.
     *
     * @param i The position for which the variable name should be returned.
     * @return The variable name at position i.
     */
    public String get(final int i) {
        return variableNames[i];
    }

    /**
     * Puts a variable name in the word at position i.
     *
     * @param i            The position where to place the variable name.
     * @param variableName The name of the variable.
     */
    public void put(final int i, final String variableName) {
        variableNames[i] = variableName;
    }

    /**
     * Returns a word with all variable names rotated to the right with r positions.
     *
     * @param r The number of positions to rotate.
     * @return A word with all variable names rotated to the right with r positions.
     */
    public Word rightRotate(final int r) {
        Word result = new Word(length);
        for (int i = 0; i < length; i++) {
            result.put(i, get((i + r) % length));
        }
        return result;
    }
}
