package net.filipvanlaenen.iacaj.builders;

/**
 * Abstract class for builders with a variable output vector width.
 */
abstract class VariableWidthVectorialFunctionBuilder extends VectorialFunctionBuilder {
    /**
     * The output vector width.
     */
    private Integer outputVectorWidth;

    /**
     * Returns the output vector width.
     *
     * @return The output vector width.
     */
    protected Integer getOutputVectorWidth() {
        return outputVectorWidth;
    }

    /**
     * Sets the output vector width.
     *
     * @param newOutputVectorWidth The output vector width.
     */
    public void outputVectorWidth(final Integer newOutputVectorWidth) {
        this.outputVectorWidth = newOutputVectorWidth;
    }

    /**
     * Runs assertions before the build phase is performed, typically verifying the state of the builder.
     *
     * @throws IllegalStateException Thrown if the builder isn't in a state ready to build.
     */
    protected void prebuild() throws IllegalStateException {
        super.prebuild();
        if (outputVectorWidth == null) {
            throw new IllegalStateException(
                    "Cannot build a vectorial function with a variable width when the output vector width isn't set.");
        }
    }
}
