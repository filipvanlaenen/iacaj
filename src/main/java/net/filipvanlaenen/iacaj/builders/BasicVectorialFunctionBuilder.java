package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;

public final class BasicVectorialFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    private Operator operator;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        if (operator == null) {
            throw new IllegalStateException("Cannot build a basic vectorial function when the operator isn't set.");
        }
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        Word inputVector = new Word(inputVectorName, outputVectorWidth * 2);
        Word inputVectorA = inputVector.firstHalf();
        Word inputVectorB = inputVector.secondHalf();
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        Map<Variable, Expression> map = buildOperationFunctions(inputVectorA, inputVectorB, outputVector, operator);
        return new VectorialFunction(map);
    }

    public void operator(Operator operator) {
        this.operator = operator;
    }
}
