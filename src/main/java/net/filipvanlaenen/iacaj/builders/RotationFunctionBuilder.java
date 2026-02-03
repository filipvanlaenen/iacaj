package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.ModifiableMap;

public final class RotationFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    private Integer rotateRight;

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
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 1; i <= outputVectorWidth; i++) {
            Variable ivi = new Variable(inputVectorName + i);
            int j = ((i + rotateRight - 1) % outputVectorWidth) + 1;
            Variable ovi = new Variable(outputVectorName + j);
            map.add(ovi, new IdentityExpression(ivi));
        }
        return new VectorialFunction(map);
    }

    public void rotateRight(Integer rotateRight) {
        this.rotateRight = rotateRight;
    }
}
