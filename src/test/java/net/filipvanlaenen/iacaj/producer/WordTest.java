package net.filipvanlaenen.iacaj.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Word</code> class.
 */
public class WordTest {
    /**
     * Verifies that get returns the variable that was put.
     */
    @Test
    public void getShouldReturnTheVariableThatWasPut() {
        Word word = new Word(8);
        word.put(0, "v1");
        assertEquals("v1", word.get(0));
    }

    /**
     * Verifies that right rotation moves the variables to the right.
     */
    @Test
    public void rightRotateMovesFirstVariableToTheRight() {
        Word word = new Word(8);
        for (int i = 0; i < 8; i++) {
            word.put(i, "v" + (i + 1));
        }
        Word rotated = word.rightRotate(3);
        assertEquals("v1", rotated.get(3));
        assertEquals("v5", rotated.get(7));
        assertEquals("v6", rotated.get(0));
        assertEquals("v8", rotated.get(2));
    }
}
