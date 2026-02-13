package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>AddFunctionBuilder</code> class.
 */
public class AddFunctionBuilderTest {
    /**
     * Verifies that an <code>IllegalStateException</code> if the output vector width hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenOutputWidthIsntSet() {
        AddFunctionBuilder builder = new AddFunctionBuilder();
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a vectorial function with a variable width when the output vector width isn't set.",
                exception.getMessage());
    }

    /**
     * Verifies the correct production of an add operation.
     */
    @Test
    public void buildShouldProduceACorrectRotateRightOperation() {
        AddFunctionBuilder builder = new AddFunctionBuilder();
        builder.outputVectorWidth(2);
        String actual = builder.build().toString() + "\n";
        assertTrue(actual.contains("y1 = x1 ⊻ x3 ⊻ y1c\n") || actual.contains("y1 = x1 ⊻ y1c ⊻ x3\n")
                || actual.contains("y1 = x3 ⊻ x1 ⊻ y1c\n") || actual.contains("y1 = x3 ⊻ y1c ⊻ x1\n")
                || actual.contains("y1 = y1c ⊻ x1 ⊻ x3\n") || actual.contains("y1 = y1c ⊻ x3 ⊻ x1\n"));
        assertTrue(actual.contains("y1c = x2 ∧ x4\n") || actual.contains("y1c = x4 ∧ x2\n"));
        assertTrue(actual.contains("y2 = x2 ⊻ x4\n") || actual.contains("y2 = x4 ⊻ x2\n"));
    }
}
