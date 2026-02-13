package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;

/**
 * A builder class for the shift function.
 */
public final class ShiftFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    /**
     * The number of positions to shift to the right.
     */
    private Integer shiftRight;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        if (shiftRight == null) {
            throw new IllegalStateException("Cannot build a shift function when the shift left/right isn't set.");
        }
        Word inputVector = new Word(inputVectorName, outputVectorWidth);
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        Map<Variable, Expression> map = buildShiftFunctions(inputVector, outputVector, shiftRight);
        return new VectorialFunction(map);
    }

    /**
     * Sets the number of positions the shift function should move bits to the left.
     *
     * @param shiftLeft The number of positions the shift function should move bits to the left.
     */
    public void shiftLeft(final Integer shiftLeft) {
        shiftRight(-shiftLeft);
    }

    /**
     * Sets the number of positions the shift function should move bits to the right.
     *
     * @param newShiftRight The number of positions the shift function should move bits to the right.
     */
    public void shiftRight(final Integer newShiftRight) {
        this.shiftRight = newShiftRight;
    }
}
