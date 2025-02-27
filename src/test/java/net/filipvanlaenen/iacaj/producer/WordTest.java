package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Word</code> class.
 */
public class WordTest {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;
    /**
     * The magic number 7.
     */
    private static final int SEVEN = 7;
    /**
     * The magic number 8.
     */
    private static final int EIGHT = 8;

    /**
     * Verifies that get returns the variable that was put.
     */
    @Test
    public void getShouldReturnTheVariableThatWasPut() {
        Word word = new Word(EIGHT);
        word.put(0, "v1");
        assertEquals("v1", word.get(0));
    }

    /**
     * Verifies that right rotation moves the variables to the right.
     */
    @Test
    public void rightRotateMovesFirstVariableToTheRight() {
        Word word = new Word(EIGHT);
        for (int i = 0; i < EIGHT; i++) {
            word.put(i, "v" + (i + 1));
        }
        Word rotated = word.rightRotate(THREE);
        assertEquals("v1", rotated.get(THREE));
        assertEquals("v5", rotated.get(SEVEN));
        assertEquals("v6", rotated.get(0));
        assertEquals("v8", rotated.get(2));
    }
}
