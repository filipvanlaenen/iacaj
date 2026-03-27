package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.kolektoj.ModifiableMap;

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

    /**
     * Verifies that <code>getOutputVector</code> returns the correct output vector.
     */
    @Test
    public void getOutputVectorShouldReturnCorrectOutputVector() {
        AddFunctionBuilder builder = new AddFunctionBuilder();
        builder.outputVectorWidth(2);
        builder.build();
        Word outputVector = builder.getOutputVector();
        assertEquals(2, outputVector.size());
    }

    /**
     * Unit test verifying that <code>0x01 + 0x02 = 0x03</code>.
     */
    @Test
    public void adding1And2ShouldProduce3() {
        AddFunctionBuilder builder = new AddFunctionBuilder();
        builder.outputVectorWidth(8);
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        builder.build();
        VectorialFunction addFunction = builder.build();
        Word inputVector = new Word("i", 16);
        ModifiableMap<Variable, Expression> inputVectorMap = ModifiableMap.empty();
        inputVectorMap.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(inputVector, 0x0102L));
        Word outputVector = builder.getOutputVector();
        VectorialFunction addFunctionWithInputVector = addFunction.extendWih(inputVectorMap);
        VectorialFunction actual = addFunctionWithInputVector.simplify(outputVector);
        ModifiableMap<Variable, Expression> expectedMap = ModifiableMap.empty();
        expectedMap.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector, 0x03L));
        VectorialFunction expected = new VectorialFunction(expectedMap);
        assertTrue(expected.containsSame(actual));
    }

    /**
     * Unit test verifying that <code>0x04 + 0x05 = 0x09</code>.
     */
    @Test
    public void adding4And5ShouldProduce9() {
        AddFunctionBuilder builder = new AddFunctionBuilder();
        builder.outputVectorWidth(8);
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        builder.build();
        VectorialFunction addFunction = builder.build();
        Word inputVector = new Word("i", 16);
        ModifiableMap<Variable, Expression> inputVectorMap = ModifiableMap.empty();
        inputVectorMap.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(inputVector, 0x0405L));
        Word outputVector = builder.getOutputVector();
        VectorialFunction addFunctionWithInputVector = addFunction.extendWih(inputVectorMap);
        VectorialFunction actual = addFunctionWithInputVector.simplify(outputVector);
        ModifiableMap<Variable, Expression> expectedMap = ModifiableMap.empty();
        expectedMap.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector, 0x09L));
        VectorialFunction expected = new VectorialFunction(expectedMap);
        assertTrue(expected.containsSame(actual));
    }
}
