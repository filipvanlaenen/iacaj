package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;

public final class AddFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        if (outputVectorWidth == null) {
            throw new IllegalStateException("Cannot build an add function when the output vector width isn't set.");
        }
        Word inputVector = new Word(inputVectorName, outputVectorWidth * 2);
        Word inputVectorA = inputVector.firstHalf();
        Word inputVectorB = inputVector.secondHalf();
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        Map<Variable, Expression> map = buildAdditionFunctions(inputVectorA, inputVectorB, outputVector);
        return new VectorialFunction(map);
    }
}
