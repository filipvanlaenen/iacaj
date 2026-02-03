package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.ModifiableMap;

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
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 1; i <= outputVectorWidth; i++) {
            Variable ivi = new Variable(inputVectorName + i);
            int j = ((i + shiftRight - 1) % outputVectorWidth) + 1;
            Variable ovi = new Variable(outputVectorName + j);
            map.add(ovi, new IdentityExpression(ivi));
        }
        return new VectorialFunction(map);
    }

    public void shiftRight(Integer shiftRight) {
        this.shiftRight = shiftRight;
    }
}
