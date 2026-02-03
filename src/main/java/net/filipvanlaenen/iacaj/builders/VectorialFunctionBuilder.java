package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.LiteralExpression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.OrderedCollection;

public abstract class VectorialFunctionBuilder {
    private String inputVectorName = "x";
    private String outputVectorName = "y";

    protected class Word {
        private final OrderedCollection<Variable> variables;

        Word(String inputVectorName, Integer outputVectorWidth) {
            variables =
                    OrderedCollection.createSequence(i -> new Variable(inputVectorName + (i + 1)), outputVectorWidth);
        }

        Variable getAt(int i) {
            return variables.getAt(i);
        }

        int size() {
            return variables.size();
        }
    }

    abstract public VectorialFunction build() throws IllegalStateException;

    protected Map<Variable, Expression> buildRotationFunctions(Word inputVector, Word outputVector, int rotateRight) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a rotation operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ivi = inputVector.getAt(i);
            int j = (i + rotateRight) % width;
            Variable ovi = outputVector.getAt(j);
            map.add(ovi, new IdentityExpression(ivi));
        }
        return map;
    }

    protected Map<Variable, Expression> buildShiftFunctions(Word inputVector, Word outputVector, int shiftRight) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a shift operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 1; i <= width; i++) {
            Variable ovi = outputVector.getAt(i);
            int j = i - shiftRight;
            if (j >= 1 && j <= width) {
                Variable ivi = inputVector.getAt(j);
                map.add(ovi, new IdentityExpression(ivi));
            } else {
                map.add(ovi, LiteralExpression.FALSE);
            }
        }
        return map;
    }

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
