package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.kolektoj.Map;

/**
 * A builder class for the add function.
 */
public final class AddFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    private Word outputVector;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        Word inputVector = new Word(inputVectorName, outputVectorWidth * 2);
        Word inputVectorA = inputVector.firstHalf();
        Word inputVectorB = inputVector.secondHalf();
        outputVector = new Word(outputVectorName, outputVectorWidth);
        Map<Variable, Expression> map = buildAdditionFunctions(inputVectorA, inputVectorB, outputVector);
        return new VectorialFunction(map);
    }

    @Override
    public Word getOutputVector() {
        return outputVector;
    }
}
