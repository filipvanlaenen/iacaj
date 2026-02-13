package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ShiftFunctionBuilder</code> class.
 */
public class ShiftFunctionBuilderTest {
    /**
     * The magic number 4.
     */
    private static final int FOUR = 4;

    /**
     * Verifies that an <code>IllegalStateException</code> if the output vector width hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenOutputWidthIsntSet() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.shiftRight(2);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a vectorial function with a variable width when the output vector width isn't set.",
                exception.getMessage());
    }

    /**
     * Verifies that an <code>IllegalStateException</code> if the shift hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenShiftLeftRightIsntSet() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(2);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a shift function when the shift left/right isn't set.", exception.getMessage());
    }

    /**
     * Verifies that correct production of shift right operation.
     */
    @Test
    public void buildShouldProduceACorrectShiftRightOperation() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(FOUR);
        builder.shiftRight(2);
        String actual = builder.build().toString() + "\n";
        assertTrue(actual.contains("y1 = false\n"));
        assertTrue(actual.contains("y2 = false\n"));
        assertTrue(actual.contains("y3 = x1\n"));
        assertTrue(actual.contains("y4 = x2\n"));
    }

    /**
     * Verifies that correct production of shift left operation.
     */
    @Test
    public void buildShouldProduceACorrectShiftLeftOperation() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(FOUR);
        builder.shiftLeft(2);
        String actual = builder.build().toString() + "\n";
        assertTrue(actual.contains("y1 = x3\n"));
        assertTrue(actual.contains("y2 = x4\n"));
        assertTrue(actual.contains("y3 = false\n"));
        assertTrue(actual.contains("y4 = false\n"));
    }
}
