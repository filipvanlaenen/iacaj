package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;

/**
 * A builder class for the rotation function.
 */
public final class RotationFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    /**
     * The number of positions to rotate to the right.
     */
    private Integer rotateRight;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        if (rotateRight == null) {
            throw new IllegalStateException("Cannot build a rotate function when the rotate left/right isn't set.");
        }
        Word inputVector = new Word(inputVectorName, outputVectorWidth);
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        Map<Variable, Expression> map = buildRotationFunctions(inputVector, outputVector, rotateRight);
        return new VectorialFunction(map);
    }

    /**
     * Sets the number of positions the rotation function should move bits to the left.
     *
     * @param rotateLeft The number of positions the rotation function should move bits to the left.
     */
    public void rotateLeft(final Integer rotateLeft) {
        rotateRight(-rotateLeft);
    }

    /**
     * Sets the number of positions the rotation function should move bits to the right.
     *
     * @param newRotateRight The number of positions the rotation function should move bits to the right.
     */
    public void rotateRight(final Integer newRotateRight) {
        this.rotateRight = newRotateRight;
    }
}
