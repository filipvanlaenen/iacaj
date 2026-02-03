package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.VectorialFunction;

public abstract class VectorialFunctionBuilder {
    private String inputVectorName = "x";
    private String outputVectorName = "y";

    abstract public VectorialFunction build() throws IllegalStateException;

    protected String getInputVectorName() {
        return inputVectorName;
    }

    protected String getOutputVectorName() {
        return outputVectorName;
    }

    public void inputVectorName(String inputVectorName) {
        this.inputVectorName = inputVectorName;
    }

    public void outputVectorName(String outputVectorName) {
        this.outputVectorName = outputVectorName;
    }

    protected void prebuild() throws IllegalStateException {
        if (inputVectorName == null) {
            throw new IllegalStateException("Cannot build a vectorial function when the input vector name is null.");
        }
        if (outputVectorName == null) {
            throw new IllegalStateException("Cannot build a vectorial function when the output vector name is null.");
        }
        if (inputVectorName.equals(outputVectorName)) {
            throw new IllegalStateException(
                    "Cannot build a vectorial  function when the input and output vector names are equal.");
        }
    }
}
