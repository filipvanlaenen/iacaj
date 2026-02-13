package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Operator;

/**
 * Unit tests on the <code>BasicVectorialFunctionBuilder</code> class.
 */
public class BasicVectorialFunctionBuilderTest {
    /**
     * The magic number 4.
     */
    private static final int FOUR = 4;

    /**
     * Verifies that an <code>IllegalStateException</code> if the output vector width hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenOutputWidthIsntSet() {
        BasicVectorialFunctionBuilder builder = new BasicVectorialFunctionBuilder();
        builder.operator(Operator.AND);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a vectorial function with a variable width when the output vector width isn't set.",
                exception.getMessage());
    }

    /**
     * Verifies that an <code>IllegalStateException</code> if the operator hasn't been set.
     */
    @Test
    public void buildShouldThrowExceptionWhenOperatorIsntSet() {
        BasicVectorialFunctionBuilder builder = new BasicVectorialFunctionBuilder();
        builder.outputVectorWidth(2);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.build());
        assertEquals("Cannot build a basic vectorial function when the operator isn't set.", exception.getMessage());
    }

    /**
     * Verifies that correct production of basic vectorial operation.
     */
    @Test
    public void buildShouldProduceACorrectAndOperation() {
        BasicVectorialFunctionBuilder builder = new BasicVectorialFunctionBuilder();
        builder.outputVectorWidth(FOUR);
        builder.operator(Operator.AND);
        String actual = builder.build().toString() + "\n";
        assertTrue(actual.contains("y1 = x1 ∧ x5\n") || actual.contains("y1 = x5 ∧ x1\n"));
        assertTrue(actual.contains("y2 = x2 ∧ x6\n") || actual.contains("y2 = x6 ∧ x2\n"));
        assertTrue(actual.contains("y3 = x3 ∧ x7\n") || actual.contains("y3 = x7 ∧ x3\n"));
        assertTrue(actual.contains("y4 = x4 ∧ x8\n") || actual.contains("y4 = x8 ∧ x4\n"));
    }
}
