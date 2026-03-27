package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.kolektoj.ModifiableMap;

/**
 * Unit tests on the <code>VectorialFunctionBuilder</code> class.
 */
public class VectorialFunctionBuilderTest {
    /**
     * Unit test verifying that <code>0x01 + 0x02 = 0x03</code>.
     */
    @Test
    public void buildAdditionFunctionsShouldBuildAVectorialFunctionAddingCorrectly() {
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
}
