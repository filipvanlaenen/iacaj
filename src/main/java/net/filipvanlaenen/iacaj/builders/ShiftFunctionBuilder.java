package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;

public final class ShiftFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    private Integer shiftRight;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        if (outputVectorWidth == null) {
            throw new IllegalStateException("Cannot build a shift function when the output vector width isn't set.");
        }
        if (shiftRight == null) {
            throw new IllegalStateException("Cannot build a shift function when the shift right isn't set.");
        }
        Word inputVector = new Word(inputVectorName, outputVectorWidth);
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        Map<Variable, Expression> map = buildShiftFunctions(inputVector, outputVector, shiftRight);
        return new VectorialFunction(map);
    }

    public void shiftRight(Integer shiftRight) {
        this.shiftRight = shiftRight;
    }
}
