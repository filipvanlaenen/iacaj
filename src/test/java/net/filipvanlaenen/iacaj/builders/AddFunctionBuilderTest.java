package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.LiteralExpression;
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
        ModifiableMap<Variable, Expression> message = ModifiableMap.empty();
        message.add(inputVector.getAt(7), LiteralExpression.TRUE);
        message.add(inputVector.getAt(14), LiteralExpression.TRUE);
        for (int i = 0; i < 16; i++) {
            if (!message.containsKey(inputVector.getAt(i))) {
                message.add(inputVector.getAt(i), LiteralExpression.FALSE);
            }
        }
        VectorialFunction addFunctionWithInputVector = addFunction.extendWih(message);
        VectorialFunction result = addFunctionWithInputVector.simplify(builder.getOutputVector());
        String output = result.toString();
        assertTrue(output.contains("o6 = false"));
        assertTrue(output.contains("o7 = true"));
        assertTrue(output.contains("o8 = true"));

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
        ModifiableMap<Variable, Expression> message = ModifiableMap.empty();
        message.add(inputVector.getAt(5), LiteralExpression.TRUE);
        message.add(inputVector.getAt(13), LiteralExpression.TRUE);
        message.add(inputVector.getAt(15), LiteralExpression.TRUE);
        for (int i = 0; i < 16; i++) {
            if (!message.containsKey(inputVector.getAt(i))) {
                message.add(inputVector.getAt(i), LiteralExpression.FALSE);
            }
        }
        VectorialFunction addFunctionWithInputVector = addFunction.extendWih(message);
        VectorialFunction result = addFunctionWithInputVector.simplify(builder.getOutputVector());
        String output = result.toString();
        assertTrue(output.contains("o4 = false"));
        assertTrue(output.contains("o5 = true"));
        assertTrue(output.contains("o6 = false"));
        assertTrue(output.contains("o7 = false"));
        assertTrue(output.contains("o8 = true"));
    }

    /**
     * Unit test verifying that <code>0x06 + 0x07 = 0x0d</code>.
     */
    @Test
    public void adding6And7ShouldProduceD() {
        assertAddition(0xefcdab89l, 0xb5523babl, 0xa51fe734l);
        assertAddition(0xefcdab89l, 0xa521e734l, 0x94ef92bdl);
    }

    private void assertAddition(long a, long b, long r) {
        Word inputVector = new Word("i", 64);
        ModifiableMap<Variable, Expression> message = ModifiableMap.empty();
        message.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(inputVector.firstHalf(), a));
        message.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(inputVector.secondHalf(), b));
        AddFunctionBuilder builder = new AddFunctionBuilder();
        builder.outputVectorWidth(32);
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        builder.build();
        VectorialFunction addFunction = builder.build();
        VectorialFunction addFunctionWithInputVector = addFunction.extendWih(message);
        Word outputVector = builder.getOutputVector();
        VectorialFunction result = addFunctionWithInputVector.simplify(outputVector);
        ModifiableMap<Variable, Expression> expected = ModifiableMap.empty();
        expected.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector, r));
        VectorialFunction e = new VectorialFunction(expected);
        assertTrue(e.containsSame(result));
    }
}
