package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.kolektoj.ModifiableMap;

/**
 * Unit tests on the <code>VectorialFunctionBuilder</code> class.
 */
public class VectorialFunctionBuilderTest {
    /**
     * Builds a vectorial function, adds an input vector, simplifies and asserts that the result equals the expected
     * output vector.
     *
     * @param builder           The builder.
     * @param inputVectorLength The length of the input vector.
     * @param inputValue        The input vector as a Long value.
     * @param outputValue       The output vector as a Long value.
     */
    public void buildSimplifyAndAssert(final VectorialFunctionBuilder builder, final int inputVectorLength,
            final Long inputValue, final Long outputValue) {
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        builder.build();
        VectorialFunction addFunction = builder.build();
        Word inputVector = new Word("i", inputVectorLength);
        ModifiableMap<Variable, Expression> inputVectorMap = ModifiableMap.empty();
        inputVectorMap.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(inputVector, inputValue));
        Word outputVector = builder.getOutputVector();
        VectorialFunction addFunctionWithInputVector = addFunction.extendWih(inputVectorMap);
        VectorialFunction actual = addFunctionWithInputVector.simplify(outputVector);
        ModifiableMap<Variable, Expression> expectedMap = ModifiableMap.empty();
        expectedMap.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector, outputValue));
        VectorialFunction expected = new VectorialFunction(expectedMap);
        assertTrue(expected.containsSame(actual));
    }

    /**
     * Unit test verifying that <code>0x01 + 0x02 = 0x03</code>. This unit test uses the subclass
     * <code>AddFunctionBuilder</code> to test the <code>buildAdditionFunctions</code> method.
     */
    @Test
    public void buildAdditionFunctionsShouldBuildAVectorialFunctionAddingCorrectly() {
        AddFunctionBuilder builder = new AddFunctionBuilder();
        builder.outputVectorWidth(8);
        buildSimplifyAndAssert(builder, 16, 0x0102L, 0x03L);
    }

    /**
     * Unit test verifying that <code>0x53 & 0x35 = 0x11</code>. This unit test uses the subclass
     * <code>BasicVectorialFunctionBuilder</code> to test the <code>buildOperationFunctions</code> method.
     */
    @Test
    public void buildOperationFunctionsShouldBuildAVectorialFunctionAndingCorrectly() {
        BasicVectorialFunctionBuilder builder = new BasicVectorialFunctionBuilder();
        builder.outputVectorWidth(8);
        builder.operator(Operator.AND);
        buildSimplifyAndAssert(builder, 16, 0x5335L, 0x11L);
    }

    /**
     * Unit test verifying that <code>0x84 R< 2 = 0x12</code>. This unit test uses the subclass
     * <code>RotationFunctionBuilder</code> to test the <code>buildRotationFunctions</code> method.
     */
    @Test
    public void buildRotationFunctionsShouldBuildAVectorialFunctionRotatingCorrectly() {
        RotationFunctionBuilder builder = new RotationFunctionBuilder();
        builder.outputVectorWidth(8);
        builder.rotateLeft(2);
        buildSimplifyAndAssert(builder, 8, 0x84L, 0x12L);
    }

    /**
     * Unit test verifying that <code>0xF1 S< 2 = 0xC4</code>. This unit test uses the subclass
     * <code>ShiftFunctionBuilder</code> to test the <code>buildShiftFunctions</code> method.
     */
    @Test
    public void buildShiftFunctionsShouldBuildAVectorialFunctionShiftingLeftCorrectly() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(8);
        builder.shiftLeft(2);
        buildSimplifyAndAssert(builder, 8, 0xF1L, 0xC4L);
    }

    /**
     * Unit test verifying that <code>0x8F S> 2 = 0x23</code>. This unit test uses the subclass
     * <code>ShiftFunctionBuilder</code> to test the <code>buildShiftFunctions</code> method.
     */
    @Test
    public void buildShiftFunctionsShouldBuildAVectorialFunctionShiftingRightCorrectly() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(8);
        builder.shiftRight(2);
        buildSimplifyAndAssert(builder, 8, 0x8FL, 0x23L);
    }
}
