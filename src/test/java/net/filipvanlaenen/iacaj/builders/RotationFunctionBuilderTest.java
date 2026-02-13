package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>RotationFunctionBuilder</code> class.
 */
public class RotationFunctionBuilderTest {
    /**
     * The magic number 4.
     */
    private static final int FOUR = 4;

    /**
     * Verifies that an <code>IllegalStateException</code> if the output vector width hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenOutputWidthIsntSet() {
        RotationFunctionBuilder builder = new RotationFunctionBuilder();
        builder.rotateRight(2);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a vectorial function with a variable width when the output vector width isn't set.",
                exception.getMessage());
    }

    /**
     * Verifies that an <code>IllegalStateException</code> if the shift hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenRotateLeftRightIsntSet() {
        RotationFunctionBuilder builder = new RotationFunctionBuilder();
        builder.outputVectorWidth(2);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a rotate function when the rotate left/right isn't set.", exception.getMessage());
    }

    /**
     * Verifies that correct production of rotate right operation.
     */
    @Test
    public void buildShouldProduceACorrectRotateRightOperation() {
        RotationFunctionBuilder builder = new RotationFunctionBuilder();
        builder.outputVectorWidth(FOUR);
        builder.rotateRight(1);
        String actual = builder.build().toString() + "\n";
        assertTrue(actual.contains("y1 = x4\n"));
        assertTrue(actual.contains("y2 = x1\n"));
        assertTrue(actual.contains("y3 = x2\n"));
        assertTrue(actual.contains("y4 = x3\n"));
    }

    /**
     * Verifies that correct production of rotate left operation.
     */
    @Test
    public void buildShouldProduceACorrectRotateLeftOperation() {
        RotationFunctionBuilder builder = new RotationFunctionBuilder();
        builder.outputVectorWidth(FOUR);
        builder.rotateLeft(1);
        String actual = builder.build().toString() + "\n";
        assertTrue(actual.contains("y1 = x2\n"));
        assertTrue(actual.contains("y2 = x3\n"));
        assertTrue(actual.contains("y3 = x4\n"));
        assertTrue(actual.contains("y4 = x1\n"));
    }
}
