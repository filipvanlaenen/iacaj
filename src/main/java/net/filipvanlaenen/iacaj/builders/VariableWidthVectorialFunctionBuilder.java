package net.filipvanlaenen.iacaj.builders;

abstract class VariableWidthVectorialFunctionBuilder extends VectorialFunctionBuilder {
    private Integer outputVectorWidth;

    protected Integer getOutputVectorWidth() {
        return outputVectorWidth;
    }

    public void outputVectorWidth(Integer width) {
        this.outputVectorWidth = width;
    }

    protected void prebuild() throws IllegalStateException {
        super.prebuild();
        if (outputVectorWidth == null) {
            throw new IllegalStateException(
                    "Cannot build a vectorial function with a variable width when the output vector width isn't set.");
        }
    }
}
