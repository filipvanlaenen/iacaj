package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.ModifiableMap;

public final class RotationFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    Integer rotateRight;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        if (outputVectorWidth == null) {
            throw new IllegalStateException("Cannot build a rotate function when the output vector width isn't set.");
        }
        if (rotateRight == null) {
            throw new IllegalStateException("Cannot build a rotate function when the rotate right isn't set.");
        }
        Word inputVector = new Word(inputVectorName, outputVectorWidth);
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        map.addAll(buildRotationFunctions(inputVector, outputVector, rotateRight));
        return new VectorialFunction(map);
    }

    public void rotateRight(Integer rotateRight) {
        this.rotateRight = rotateRight;
    }
}
